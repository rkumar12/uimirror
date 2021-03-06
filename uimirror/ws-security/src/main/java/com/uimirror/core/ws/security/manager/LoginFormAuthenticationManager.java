/*******************************************************************************
 * Copyright (c) 2014 Uimirror.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Uimirror license
 * which accompanies this distribution, and is available at
 * http://www.uimirror.com/legal
 *
 * Contributors:
 * Uimirror Team
 *******************************************************************************/
package com.uimirror.core.ws.security.manager;

import static com.uimirror.core.Constants.IP;
import static com.uimirror.core.Constants.USER_AGENT;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.uimirror.core.AccountState;
import com.uimirror.core.AccountStatus;
import com.uimirror.core.RandomKeyGenerator;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.exceptions.db.RecordNotFoundException;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.user.Credentials;
import com.uimirror.core.user.UserAuthDBFields;
import com.uimirror.core.user.store.UserCredentialsStore;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.core.ws.security.processor.UserRestoreProcessor;
import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.PasswordMatcher;
import com.uimirror.sso.auth.LoginAuthentication;
import com.uimirror.sso.auth.provider.AccessTokenProvider;
import com.uimirror.sso.client.store.UserAuthorizedClientStore;
import com.uimirror.sso.exception.AuthExceptionMapper;
import com.uimirror.sso.exception.AuthenticationException;
import com.uimirror.sso.exception.BadCredentialsException;
import com.uimirror.sso.exception.InvalidTokenException;
import com.uimirror.sso.exception.LockedException;
import com.uimirror.sso.exception.NotVerifiedException;
import com.uimirror.sso.token.TokenGenerator;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details {@linkplain LoginAuthentication}
 * 
 * Steps are as below
 * <ol>
 * <li>Validate the previous token</li>
 * <li>Validate the user credentials</li>
 * <li>Generate a Secret {@link AccessToken} of type {@link TokenType#SECRET} or
 * {@link TokenType#_2FA} or {@linkplain TokenType#USER_PERMISSION}</li>
 * <li>Restore user account if required</li>
 * </ol>
 * @author Jay
 */
//TODO if account restore required try to get the details via webservice call only no depedancy please
public class LoginFormAuthenticationManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(LoginFormAuthenticationManager.class);
	
	private UserCredentialsStore userCredentialStore;
	private AccessTokenProvider persistedAccessTokenProvider;
	private PasswordMatcher passwordMatcher;
	private UserAuthorizedClientStore persistedUserAuthorizedClientStore;
	private BackgroundProcessorFactory<String, Object> backgroundProcessorFactory; 

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LOG.info("[START]- Authenticating User");
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		AccessToken token = authenticateAndGetToken(authentication);
		LOG.info("[END]- Authenticating User");
		return new LoginAuthentication(token);
	}

	/**
	 * Gets the {@link Credentials} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private AccessToken authenticateAndGetToken(Authentication authentication){
		LOG.debug("[START]- Reteriving User Credentials on basics of the user id");
		Map<String, String> credentials = (Map<String, String>)authentication.getCredentials();
		Map<String, Object> details = (Map<String, Object>)authentication.getDetails();
		//Step 1- Get the previous Token
		AccessToken prevToken = getPreviousToken(credentials);
		//Step 2- Get the user credentials
		Credentials userCredentials = getUserCredentials((String)details.get(AuthConstants.USER_ID));
		//Step 3- Check the account status as well password match
		doAuthenticate(credentials.get(AuthConstants.PASSWORD), userCredentials);
		//Step 4- Generate a new Token
		AccessToken newToken = issueNewToken(prevToken, userCredentials, authentication);
		//Step 5- Restore User Account If required
		doAccountRestoreIfRequired(userCredentials);
		LOG.debug("[END]- Reteriving User Credentials on basics of the user id");
		return newToken;
	}

	/**
	 * This will retrieve the previous token issued for the client.
	 * 
	 * @param credentials
	 * @return
	 */
	private AccessToken getPreviousToken(Map<String, String> credentials){
		String access_token = credentials.get(AuthConstants.ACCESS_TOKEN);
		AccessToken token = persistedAccessTokenProvider.get(access_token);
		if(token == null || !TokenType.TEMPORAL.equals(token.getType()) || token.getExpire() > 0l)
			throw new InvalidTokenException();
		return token;
	}
	
	/**
	 * Will try to find the user based on the user ID, if no record then throw
	 * an {@link BadCredentialsException}
	 * @param userId
	 * @return
	 */
	private Credentials getUserCredentials(String userId){
		return userCredentialStore.getCredentialsByUserName(userId);
	}
	
	/**
	 * <p>This will validate the credentials in the order, authentication should happen</p>
	 * @param auth
	 * @param userCredentials
	 * @return <code>true</code> if successfully authenticated else <code>false</code>
	 * or appropriate {@link AuthenticationException}
	 */
	private boolean doAuthenticate(String providedPassword, Credentials userCredentials){
		if(AccountState.NEW.equals(userCredentials.getAccountState())){
			throw new NotVerifiedException();
		}
		if(AccountStatus.BLOCKED.equals(userCredentials.getAccountStatus())){
			throw new LockedException();
		}
		if(!passwordMatcher.match(providedPassword, userCredentials.getPassword(), userCredentials.getEncryptionStratgy()))
			throw new BadCredentialsException();
		
		return Boolean.TRUE;
	}
	
	/**
	 * Issue a new Token based on the previous and current state of the account.
	 * @param prevToken
	 * @param userCredentials
	 * @param authentication
	 * @return
	 */
	private AccessToken issueNewToken(AccessToken prevToken, Credentials userCredentials, Authentication authentication) {
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)authentication.getDetails();
		TokenType tokenType = getTokenType(userCredentials, prevToken);
		Token token = generateToken(tokenType);
		long expiresOn = getExpiresOn(details, tokenType);
		String requestor = prevToken.getClient();
		String owner = userCredentials.getProfileId();
		return new DefaultAccessToken.TokenBuilder(token).
				addOwner(owner).
				addClient(requestor).
				addExpire(expiresOn).
				addType(tokenType).
				addScope(prevToken.getScope()).
				addNotes(getNotes(details)).
				addInstructions(getInstructions(details, tokenType)).build();
	}
	
	/**
	 * Decides the token type that should be appropriate for the next step of operations.
	 * @param userCredentials
	 * @param prevTokn
	 * @return
	 */
	private TokenType getTokenType(Credentials userCredentials, AccessToken prevTokn){
		TokenType type = isOTPRequired(userCredentials) ? TokenType._2FA :  TokenType.SECRET;
		if(TokenType.SECRET.equals(type) && !isClientAuthorized(userCredentials.getProfileId(), prevTokn.getClient(), prevTokn.getScope().getScope())){
			type = TokenType.USER_PERMISSION;
		}
		return type;
	}
	
	/**
	 * Checks if the client has been authorized by the user for the given scope
	 * @param profileId
	 * @param clientId
	 * @param scope
	 * @return
	 */
	private boolean isClientAuthorized(String profileId, String clientId, String scope){
		boolean authroized = Boolean.FALSE;
		try{
			if(persistedUserAuthorizedClientStore.findAuthrorizedClient(profileId, clientId, scope) != null)
				authroized = Boolean.TRUE;
		}catch(RecordNotFoundException e){
			LOG.warn("Client is not authroized by the user, need to promote the screen");
		}
		return authroized;
	}
	
	/**
	 * If {@link TokenType#SECRET} then generate the token without par-phrase
	 * else a token with par-phase
	 * @param type
	 * @return
	 */
	private Token generateToken(TokenType type){
		Token token = null;
		if(TokenType.SECRET.equals(type))
			token = TokenGenerator.getNewOneWithOutPharse();
		else
			token = TokenGenerator.getNewOne();
		return token;
	}
	
	/**
	 * Decides the expires interval of the token
	 * @param details
	 * @param type
	 * @return
	 */
	private long getExpiresOn(Map<String, Object> details, TokenType type){
		long expire = getExpiresInterval(details, type);
		return expire == 0l ? 0l : DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(expire);
	}

	/**
	 * Gets the expires period of the token
	 * @param details
	 * @param type
	 * @return
	 */
	private long getExpiresInterval(Map<String, Object> details, TokenType type){
		long expires = 0l;
		if(TokenType.SECRET.equals(type)){
			expires = getMaxExpiresTime(details, type);
		}
		return expires;
	}

	/**
	 * This will determine the max expires interval of the token.
	 * @param details
	 * @param type
	 * @return
	 */
	private long getMaxExpiresTime(Map<String, Object> details, TokenType type){
		boolean keepMeLogedIn = Boolean.FALSE;
		if(details.get(AuthConstants.KEEP_ME_LOGIN) != null){
			keepMeLogedIn = (boolean) details.get(AuthConstants.KEEP_ME_LOGIN);
		}
		return keepMeLogedIn ? AuthConstants.KEEP_ME_LOGED_IN_EXPIRY_INTERVAL : AuthConstants.DEFAULT_EXPIRY_INTERVAL;
	}
	
	/**
	 * Checks if next set of the login is OTP or not
	 * @param userCredentials
	 * @return
	 */
	private boolean isOTPRequired(Credentials userCredentials){
		Map<String, Object> instructions = userCredentials.getInstructions();
		if(instructions.get(UserAuthDBFields.ACC_INS_2FA) != null)
			return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	/**
	 * Get details such as IP and user agent
	 * @param details
	 * @return
	 */
	private Map<String, Object> getNotes(Map<String, Object> details){
		Map<String, Object> notes = new LinkedHashMap<String, Object>(5);
		notes.put(IP, details.get(IP));
		notes.put(USER_AGENT, details.get(USER_AGENT));
		return notes;
	}
	
	/**
	 * Get instructions for this token such as expire interval
	 * @param details
	 * @param type
	 * @return
	 */
	private Map<String, Object> getInstructions(Map<String, Object> details, TokenType type){
		Map<String, Object> instructions = new LinkedHashMap<String, Object>(5);
		instructions.put(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, getMaxExpiresTime(details, type));
		if(TokenType._2FA.equals(type)){
			instructions.put(AuthConstants.OTP, RandomKeyGenerator.randomString(4));
			instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_USER_OTP);
		}else if(TokenType.USER_PERMISSION.equals(type)){
			instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_CLIENT_AUTHORIZATION);
		}else{
			instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_ACCESS_TOKEN);
		}
		return instructions;
	}
	/**
	 * Checks if the user account is in disabled state 
	 * it will try to restore the user account to normal state in background
	 * @param userCredentials
	 */
	private void doAccountRestoreIfRequired(Credentials userCredentials){
		if(isAccountRestoreRequired(userCredentials))
			backgroundProcessorFactory.getProcessor(UserRestoreProcessor.NAME).invoke(userCredentials.getProfileId());
	}
	
	/**
	 * Checks if the account state {@link AccountState#DISABLED}, then tries to restore the account in back ground.
	 * 
	 * @param userCredentials
	 * @return
	 */
	private boolean isAccountRestoreRequired(Credentials userCredentials){
		boolean restore = Boolean.FALSE;
		restore = AccountState.DISABLED.equals(userCredentials.getAccountState()) ? Boolean.TRUE : Boolean.FALSE;
		return restore;
	}

	public void setUserCredentialStore(UserCredentialsStore userCredentialStore) {
		this.userCredentialStore = userCredentialStore;
	}

	public void setPersistedAccessTokenProvider(
			AccessTokenProvider persistedAccessTokenProvider) {
		this.persistedAccessTokenProvider = persistedAccessTokenProvider;
	}

	public void setPasswordMatcher(PasswordMatcher passwordMatcher) {
		this.passwordMatcher = passwordMatcher;
	}

	public void setPersistedUserAuthorizedClientStore(
			UserAuthorizedClientStore persistedUserAuthorizedClientStore) {
		this.persistedUserAuthorizedClientStore = persistedUserAuthorizedClientStore;
	}

	public void setBackgroundProcessorFactory(
			BackgroundProcessorFactory<String, Object> backgroundProcessorFactory) {
		this.backgroundProcessorFactory = backgroundProcessorFactory;
	}

}

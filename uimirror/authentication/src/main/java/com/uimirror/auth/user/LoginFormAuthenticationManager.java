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
package com.uimirror.auth.user;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.uimirror.auth.bean.AccountState;
import com.uimirror.auth.bean.AccountStatus;
import com.uimirror.auth.bean.DefaultAccessToken;
import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.core.AuthenticationException;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.core.BadCredentialsException;
import com.uimirror.auth.core.InvalidTokenException;
import com.uimirror.auth.core.LockedException;
import com.uimirror.auth.core.NotVerifiedException;
import com.uimirror.auth.core.PasswordMatcher;
import com.uimirror.auth.core.TokenGenerator;
import com.uimirror.auth.dao.UserCredentialsStore;
import com.uimirror.auth.exception.AuthExceptionMapper;
import com.uimirror.auth.user.bean.LoginFormAuthentication;
import com.uimirror.core.RandomKeyGenerator;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.util.DateTimeUtil;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details {@linkplain AuthenticatedDetails}
 * 
 * @author Jay
 */
public class LoginFormAuthenticationManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(LoginFormAuthenticationManager.class);
	
	private @Autowired UserCredentialsStore userCredentialStore;
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;
	private @Autowired PasswordMatcher passwordMatcher;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating User");
		AccessToken token = authenticateAndGetToken(authentication);
		LOG.info("[END]- Authenticating User");
		return new LoginFormAuthentication(token);
	}

	/**
	 * Gets the {@link UserCredentials} object from the {@link Authentication}
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
		UserCredentials userCredentials = getUserCredentials((String)details.get(AuthConstants.USER_ID));
		//Step 3- Check the account status as well password match
		doAuthenticate(credentials.get(AuthConstants.PASSWORD), userCredentials);
		//Step 4- Generate a new Token
		AccessToken newToken = issueNewToken(prevToken, userCredentials, authentication);
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
		if(token == null || !TokenType.TEMPORAL.equals(token.getType()))
			throw new InvalidTokenException();
		return token;
	}
	
	/**
	 * Will try to find the user based on the user ID, if no record then throw
	 * an {@link BadCredentialsException}
	 * @param userId
	 * @return
	 */
	private UserCredentials getUserCredentials(String userId){
		return userCredentialStore.getCredentialsByUserName(userId);
	}
	
	/**
	 * <p>This will validate the credentials in the order, authentication should happen</p>
	 * @param auth
	 * @param userCredentials
	 * @return <code>true</code> if successfully authenticated else <code>false</code>
	 * or appropriate {@link AuthenticationException}
	 */
	private boolean doAuthenticate(String providedPassword, UserCredentials userCredentials){
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
	private AccessToken issueNewToken(AccessToken prevToken, UserCredentials userCredentials, Authentication authentication) {
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)authentication.getDetails();
		Token token = TokenGenerator.getNewOneWithOutPharse();
		TokenType tokenType = getTokenType(userCredentials);
		long expiresOn = getExpiresOn(details, tokenType);
		String requestor = prevToken.getClient();
		String owner = userCredentials.getProfileId();
		return new DefaultAccessToken(token, owner, requestor
				, expiresOn, tokenType, prevToken.getScope()
				, getNotes(details), getInstructions(details, tokenType, userCredentials.getAccountState()));
	}
	
	/**
	 * Decides the token type that should be appropriate for the next step of operations.
	 * @param userCredentials
	 * @return
	 */
	private TokenType getTokenType(UserCredentials userCredentials){
		//TODO check here if it is a secret key, then user has accepted client or not, if not issue temporal token
		return isOTPRequired(userCredentials) ? TokenType._2FA :  TokenType.SECRET;
	}
	
	/**
	 * Decides the expires interval of the token
	 * @param details
	 * @param type
	 * @return
	 */
	private long getExpiresOn(Map<String, Object> details, TokenType type){
		return DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(getExpiresInterval(details, type));
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
	private boolean isOTPRequired(UserCredentials userCredentials){
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
		notes.put(AuthConstants.IP, details.get(AuthConstants.IP));
		notes.put(AuthConstants.USER_AGENT, details.get(AuthConstants.USER_AGENT));
		return notes;
	}
	
	/**
	 * Get instructions for this token such as expire interval
	 * @param details
	 * @return
	 */
	private Map<String, Object> getInstructions(Map<String, Object> details, TokenType type, AccountState state){
		Map<String, Object> instructions = new LinkedHashMap<String, Object>(5);
		instructions.put(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, getMaxExpiresTime(details, type));
		if(TokenType._2FA.equals(type)){
			instructions.put(AuthConstants.OTP, RandomKeyGenerator.randomString(4));
			instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_USER_OTP);
		}else{
			instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_ACCESS_TOKEN);
		}
		if(AccountState.DISABLED.equals(state)){
			instructions.put(AuthConstants.INST_RESTORE_REQUIRED, Boolean.TRUE);
		}
		return instructions;
	}

}

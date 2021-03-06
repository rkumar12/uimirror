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

import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.exceptions.db.RecordNotFoundException;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.user.Credentials;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.auth.OTPAuthentication;
import com.uimirror.sso.auth.provider.AccessTokenProvider;
import com.uimirror.sso.client.store.UserAuthorizedClientStore;
import com.uimirror.sso.exception.AuthExceptionMapper;
import com.uimirror.sso.exception.AuthenticationException;
import com.uimirror.sso.exception.BadCredentialsException;
import com.uimirror.sso.exception.InvalidTokenException;
import com.uimirror.sso.token.TokenGenerator;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details {@linkplain OTPAuthentication}
 * 
 * Steps are as below
 * <ol>
 * <li>Validate the previous token</li>
 * <li>Validate the OTP</li>
 * <li>Generate a Secret {@link AccessToken} of type {@link TokenType#SECRET} or
 * {@linkplain TokenType#USER_PERMISSION}</li>
 * </ol>
 * 
 * @author Jay
 */
public class OTPAuthenticationManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(OTPAuthenticationManager.class);
	private AccessTokenProvider persistedAccessTokenProvider;
	private UserAuthorizedClientStore persistedUserAuthorizedClientStore;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating User's OTP");
		AccessToken token = authenticateAndGetToken(authentication);
		LOG.info("[END]- Authenticating User");
		return new OTPAuthentication(token);
	}

	/**
	 * Gets the {@link Credentials} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private AccessToken authenticateAndGetToken(Authentication authentication){
		LOG.debug("[START]- Reteriving Previous Token to match with OTP");
		Map<String, String> credentials = (Map<String, String>)authentication.getCredentials();
		//Step 1- Get the previous Token
		AccessToken prevToken = getPreviousToken(credentials);
		//Step 3- Check the account status as well OTP
		doAuthenticate(credentials.get(AuthConstants.PASSWORD), prevToken.getInstructions());
		//Step 4- Generate a new Token
		AccessToken newToken = issueNewToken(prevToken, authentication);
		LOG.debug("[END]- Reteriving Previous Token to match with OTP");
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
		if(token == null || !TokenType._2FA.equals(token.getType()) || token.getExpire() > 0l)
			throw new InvalidTokenException();
		return token;
	}
	
	/**
	 * <p>This will validate the credentials in the order, authentication should happen</p>
	 * @param auth
	 * @param map
	 * @return <code>true</code> if successfully authenticated else <code>false</code>
	 * or appropriate {@link AuthenticationException}
	 */
	private boolean doAuthenticate(String providedOtp, Map<String, Object> map){
		if(!StringUtils.hasText(providedOtp))
			throw new BadCredentialsException();
		String otp = (String)map.get(AuthConstants.OTP);
		if(!providedOtp.equals(otp))
			throw new BadCredentialsException();
		
		return Boolean.TRUE;
	}
	
	/**
	 * Issue a new Token based on the previous and current state of the account.
	 * @param prevToken
	 * @param authentication
	 * @return
	 */
	private AccessToken issueNewToken(AccessToken prevToken, Authentication authentication) {
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)authentication.getDetails();
		Map<String, Object> intsructions = prevToken.getInstructions();
		//Get the TokenType first
		TokenType tokenType = getTokenType(prevToken);
		//Get Token
		Token token = generateToken(tokenType);
		//Get Expires On
		long expiresOn = getExpiresOn(intsructions, tokenType);

		return new DefaultAccessToken.TokenBuilder(token).
				addClient(prevToken.getClient()).
				addOwner(prevToken.getOwner()).
				addExpire(expiresOn).
				addType(tokenType).
				addScope(prevToken.getScope()).
				addNotes(getNotes(getNotes(details))).
				addInstructions(getInstructions(intsructions, tokenType)).build();
	}
	
	/**
	 * Decides the token type that should be appropriate for the next step of operations.
	 * @param prevTokn
	 * @return
	 */
	private TokenType getTokenType(AccessToken prevTokn){
		TokenType type = TokenType.SECRET;
		if(!isClientAuthorized(prevTokn.getOwner(), prevTokn.getClient(), prevTokn.getScope().getScope())){
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
	 * @param instructions
	 * @param type
	 * @return
	 */
	private long getExpiresOn(Map<String, Object> instructions, TokenType type){
		if(TokenType.USER_PERMISSION.equals(type))
			return 0l;
		return DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(getExpiresInterval(instructions));
	}

	/**
	 * Gets the expires period of the token
	 * @param instructions
	 * @return
	 */
	private long getExpiresInterval(Map<String, Object> instructions){
		return (long)instructions.getOrDefault(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, 0l);
	}
	
	/**
	 * Get details such as IP and user agent
	 * @param details
	 * @return
	 */
	private Map<String, Object> getNotes(Map<String, Object> details){
		Map<String, Object> notes = new WeakHashMap<String, Object>(5);
		notes.put(IP, details.get(IP));
		notes.put(USER_AGENT, details.get(USER_AGENT));
		return notes;
	}
	
	/**
	 * Get instructions for this token such as expire interval
	 * @param instructions
	 * @return
	 */
	private Map<String, Object> getInstructions(Map<String, Object> prevInstructions, TokenType type){
		Map<String, Object> instructions = new WeakHashMap<String, Object>(5);
		instructions.put(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, getExpiresInterval(prevInstructions));
		if(TokenType.USER_PERMISSION.equals(type)){
			instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_CLIENT_AUTHORIZATION);
		}else{
			instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_ACCESS_TOKEN);
		}
		return instructions;
	}

	public void setPersistedAccessTokenProvider(
			AccessTokenProvider persistedAccessTokenProvider) {
		this.persistedAccessTokenProvider = persistedAccessTokenProvider;
	}

	public void setPersistedUserAuthorizedClientStore(
			UserAuthorizedClientStore persistedUserAuthorizedClientStore) {
		this.persistedUserAuthorizedClientStore = persistedUserAuthorizedClientStore;
	}
	
}

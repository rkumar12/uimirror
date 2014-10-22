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
package com.uimirror.account.auth.user;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.uimirror.account.auth.controller.AccessTokenProvider;
import com.uimirror.account.auth.core.AuthenticationManager;
import com.uimirror.account.auth.core.PasswordMatcher;
import com.uimirror.account.auth.core.TokenGenerator;
import com.uimirror.account.auth.exception.AuthExceptionMapper;
import com.uimirror.account.auth.user.bean.ScreenLockAuthentication;
import com.uimirror.account.user.dao.UserCredentialsStore;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.user.UserCredentials;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.ws.api.security.exception.AuthenticationException;
import com.uimirror.ws.api.security.exception.BadCredentialsException;
import com.uimirror.ws.api.security.exception.InvalidTokenException;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details {@linkplain AuthenticatedDetails}
 * 
 * @author Jay
 */
public class ScreenLockAuthenticationManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(ScreenLockAuthenticationManager.class);
	
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
		LOG.info("[START]- Authenticating User for unlocking the screen");
		AccessToken token = authenticateAndGetToken(authentication);
		LOG.info("[END]- Authenticating User for unlocking the screen");
		return new ScreenLockAuthentication(token);
	}


	/**
	 * @param authentication
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private AccessToken authenticateAndGetToken(Authentication authentication) {
		Map<String, String> credentials = (Map<String, String>)authentication.getCredentials();
		//Step 1- Get the previous Token
		AccessToken prevToken = getPreviousToken(credentials);
		//Step 2- Get the user credentials
		UserCredentials userCredentials = getUserCredentials(prevToken.getOwner());
		//Step 3- Check the account status as well password match
		doAuthenticate(credentials.get(AuthConstants.PASSWORD), userCredentials);
		//Step 4- Generate a new Token
		AccessToken newToken = issueNewToken(prevToken, userCredentials, authentication);
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
		if(token == null || !TokenType.ACCESS.equals(token.getType()))
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
		if(!passwordMatcher.match(providedPassword, userCredentials.getScreenPassword(), userCredentials.getEncryptionStratgy()))
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
	@SuppressWarnings("unchecked")
	private AccessToken issueNewToken(AccessToken prevToken, UserCredentials userCredentials, Authentication authentication) {
		Map<String, Object> instructions = (Map<String, Object>)prevToken.getInstructions();
		Map<String, Object> details = (Map<String, Object>)authentication.getDetails();
		Token token = TokenGenerator.getNewOneWithOutPharse();
		TokenType tokenType = TokenType.ACCESS;
		long expiresOn = getExpiresOn(instructions);
		String requestor = prevToken.getClient();
		String owner = userCredentials.getProfileId();
		return new DefaultAccessToken(token, owner, requestor
				, expiresOn, tokenType, prevToken.getScope()
				, getNotes(details), getInstructions(instructions));
	}

	/**
	 * Decides the expires interval of the token
	 * @param instructions
	 * @return
	 */
	private long getExpiresOn(Map<String, Object> instructions){
		return DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(getExpiresInterval(instructions));
	}

	/**
	 * Gets the expires period of the token
	 * @param instructions
	 * @return
	 */
	private long getExpiresInterval(Map<String, Object> instructions){
		long expires = 0l;
		if(instructions.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL) != null){
			expires = (long)instructions.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL);
		}
		return expires;
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
	 * @param instructions
	 * @return
	 */
	private Map<String, Object> getInstructions(Map<String, Object> prevInstructions){
		Map<String, Object> instructions = new LinkedHashMap<String, Object>(5);
		instructions.put(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, getExpiresInterval(prevInstructions));
		return instructions;
	}
}

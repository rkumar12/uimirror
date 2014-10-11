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
import org.springframework.util.StringUtils;

import com.uimirror.auth.bean.DefaultAccessToken;
import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.core.AuthenticationException;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.core.BadCredentialsException;
import com.uimirror.auth.core.InvalidTokenException;
import com.uimirror.auth.core.TokenGenerator;
import com.uimirror.auth.exception.AuthExceptionMapper;
import com.uimirror.auth.user.bean.OTPAuthentication;
import com.uimirror.auth.user.bean.UserCredentials;
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
public class OTPAuthenticationManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(OTPAuthenticationManager.class);
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

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
	 * Gets the {@link UserCredentials} object from the {@link Authentication}
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
		if(token == null)
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
		//TODO check here if it is a secret key, then user has accepted client or not, if not issue temporal token
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)authentication.getDetails();
		Map<String, Object> intsructions = prevToken.getInstructions();
		Token token = TokenGenerator.getNewOneWithOutPharse();
		TokenType tokenType = TokenType.SECRET;
		long expiresOn = getExpiresOn(intsructions);
		String requestor = prevToken.getClient();
		String owner = prevToken.getOwner();
		return new DefaultAccessToken(token, owner, requestor
				, expiresOn, tokenType, prevToken.getScope()
				, getNotes(details), getInstructions(intsructions));
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
		instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_ACCESS_TOKEN);
		return instructions;
	}

}

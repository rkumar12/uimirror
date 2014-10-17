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

import com.uimirror.auth.bean.DefaultAccessToken;
import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.core.AuthenticationException;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.core.InvalidTokenException;
import com.uimirror.auth.core.TokenGenerator;
import com.uimirror.auth.exception.AuthExceptionMapper;
import com.uimirror.auth.user.bean.ClientAuthorizationAuthentication;
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
public class ClientAuthorizationAuthenticationManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(ClientAuthorizationAuthenticationManager.class);
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating User's Access Token");
		AccessToken token = authenticateAndGetToken(authentication);
		LOG.info("[END]- Authenticating User's Access Token");
		return new ClientAuthorizationAuthentication(token);
	}

	/**
	 * Gets the {@link UserCredentials} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private AccessToken authenticateAndGetToken(Authentication authentication){
		LOG.debug("[START]- Reteriving Previous Token to validate");
		Map<String, String> credentials = (Map<String, String>)authentication.getCredentials();
		//Step 1- Get the previous Token
		AccessToken prevToken = getPreviousToken(credentials);
		//Step 2- Generate a new Token
		AccessToken newToken = issueNewToken(prevToken, authentication);
		LOG.debug("[END]- Reteriving Previous Token to validate");
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
		if(token == null || !TokenType.USER_PERMISSION.equals(token.getType()))
			throw new InvalidTokenException();
		return token;
	}
	
	/**
	 * Issue a new Token based on the previous and current state of the account.
	 * @param prevToken
	 * @param authentication
	 * @return
	 */
	private AccessToken issueNewToken(AccessToken prevToken, Authentication authentication) {

		String requestor = prevToken.getClient();
		//Get the Requester client info in background //TODO think of a way how can be manged via spring
		
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)authentication.getDetails();
		TokenType tokenType = determineTokenType(details);
		Token token = generateToken(tokenType, prevToken.getToken());
		String owner = prevToken.getOwner();
		Map<String, Object> intsructions = prevToken.getInstructions();
		long expiresOn = determineExpiresOn(tokenType, intsructions);
		//TODO check for the instructions as instructions depends on the earlier source as well
		return new DefaultAccessToken(token, owner, requestor
				, expiresOn, tokenType, prevToken.getScope()
				, getNotes(details), getInstructions(intsructions));
	}
	
	/**
	 * Determines the {@link TokenType} based on the user action such as,
	 * if user has opted for the deny, then there is no meaning to process and generate new token
	 * in that case it will be {@link TokenType#CANCELLED}, which will not generate any new token
	 * else {@link TokenType#SECRET} which will generate new token.
	 * @param details
	 * @return
	 */
	private TokenType determineTokenType(Map<String, Object> details){
		Approval approval = (Approval)details.get(AuthConstants.APPROVAL);
		TokenType tokenType = null;
		if(Approval.DENY.equals(approval))
			tokenType= TokenType.CANCELLED;
		else
			tokenType= TokenType.SECRET;
		return tokenType;
	}
	
	/**
	 * Based on the token type, it will create if {@link TokenType#SECRET}
	 * else it will re-asgin the previous one.
	 * @param type
	 * @param earillerToken
	 * @return
	 */
	private Token generateToken(TokenType type, Token earillerToken){
		Token token = null;
		if(TokenType.SECRET.equals(type))
			token = TokenGenerator.getNewOneWithOutPharse();
		else
			token = earillerToken;
		return token;	
	}
	
	/**
	 * Determines the expires on time on UTC EPOCH time, if {@link TokenType#SECRET}
	 * then only a valid expires will apply else 0l
	 * @param type
	 * @param intsructions
	 * @return
	 */
	private long determineExpiresOn(TokenType type, Map<String, Object> intsructions){
		long expires = 0l;
		if(TokenType.SECRET.equals(type))
			expires = getExpiresOn(intsructions);
		return expires;
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

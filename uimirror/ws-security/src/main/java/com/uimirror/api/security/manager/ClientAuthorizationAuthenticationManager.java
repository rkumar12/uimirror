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
package com.uimirror.api.security.manager;

import static com.uimirror.core.Constants.IP;
import static com.uimirror.core.Constants.USER_AGENT;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.user.Credentials;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.sso.Approval;
import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.auth.ClientAuthorizationAuthentication;
import com.uimirror.sso.auth.provider.AccessTokenProvider;
import com.uimirror.sso.exception.AuthExceptionMapper;
import com.uimirror.sso.exception.AuthenticationException;
import com.uimirror.sso.exception.InvalidTokenException;
import com.uimirror.sso.token.TokenGenerator;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details {@linkplain ClientAuthorizationAuthentication}
 * Steps are as below
 * <ol>
 * <li>Validate the previous token</li>
 * <li>Generate a Secret {@link AccessToken} of type {@link TokenType#SECRET}</li>
 * </ol>
 * @author Jay
 */
public class ClientAuthorizationAuthenticationManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(ClientAuthorizationAuthenticationManager.class);
	private AccessTokenProvider persistedAccessTokenProvider;

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
	 * Gets the {@link Credentials} object from the {@link Authentication}
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
		if(token == null || !TokenType.USER_PERMISSION.equals(token.getType()) || token.getExpire() > 0l)
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
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)authentication.getDetails();
		//Get the token Type first
		TokenType tokenType = determineTokenType(details);
		Token token = generateToken(tokenType);
		String owner = prevToken.getOwner();
		Scope scope = getTokenScope(details);
		Map<String, Object> intsructions = prevToken.getInstructions();
		long expiresOn = determineExpiresOn(tokenType, intsructions);
		return new DefaultAccessToken.TokenBuilder(token).
				addOwner(owner).
				addClient(requestor).
				addExpire(expiresOn).
				addType(tokenType).
				addScope(scope).
				addNotes(getNotes(details)).
				addInstructions(getInstructions(intsructions)).
				build();
//		return new DefaultAccessToken(token, owner, requestor
//				, expiresOn, tokenType, scope
//				, getNotes(details), getInstructions(intsructions));
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
	 * 
	 * @param type
	 * @return
	 */
	private Token generateToken(TokenType type){
		//If Token Type is Cancelled, stop the process
		if(TokenType.CANCELLED.equals(type))
			return null;
		return TokenGenerator.getNewOneWithOutPharse();	
	}
	
	/**
	 * Determines the expires on time on UTC EPOCH time, if {@link TokenType#SECRET}
	 * then only a valid expires will apply else 0l
	 * @param type
	 * @param intsructions
	 * @return
	 */
	private long determineExpiresOn(TokenType type, Map<String, Object> intsructions){
		if(TokenType.CANCELLED.equals(type))
			return 0l;
		return getExpiresOn(intsructions);
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
	 * @param details
	 * @return
	 */
	private Scope getTokenScope(Map<String, Object> details) {
		Scope scope = (Scope)details.get(AuthConstants.SCOPE);
		return scope;
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
	 * @param instructions
	 * @return
	 */
	private Map<String, Object> getInstructions(Map<String, Object> prevInstructions){
		Map<String, Object> instructions = new LinkedHashMap<String, Object>(5);
		instructions.put(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, getExpiresInterval(prevInstructions));
		instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_ACCESS_TOKEN);
		return instructions;
	}

	public void setPersistedAccessTokenProvider(AccessTokenProvider persistedAccessTokenProvider) {
		this.persistedAccessTokenProvider = persistedAccessTokenProvider;
	}

}

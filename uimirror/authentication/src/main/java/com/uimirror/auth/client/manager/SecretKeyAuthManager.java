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
package com.uimirror.auth.client.manager;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.uimirror.auth.bean.DefaultAccessToken;
import com.uimirror.auth.client.Client;
import com.uimirror.auth.client.bean.OAuth2APIKeyAuthentication;
import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.core.AuthenticationException;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.core.BadCredentialsException;
import com.uimirror.auth.core.TokenGenerator;
import com.uimirror.auth.dao.ClientStore;
import com.uimirror.auth.exception.AuthExceptionMapper;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.service.MatcherService;
import com.uimirror.core.util.DateTimeUtil;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details in principal {@linkplain Authentication}
 * 
 * @author Jay
 */
public class SecretKeyAuthManager implements AuthenticationManager, MatcherService<AccessToken, Client>{
	
	protected static final Logger LOG = LoggerFactory.getLogger(SecretKeyAuthManager.class);
	
	private @Autowired ClientStore clientStore;
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating Client and generating access token");
		//Step 1- Authenticate and get the previous access Token, which has the user id and client info
		AccessToken token = authenticateAndGetToken(authentication);
		//Step 2- Mark the principal as the previous AccessToken
		Authentication authenticated = getAuthenticatedDetails(authentication, token);
		LOG.info("[END]- Authenticating Client and generating access token");
		return authenticated;
	}

	/**
	 * Gets the previous {@link AccessToken} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private AccessToken authenticateAndGetToken(Authentication authentication){
		LOG.debug("[START]- Reteriving Secret Code details issued to client eariller");
		Map<String, String> credentials = (Map<String, String>)authentication.getCredentials();
		AccessToken accessToken = getActiveToken(credentials.get(AuthConstants.CLIENT_SECRET_CODE));
		Client client = reteriveActieveClient(authentication.getName());
		if(!StringUtils.hasText(accessToken.getOwner()) 
				|| !TokenType.SECRET.equals(accessToken.getType()) 
				|| !isMatching(accessToken, client) 
				|| !isMatching(client.getSecret(), credentials.get(AuthConstants.CLIENT_SECRET))){
			throw new BadCredentialsException();
		}
		LOG.debug("[END]- Reteriving Secret Code details issued to client eariller");
		return accessToken;
	}
	
	/**
	 * Retrieves the client based on the client apiKey
	 * @param apiKey
	 * @return
	 */
	private Client reteriveActieveClient(String apiKey){
		return clientStore.findActieveClientByApiKey(apiKey);
	}
	
	/**
	 * Retrieves the {@link AccessToken} associated with the given token
	 * @param issuedToken
	 * @return
	 */
	private AccessToken getActiveToken(String issuedToken){
		return persistedAccessTokenProvider.getValid(issuedToken);
	}
	
	/**
	 * This will create a new {@link Authentication} of type {@link OAuth2APIKeyAuthentication}
	 * where principal is the {@link AccessToken} and details as passed from the authentication.
	 * @param auth
	 * @param token
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Authentication getAuthenticatedDetails(Authentication auth, AccessToken token) {
		Map<String, Object> details = (Map<String, Object>)auth.getDetails();
		//Issue a new Token with type as AccessToken
		AccessToken newToken = issueANewToken(token, details);
		return new OAuth2APIKeyAuthentication(newToken, details);
	}
	
	/**
	 * from the previous {@linkplain AccessToken} and provided previous details
	 * it will create a new {@link AccessToken} of the type {@link TokenType#ACCESS}
	 * 
	 * @param prevToken
	 * @param details
	 * @return
	 */
	private AccessToken issueANewToken(AccessToken prevToken, Map<String, Object> details){
		Map<String, Object> prevInstructions = prevToken.getInstructions(); 
		Token token = TokenGenerator.getNewOne();
		TokenType type = TokenType.ACCESS;
		String requestor = prevToken.getClient();
		String owner = prevToken.getOwner();
		long expire = getTokenExpire(prevInstructions);
		Scope scope = prevToken.getScope();
		return new DefaultAccessToken(token, owner, requestor, expire, type, scope, getNotes(details), getInstructions(prevInstructions));
	}
	
	/**
	 * Decide the {@link AccessToken} expire period
	 * @param instructions
	 * @return
	 */
	private long getTokenExpire(Map<String, Object> instructions){
		int standtingInstructions = 0;
		if(instructions.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL) != null){
			standtingInstructions = (int) instructions.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL);
		}
		standtingInstructions = standtingInstructions == 0 ? AuthConstants.DEFAULT_EXPIRY_INTERVAL : standtingInstructions;
		return DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(standtingInstructions);
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
	private Map<String, Object> getInstructions(Map<String, Object> prevInstructions){
		Map<String, Object> instructions = new LinkedHashMap<String, Object>(5);
		instructions.put(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, prevInstructions.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL));
		return instructions;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.MatcherService#isMatching(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean isMatching(AccessToken src, Client des) {
		boolean matching = Boolean.FALSE;
		if(src != null && des != null){
			if(src.getClient().equals(des.getClientId())){
				matching = Boolean.TRUE;
			}
		}
		return matching;
	}
	
	/**
	 * This will validate the provided credentials and stored credentials are matching or not
	 * @param src
	 * @param des
	 * @return
	 */
	public boolean isMatching(String src, String des){
		return src.equalsIgnoreCase(des);
	}

}

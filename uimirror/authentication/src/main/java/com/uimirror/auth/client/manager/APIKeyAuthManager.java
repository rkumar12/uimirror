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

import javax.persistence.Access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.uimirror.auth.bean.DefaultAccessToken;
import com.uimirror.auth.client.Client;
import com.uimirror.auth.client.bean.OAuth2APIKeyAuthentication;
import com.uimirror.auth.core.AuthenticationException;
import com.uimirror.auth.core.AuthenticationManager;
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
import com.uimirror.core.util.DateTimeUtil;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details in principal {@linkplain Authentication}
 * 
 * This always generate a {@link Access} or type {@link TokenType#TEMPORAL}
 * 
 * @author Jay
 */
public class APIKeyAuthManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(APIKeyAuthManager.class);
	
	private @Autowired ClientStore clientStore;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating Client");
		Client client = getActieveClientDetails(authentication);
		AccessToken token = generateToken(authentication, client);
		Authentication authenticated = getAuthenticatedDetails(authentication, token);
		LOG.info("[END]- Authenticating User");
		return authenticated;
	}

	/**
	 * Gets the {@link Client} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	private Client getActieveClientDetails(Authentication authentication){
		LOG.debug("[START]- Reteriving acteive Client By api Key");
		Client client = reteriveActiveClient(authentication.getName());
		LOG.debug("[END]- Reteriving acteive Client By api Key");
		return client;
	}
	
	/**
	 * Retrieves the client based on the client apiKey
	 * @param apiKey
	 * @return
	 */
	private Client reteriveActiveClient(String apiKey){
		return clientStore.findActieveClientByApiKey(apiKey);
	}
	
	/**
	 * This will create a new {@link Authentication} of type {@link OAuth2APIKeyAuthentication}
	 * where principal is the {@link Client} and details as passed from the authentication.
	 * @param auth
	 * @param token
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Authentication getAuthenticatedDetails(Authentication auth, AccessToken token) {
		return new OAuth2APIKeyAuthentication(token, (Map<String, Object>)auth.getDetails());
	}
	
	/**
	 * Generates a {@link AccessToken} based on the provided authentication
	 * @param auth
	 * @param client
	 * @return
	 */
	private AccessToken generateToken(Authentication auth, Client client){
		Token token = TokenGenerator.getNewOne();
		TokenType type = TokenType.TEMPORAL;
		String requestor = client.getClientId();
		long expire = DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(AuthConstants.DEFAULT_EXPIRY_INTERVAL+50);
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)auth.getDetails();
		return new DefaultAccessToken(token, null, requestor, expire, type, getScope(details), getNotes(details), getInstructions());
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
	 * Creates the instructions for this token, as this token only can be used for the next user authentication only.
	 * 
	 * @return
	 */
	private Map<String, Object> getInstructions(){
		Map<String, Object> instructions = new LinkedHashMap<String, Object>(5);
		instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_USER_AUTH);
		return instructions;
	}

	/**
	 * Gets the scope of this token from the caller
	 * @param details
	 * @return
	 */
	private Scope getScope(Map<String, Object> details){
		String scp = (String)details.get(AuthConstants.SCOPE);
		Scope scope = Scope.getEnum(scp) != null ? Scope.getEnum(scp) : Scope.READ;
		return scope;
	}

}

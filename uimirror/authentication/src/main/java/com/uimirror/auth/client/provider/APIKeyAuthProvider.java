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
package com.uimirror.auth.client.provider;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.uimirror.auth.client.bean.OAuth2APIKeyAuthentication;
import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;

/**
 * Validates the {@link Authentication} and populate the authenticated principal
 * with the appropriate token.
 * 
 * @author Jay
 */
public class APIKeyAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(APIKeyAuthProvider.class);
	private @Autowired AuthenticationManager apiKeyAuthManager;
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating and storing token");
		//Step 1- get the authenticated principal
		Authentication authDetails = getAuthenticatedDetails(authentication);
		LOG.debug("[END]- Authenticating, generating and storing token");
		//Step 2- generate a authentication which has a access token
		return generateAuthenticatedTokenPrincipal(authDetails);
	}
	
	/**
	 * Will interact with the {@link AuthenticationManager} to get the prinicpal of the caller
	 * @param auth
	 * @return
	 */
	private Authentication getAuthenticatedDetails(Authentication auth){
		return apiKeyAuthManager.authenticate(auth);
	}
	
	/**
	 * It should generate a access token and tries to encapsulate the accesstoken to the
	 * {@link Authentication}
	 * 
	 * @param auth an authenticated principal that indicate the prinicpal clearly.
	 * @return
	 */
	private Authentication generateAuthenticatedTokenPrincipal(Authentication auth){
		//Generate a Access Token
		AccessToken accessToken = persistedAccessTokenProvider.generateToken(auth);
		return populateNewAuthenticatedToken(accessToken);
	}
	
	/**
	 * Generates a new {@link Authentication} object using the {@link AccessToken}
	 * @param token
	 * @return
	 */
	private Authentication populateNewAuthenticatedToken(AccessToken token){
		Map<String, Object> details = new LinkedHashMap<String, Object>(10);
		if(!CollectionUtils.isEmpty(token.getNotes())){
			details.putAll(token.getNotes());
		}
		if(!CollectionUtils.isEmpty(token.getInstructions())){
			details.putAll(token.getInstructions());
		}
		token.eraseEsential();
		return new OAuth2APIKeyAuthentication(token, details);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return OAuth2APIKeyAuthentication.class.isAssignableFrom(authentication);
	}

}

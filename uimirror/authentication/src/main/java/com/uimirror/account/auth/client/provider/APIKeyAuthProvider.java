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
package com.uimirror.account.auth.client.provider;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.auth.client.bean.APIKeyAuthentication;
import com.uimirror.account.auth.controller.AccessTokenProvider;
import com.uimirror.account.auth.controller.AuthenticationProvider;
import com.uimirror.account.auth.core.AuthenticationManager;
import com.uimirror.account.auth.dao.AccessTokenStore;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;

/**
 * The step of operations for this processor is defined as below:
 * <ol>
 * <li>Authenticate the provided client details using {@linkplain Authentication}</li>
 * <li>Store {@link AccessToken}</li>
 * <li>Clean {@link AccessToken}</li>
 * </ol>
 * 
 * @author Jay
 */
public class APIKeyAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(APIKeyAuthProvider.class);
	private @Autowired AuthenticationManager apiKeyAuthManager;
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationProvider#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating and storing token");
		//Step 1- get the authenticated principal
		Authentication authDetails = getAuthenticatedDetails(authentication);
		//Step 2- Store principal
		storeAuthenticatedPrincipal((AccessToken)authDetails.getPrincipal());
		//Step 3- Clean principal before leaving the control
		LOG.debug("[END]- Authenticating, generating and storing token");
		return cleanAuthentication(authDetails);
	}
	
	/**
	 * Will interact with the {@link AuthenticationManager} to get the principal of the caller
	 * @param auth
	 * @return
	 */
	private Authentication getAuthenticatedDetails(Authentication auth){
		return apiKeyAuthManager.authenticate(auth);
	}
	
	/**
	 * Stores the {@link AccessToken} using {@link AccessTokenStore}
	 * @param token
	 */
	private void storeAuthenticatedPrincipal(AccessToken token){
		persistedAccessTokenProvider.store(token);
	}
	
	/**
	 * It should generate a access token and tries to encapsulate the accesstoken to the
	 * {@link Authentication}
	 * 
	 * @param auth an authenticated principal that indicate the principal clearly.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Authentication cleanAuthentication(Authentication auth){
		//Clean the Authentication principal
		AccessToken accessToken = (AccessToken)auth.getPrincipal();
		//No cleaning required as this will be a internal application communication
		accessToken = accessToken.eraseEsential();
		return new APIKeyAuthentication(accessToken, (Map<String, Object>)auth.getDetails());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return APIKeyAuthentication.class.isAssignableFrom(authentication);
	}

}

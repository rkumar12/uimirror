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

import com.uimirror.account.auth.client.OAuth2Authentication;
import com.uimirror.account.auth.controller.AuthenticationProvider;
import com.uimirror.account.auth.core.AuthenticationManager;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;

/**
 * Validates the {@link Authentication} and populate the authenticated principal
 * with the appropriate token i.e access_token. 
 * 
 * @author Jay
 */
public class AccessKeyAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(AccessKeyAuthProvider.class);
	private @Autowired AuthenticationManager accessKeyAuthManager;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating and storing token");
		//Step 1- get the authenticated principal
		Authentication authDetails = getAuthenticatedDetails(authentication);
		LOG.debug("[END]- Authenticating, generating and storing token");
		return authDetails;
	}

	/**
	 * Will interact with the {@link AuthenticationManager} to get the prinicpal of the caller
	 * @param auth
	 * @return
	 */
	private Authentication getAuthenticatedDetails(Authentication auth){
		return accessKeyAuthManager.authenticate(auth);
	}
	
	/**
	 * It should generate a access token and tries to encapsulate the accesstoken to the
	 * {@link Authentication}
	 * 
	 * @param auth an authenticated principal that indicate the principal clearly.
	 * @return
	 */
	//TODO remove this after everything complete
	private Authentication cleanAuthentication(Authentication auth){
		//Clean the Authentication principal
		AccessToken accessToken = (AccessToken)auth.getPrincipal();
		accessToken = accessToken.eraseEsential();
		return new OAuth2Authentication(accessToken, (Map<String, Object>)auth.getDetails());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return OAuth2Authentication.class.isAssignableFrom(authentication);
	}

}

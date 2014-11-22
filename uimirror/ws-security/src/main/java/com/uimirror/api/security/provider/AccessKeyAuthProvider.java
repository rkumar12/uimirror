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
package com.uimirror.api.security.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.auth.Authentication;
import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.auth.OAuth2Authentication;
import com.uimirror.sso.auth.provider.AuthenticationProvider;

/**
 * Validates the {@link Authentication} and populate the authenticated principal
 * with the appropriate token i.e access_token. 
 * 
 * @author Jay
 */
public class AccessKeyAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(AccessKeyAuthProvider.class);
	private AuthenticationManager accessKeyAuthManager;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating the provided details.");
		//Step 1- get the authenticated principal
		Authentication authDetails = getAuthenticatedDetails(authentication);
		LOG.debug("[END]- Authenticating the provided details.");
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

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return OAuth2Authentication.class.isAssignableFrom(authentication);
	}

	public void setAccessKeyAuthManager(AuthenticationManager accessKeyAuthManager) {
		this.accessKeyAuthManager = accessKeyAuthManager;
	}

}

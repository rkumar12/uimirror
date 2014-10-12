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
package com.uimirror.auth.user.provider;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.user.bean.ScreenLockAuthentication;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;

/**
 * Will be responsible to generate {@linkplain AccessToken} by validating the existing token,
 * then validate the provided password for the screen unlock.
 * 
 * it should adhere the methodology of {@linkplain AuthenticationManager#authenticate(Authentication)}
 * 
 * @author Jay
 */
public class ScreenLockAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(ScreenLockAuthProvider.class);
	private @Autowired AuthenticationManager screenLockAuthenticationManager;
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating or refreshing and storing token");
		//Step 1- get the authenticated principal
		Authentication authenticated = getAuthenticatedDetails(authentication);
		AccessToken token = (AccessToken)authenticated.getPrincipal();
		//Step 2- Store the token
		storeAuthenticatedPrincipal(token);
		//Step 3- Clean Authentication Principal
		LOG.debug("[END]- Authenticating, generating and storing token");
		return cleanAuthentication(authenticated);
	}

	/**
	 * @param authentication
	 * @return
	 */
	private Authentication getAuthenticatedDetails(Authentication authentication) {
		return screenLockAuthenticationManager.authenticate(authentication);
	}

	/**
	 * @param token
	 */
	private void storeAuthenticatedPrincipal(AccessToken token) {
		persistedAccessTokenProvider.store(token);
	}
	
	/**
	 * @param authenticated
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Authentication cleanAuthentication(Authentication authenticated) {
		AccessToken accessToken = (AccessToken)authenticated.getPrincipal();
		return new ScreenLockAuthentication(accessToken.toResponseMap(), (Map<String, Object>)authenticated.getDetails());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return ScreenLockAuthentication.class.isAssignableFrom(authentication);
	}

}

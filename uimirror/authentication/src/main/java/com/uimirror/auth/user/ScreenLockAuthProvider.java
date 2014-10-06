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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.controller.AuthenticationProvider;

/**
 * Will be responsible to generate {@linkplain AccessToken} by validating the existing token,
 * then validate the provided password for the screen unlock.
 * 
 * it should adhere the methodology of {@linkplain AuthenticationManager#authenticate(Authentication)}
 * 
 * @author Jay
 */
public class ScreenLockAuthProvider extends UserAccessTokenProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(ScreenLockAuthProvider.class);
	private @Autowired AuthenticationManager screenLockAuthenticationManager;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public AccessToken getAuthenticationToken(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating or refreshing and storing token");
		//TODO steps should be from AuthenticationManager get the AccessToken, Deserilze to Authenticated details then
		// validate the entered screen lock password, if everything seems ok, then generate a new token and send back to the client
		//get the authenticated principal
		AccessToken accessToken = super.getValidToken(authentication);
		//AuthenticatedDetails authDetails = screenLockAuthenticationManager.authenticate(authentication);
		//Generate a Access Token
		//AccessToken accessToken = super.generateToken(authentication, authDetails);
		LOG.debug("[END]- Authenticating, generating or refreshing and storing token");
		return accessToken;
	}

}

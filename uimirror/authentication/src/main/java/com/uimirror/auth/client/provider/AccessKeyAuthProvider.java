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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;

/**
 * Will be responsible to generate {@linkplain AccessToken} depends on the scenario
 * like, if user has opted for the two factor authentication then it will 
 * generate token, process for gathering user contact details in parallel thread
 * and finally they will be separate thread to process the email and control will 
 * be back to the user, else it will generate the token and returned back 
 * 
 * @author Jay
 */
public class AccessKeyAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(AccessKeyAuthProvider.class);
	private @Autowired AuthenticationManager apiKeyAuthManager;
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating and storing token");
		//get the authenticated principal
		Authentication authDetails = apiKeyAuthManager.authenticate(authentication);
		//Update the refresh interval
		//authDetails = AuthenticationUpdaterUtil.updateRefreshPeriodIfNecessary(authentication, authDetails);
		//Generate a Access Token
		AccessToken accessToken = persistedAccessTokenProvider.generateToken(authDetails);
		//TODO populate the authentication object from the access Token
		//TODO check for _2FA level authentication, if 2FA process this mail processing in background and return back to the user
		LOG.debug("[END]- Authenticating, generating and storing token");
		return authentication;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}

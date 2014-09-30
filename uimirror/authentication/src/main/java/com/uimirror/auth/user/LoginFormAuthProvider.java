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
import com.uimirror.core.auth.bean.AuthenticatedDetails;
import com.uimirror.core.auth.bean.Authentication;

/**
 * Will be responsible to generate {@linkplain AccessToken} depends on the scenario
 * like, if user has opted for the two factor authentication then it will 
 * generate token, process for gathering user contact details in parallel thread
 * and finally they will be separate thread to process the email and control will 
 * be back to the user, else it will generate the token and store and returned back 
 * 
 * @author Jay
 */
public class LoginFormAuthProvider extends UserCommonAuthProvider{

	private static final Logger LOG = LoggerFactory.getLogger(LoginFormAuthProvider.class);
	private @Autowired AuthenticationManager loginFormAuthenticationManager;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public AccessToken getAuthenticateToken(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating and storing token");
		AuthenticatedDetails authDetails = loginFormAuthenticationManager.authenticate(authentication);
		LOG.debug("[END]- Authenticating, generating and storing token");
		return null;
	}

}

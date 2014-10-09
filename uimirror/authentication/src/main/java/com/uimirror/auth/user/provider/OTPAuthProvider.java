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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;

/**
 * Will be responsible to generate {@linkplain AccessToken} by validating the existing token,
 * then validate the provided OTP for the 2FA.
 * 
 * it should adhere the methodology of {@linkplain AuthenticationManager#authenticate(Authentication)}
 * 
 * @author Jay
 */
public class OTPAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(OTPAuthProvider.class);
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating or refreshing and storing token");
		//TODO steps should be from AccessTokenManager get the AccessToken, Deserilze to Authenticated details then
		// validate the entered otp, if everything seems ok, then generate a new token and send back to the client
		//get the authenticated principal
		//Check how you can do validation to make use of AuthenticationValidationService
		AccessToken accessToken = persistedAccessTokenProvider.getValidToken(authentication);
		//After getting the valid token match those details if matching generate a new token 
		//TODO think smartly how you can really generate a new token without changing existing architecture
		//AuthenticatedDetails authDetails = screenLockAuthenticationManager.authenticate(authentication);
		//Generate a Access Token
		//AccessToken accessToken = super.generateToken(authentication, authDetails);
		LOG.debug("[END]- Authenticating, generating or refreshing and storing token");
		return null;
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

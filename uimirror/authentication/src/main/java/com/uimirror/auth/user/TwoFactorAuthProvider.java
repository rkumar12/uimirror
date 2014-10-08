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

import com.uimirror.auth.bean.AccessToken;
import com.uimirror.auth.bean.Authentication;
import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.core.AuthenticationManager;

/**
 * Will be responsible to generate {@linkplain AccessToken} by validating the existing token,
 * then validate the provided OTP for the 2FA.
 * 
 * it should adhere the methodology of {@linkplain AuthenticationManager#authenticate(Authentication)}
 * 
 * @author Jay
 */
public class TwoFactorAuthProvider extends UserAccessTokenProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(TwoFactorAuthProvider.class);

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public AccessToken authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating or refreshing and storing token");
		//TODO steps should be from AccessTokenManager get the AccessToken, Deserilze to Authenticated details then
		// validate the entered otp, if everything seems ok, then generate a new token and send back to the client
		//get the authenticated principal
		//Check how you can do validation to make use of AuthenticationValidationService
		AccessToken accessToken = super.getValidToken(authentication);
		//After getting the valid token match those details if matching generate a new token 
		//TODO think smartly how you can really generate a new token without changing existing architecture
		//AuthenticatedDetails authDetails = screenLockAuthenticationManager.authenticate(authentication);
		//Generate a Access Token
		//AccessToken accessToken = super.generateToken(authentication, authDetails);
		LOG.debug("[END]- Authenticating, generating or refreshing and storing token");
		return accessToken;
	}

}

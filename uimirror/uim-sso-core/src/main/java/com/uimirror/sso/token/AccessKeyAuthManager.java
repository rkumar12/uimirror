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
package com.uimirror.sso.token;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.extra.MapException;
import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.auth.OAuth2Authentication;
import com.uimirror.sso.exception.AuthExceptionMapper;
import com.uimirror.sso.token.AccessTokenProvider;
import com.uimirror.ws.api.security.exception.AuthenticationException;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the provided access token is valid or not.
 * if valid details, it will return the Authenticated Details in principal {@linkplain Authentication}
 * 
 * @author Jay
 */
public class AccessKeyAuthManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(AccessKeyAuthManager.class);
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating access token");
		//Step 1- Authenticate and get the previous access Token, which has the user id and client info
		AccessToken token = authenticateAndGetToken(authentication);
		//Step 2- Mark the principal as the previous AccessToken
		Authentication authenticated = popultaeAuthenticatedPrinicpal(token);
		LOG.info("[END]- Authenticating access token");
		return authenticated;
	}

	/**
	 * Gets the previous {@link AccessToken} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private AccessToken authenticateAndGetToken(Authentication authentication){
		LOG.debug("[START]- Reteriving Access Token details issued to client eariller");
		Map<String, String> credentials = (Map<String, String>)authentication.getCredentials();
		AccessToken accessToken = getActiveToken(credentials.get(AuthConstants.ACCESS_TOKEN));
		LOG.debug("[END]- Reteriving Access Token details issued to client eariller");
		return accessToken;
	}
	
	/**
	 * Retrieves the {@link AccessToken} associated with the given token
	 * @param issuedToken
	 * @return
	 */
	private AccessToken getActiveToken(String issuedToken){
		return persistedAccessTokenProvider.getValid(issuedToken);
	}
	
	/**
	 * This will assign the principal to the authentication
	 * @param token
	 * @return
	 */
	private Authentication popultaeAuthenticatedPrinicpal(AccessToken token) {
		return new OAuth2Authentication(token);
	}

}

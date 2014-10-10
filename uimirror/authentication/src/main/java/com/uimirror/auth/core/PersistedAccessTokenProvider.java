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
package com.uimirror.auth.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.bean.AuthenticatedDetails;
import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.user.UserAccessTokenManager;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;

/**
 * This is the common implementation of the user authentication provider
 * such that any access token getting generated for the user should have one 
 * {@link AccessTokenManager} such as {@linkplain UserAccessTokenManager} and process 
 * to generate the {@link AccessToken}
 * 
 * @author Jay
 */
public class PersistedAccessTokenProvider implements AccessTokenProvider{

	protected static final Logger LOG = LoggerFactory.getLogger(PersistedAccessTokenProvider.class);
	
	private @Autowired AccessTokenManager userAccessTokenManager;
	

	/**
	 * This will generate a new {@linkplain AccessToken} using
	 * {@linkplain AccessTokenManager#generateToken(Authentication, AuthenticatedDetails)}
	 * 
	 * @param auth
	 * @param authDetails
	 * @return
	 */
	@Override
	public AccessToken generateToken(Authentication auth) {
		LOG.debug("[SINGLE]- Generating the AccessToken based on the Authentications");
		//TODO rectify this first
		return userAccessTokenManager.generateToken(auth, null);
	}
	
	/**
	 * Will return the valid {@link AccessToken} based on the {@link Authentication}
	 * if the provided details are valid will return the {@linkplain AccessToken} else <code>null</code>
	 * @param auth
	 * @return
	 */
	@Override
	public AccessToken getValidToken(String token){
		LOG.debug("[SINGLE]- Validating the details provided for the accesstoken");
		return null;//userAccessTokenManager.getValidToken(auth);
	}
	

}

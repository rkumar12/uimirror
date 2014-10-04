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

import com.uimirror.core.auth.AccessTokenManager;
import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.AuthenticatedDetails;
import com.uimirror.core.auth.bean.Authentication;

/**
 * This is the common implementation of the user authentication provider
 * such that any access token getting generated for the user should have one 
 * {@link AccessTokenManager} such as {@linkplain UserAccessTokenManager} and process 
 * to generate the {@link AccessToken}
 * 
 * @author Jay
 */
public class UserAccessTokenProvider{

	protected static final Logger LOG = LoggerFactory.getLogger(UserAccessTokenProvider.class);
	
	private @Autowired AccessTokenManager userAccessTokenManager;
	

	/**
	 * This will generate a new {@linkplain AccessToken} using
	 * {@linkplain AccessTokenManager#generateToken(Authentication, AuthenticatedDetails)}
	 * 
	 * @param auth
	 * @param authDetails
	 * @return
	 */
	public AccessToken generateToken(Authentication auth, AuthenticatedDetails authDetails) {
		LOG.debug("[SINGLE]- Generating the AccessToken based on the Authentications");
		return userAccessTokenManager.generateToken(auth, authDetails);
	}
	

}

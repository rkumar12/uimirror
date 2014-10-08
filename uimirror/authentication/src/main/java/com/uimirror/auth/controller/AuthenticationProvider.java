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
package com.uimirror.auth.controller;

import com.uimirror.auth.bean.AccessToken;
import com.uimirror.auth.bean.Authentication;
import com.uimirror.auth.core.AccessTokenManager;
import com.uimirror.auth.core.AuthenticationManager;

/**
 * Handles the authentication request, and process the authentication
 * using {@linkplain AuthenticationManager#authenticate(Authentication)}
 * or using {@linkplain AccessTokenManager}
 * on successful authentication a {@linkplain AccessToken} get issued to the 
 * client/user
 *   
 * @author Jay
 */
public interface AuthenticationProvider {

	/**
	 * Should process the Authentication first, then do the necessary 
	 * steps like storing the token and finally returns the {@linkplain AccessToken}
	 * This should honor the process of {@linkplain AuthenticationManager#authenticate(Authentication)}
	 * rules.
	 * 
	 * @param authentication
	 * @return
	 */
	AccessToken getAuthenticationToken(Authentication authentication);
}

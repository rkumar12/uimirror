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
package com.uimirror.core.auth.controller;

import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.Authentication;

/**
 * Handles the authentication request, and process the authentication
 * using {@linkplain AuthenticationManager#authenticate(Authentication)}
 * on successful authentication a {@linkplain AccessToken} get issued to the 
 * client/user
 *   
 * @author Jay
 */
public interface AuthenticationProvider {

	/**
	 * Should process the Authentication first, then do the necessary 
	 * steps like storing the token and finally returns the {@linkplain Authentication}
	 * This should honor the process of {@linkplain AuthenticationManager#authenticate(Authentication)}
	 * rules.
	 * 
	 * @param authentication
	 * @return
	 */
	AccessToken getAuthenticateToken(Authentication authentication);
}

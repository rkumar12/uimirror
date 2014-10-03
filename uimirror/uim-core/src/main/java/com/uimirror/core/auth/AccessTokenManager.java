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
package com.uimirror.core.auth;

import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.AuthenticatedDetails;
import com.uimirror.core.auth.bean.Authentication;

/**
 * Provides a common solution for granting a new {@link AccessToken}.
 * This may authenticate the details before granting a new token.
 *  
 * @author Jay
 */
public interface AccessTokenManager {

	/**
	 * Will generate a new {@link AccessToken} for the requested client/user.
	 * This will store the generated token for the further reference.
	 * @param auth
	 * @param authDetails
	 * @return
	 */
	AccessToken generateToken(Authentication auth, AuthenticatedDetails authDetails);
	
	/**
	 * Checks if the token supplied is not yet expired.
	 * @param token
	 * @return
	 */
	boolean isValidToken(Authentication auth);
	
	/**
	 * This will expire the given token by de-crypting.
	 * @param token
	 * @param async specifies if this task needs to in a async manner or regular way
	 */
	void markAsExpired(Authentication auth, boolean async);
	
	/**
	 * This will expire the given token.
	 * @param token
	 * @param async specifies if this task needs to in a async manner or regular way
	 */
	void markAsExpired(String token, boolean async);
	
	/**
	 * Will validate the given token and refresh the token if required, i.e refresh period is approaching or 
	 * token expires below 5 mins.
	 * This will store the generated token for the further reference.
	 * 
	 * @param token
	 * @return <code>null</code> if no valid token else a new Token
	 */
	AccessToken validateAndRefreshToken(Authentication auth);
	
}

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

/**
 * Provides a common solution for all the token  
 * generation strategy.
 *  
 * @author Jay
 */
public interface AccessTokenManager {

	/**
	 * Will generate a new {@link AccessToken} for the requested client/user.
	 * This will store the generated token for the further reference.
	 * @param auth
	 * @param id profileid/clientid of the user/client
	 * @param details
	 * @return
	 */
	AccessToken generateToken(Authentication auth, String id);
	
	/**
	 * Checks if the token supplied is not yet expired.
	 * @param token
	 * @return
	 */
	boolean isValidToken(AccessToken token);
	
	/**
	 * This will expire the given token.
	 * @param token
	 */
	void markAsExpired(Token token);
}

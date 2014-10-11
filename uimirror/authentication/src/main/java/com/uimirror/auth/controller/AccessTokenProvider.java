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

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;

/**
 * An {@link AccessToken} provider which takes care about the different type of generation of the token
 * 
 * @author Jay
 */
public interface AccessTokenProvider {
	
	/**
	 * Based on the {@link Authentication} principal, it will try to create a {@link AccessToken}
	 * 
	 * @param token
	 * @return
	 */
	public void store(AccessToken token);
	
	
	/**
	 * Gets the valid token from the token ID specified.
	 * @param token
	 * @return
	 */
	public AccessToken getValid(String token);
	
	/**
	 * Gets the valid token from the token ID specified.
	 * @param token
	 * @return
	 */
	public AccessToken get(String token);

}

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

import com.uimirror.auth.AbstractAuthProvider;
import com.uimirror.core.auth.AccessTokenManager;
import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.AuthenticatedDetails;
import com.uimirror.core.auth.bean.Authentication;

/**
 * This is the common implementation of the user authentication provider
 * such that any access token getting generated for the user should have one 
 * {@link AccessTokenManager}
 * @author Jay
 */
public abstract class UserAuthProvider extends AbstractAuthProvider{


	/* (non-Javadoc)
	 * @see com.uimirror.auth.AbstractAuthProvider#generateAccessToken(com.uimirror.core.auth.bean.Authentication, com.uimirror.core.auth.bean.AuthenticatedDetails)
	 */
	@Override
	public AccessToken generateAccessToken(Authentication authentication, AuthenticatedDetails authDetails) {
		// TODO Auto-generated method stub
		//TODO implement this logic first 
		return null;
	}

}

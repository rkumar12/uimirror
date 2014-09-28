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
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.dao.AccessTokenStoreFactory;

/**
 * @author Jay
 */
public class DefaultAccessTokenManager extends AccessTokenGeneratorManager{

	public DefaultAccessTokenManager(AccessTokenStoreFactory accessTokenStoreFactory) {
		super(accessTokenStoreFactory);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessTokenManager#isValidToken(com.uimirror.core.auth.Authentication)
	 */
	@Override
	public boolean isValidToken(Authentication auth) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessTokenManager#validateAndRefreshToken(com.uimirror.core.auth.Authentication)
	 */
	@Override
	public AccessToken validateAndRefreshToken(Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}
	

}

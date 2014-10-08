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
package com.uimirror.auth.manager;

import com.uimirror.auth.bean.AccessToken;
import com.uimirror.auth.bean.Authentication;
import com.uimirror.auth.dao.AccessTokenStore;

/**
 * @author Jay
 */
public abstract class AbstractAccessTokenValidateManager extends AbstractAccessTokenExpireManager{

	/**
	 * @param store
	 */
	public AbstractAccessTokenValidateManager(AccessTokenStore store) {
		super(store);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessTokenManager#validateAndRefreshToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public AccessToken validateAndRefreshToken(Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessTokenManager#getValidToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public AccessToken getValidToken(Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

}

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

import com.uimirror.auth.bean.AccessToken;
import com.uimirror.auth.bean.AuthenticatedDetails;
import com.uimirror.auth.bean.Authentication;
import com.uimirror.auth.dao.AccessTokenStore;
import com.uimirror.auth.manager.AbstractAccessTokenValidateManager;

/**
 * AccessTokenManager that will be responsible to generate the {@linkplain AccessToken} 
 * only for the user
 * @author Jay
 */
public class UserAccessTokenManager extends AbstractAccessTokenValidateManager{

	/**
	 * @param store
	 */
	public UserAccessTokenManager(AccessTokenStore store) {
		super(store);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessTokenManager#generateToken(com.uimirror.core.auth.bean.Authentication, com.uimirror.core.auth.bean.AuthenticatedDetails)
	 */
	@Override
	public AccessToken generateToken(Authentication auth, AuthenticatedDetails authDetails) {
		// TODO Auto-generated method stub
		return null;
	}

	

}

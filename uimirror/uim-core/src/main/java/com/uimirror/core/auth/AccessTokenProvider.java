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

import org.springframework.util.Assert;

import com.uimirror.core.auth.dao.AccessTokenStore;
import com.uimirror.core.auth.dao.AccessTokenStoreFactory;

/**
 * Common Implementation to hold the {@link AccessTokenStore}
 * 
 * @author Jay
 */
public abstract class AccessTokenProvider implements AccessTokenManager{

	private final AccessTokenStoreFactory accessTokenStoreFactory;

	public AccessTokenProvider(AccessTokenStoreFactory accessTokenStoreFactory) {
		this.accessTokenStoreFactory = accessTokenStoreFactory;
	}

	/**
	 * Gives which access token store {@link AccessTokenStore} to be used for all the database operation
	 * @param name
	 * @return
	 */
	protected AccessTokenStore getAccessTokenStore(String name){
		Assert.notNull(name, "AccessToken Store name can't be empty");
		return this.accessTokenStoreFactory.getStore(name);
	}
}

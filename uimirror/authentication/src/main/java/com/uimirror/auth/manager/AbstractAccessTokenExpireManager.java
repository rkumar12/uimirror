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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.uimirror.auth.core.AccessTokenManager;
import com.uimirror.auth.dao.AccessTokenStore;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;

/**
 * An implementation for the {@linkplain AccessToken} expiry handler
 * @author Jay
 */
public abstract class AbstractAccessTokenExpireManager implements AccessTokenManager {

	protected static final Logger LOG = LoggerFactory.getLogger(AccessTokenManager.class);
	private AccessTokenStore store;

	/**
	 * 
	 * @param store
	 */
	public AbstractAccessTokenExpireManager(AccessTokenStore store) {
		Assert.notNull(store, "AccessTokenStore Can't be empty");
		this.store = store;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessTokenManager#markAsExpired(com.uimirror.core.auth.bean.Authentication, boolean)
	 */
	@Override
	public void markAsExpired(Authentication auth, boolean async) {
		validateParam(auth);
		
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessTokenManager#markAsExpired(java.lang.String, boolean)
	 */
	@Override
	public void markAsExpired(String token, boolean async) {
		validateParam(token);
	}
	
	private void validateParam(Object obj){
		Assert.notNull(obj, "Authetication to expiry can't be empty");
	}

	public AccessTokenStore getStore() {
		return store;
	}
	
}

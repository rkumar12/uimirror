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

import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.dao.AccessTokenStoreFactory;

/**
 * This gives the implementation for the expiration logic part, keeping other part to be implemented by
 * other application specific.
 * 
 * @author Jay
 */
public abstract class AccessTokenExpirerManager extends AccessTokenProvider{

	public AccessTokenExpirerManager(AccessTokenStoreFactory accessTokenStoreFactory) {
		super(accessTokenStoreFactory);
	}
	
	@Override
	public void markAsExpired(Authentication auth, boolean async){
		//TODO implement the logic for the expiry
	}
	
	@Override
	public void markAsExpired(String token, boolean async){
		//TODO implement the logic for the expiry
	}

}

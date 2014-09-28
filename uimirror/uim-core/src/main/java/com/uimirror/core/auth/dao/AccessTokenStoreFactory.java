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
package com.uimirror.core.auth.dao;

import com.uimirror.core.auth.bean.AccessToken;

/**
 * A Factory for the AccessTokenStore to get the exact database which needs 
 * to be used for the {@link AccessToken}
 * @author Jay
 */
public interface AccessTokenStoreFactory {

	/**
	 * This will give which {@link AccessTokenStore} to be used
	 * based on the parameter.
	 * 
	 * @param name
	 * @return
	 */
	AccessTokenStore getStore(String name);
}

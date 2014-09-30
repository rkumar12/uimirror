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

import com.uimirror.auth.AuthStoreProvider;
import com.uimirror.core.auth.bean.AccessToken;

/**
 * provides a common interface for all the user genereated token to be stored in 
 * one store.
 * @author Jay
 */
public abstract class UserCommonAuthProvider implements AuthStoreProvider{

	public void storeToken(AccessToken token){
		//TODO Implement this latter
	}

}

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
package com.uimirror.ws.api.security.conf;

import com.mongodb.DBCollection;

/**
 * <p>Any class which is implementing this should able to resolve to
 * give a access-token collection so that access token activities can take place</p>
 * @author Jay
 */
public interface SecurityAccessTokenDataStore {

	/**
	 * <p>This gives the connection for the token collection</p>
	 * <p>An implementing class should able to resolve this,
	 *  so that any token related activities can take place.</p>
	 * @return
	 */
	public DBCollection getTokenStoreCollection();
	
	/**
	 * <p>Destroy the connection already existed</p>
	 */
	public void destroy();
}

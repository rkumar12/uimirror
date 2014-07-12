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
package com.uimirror.ws.api.security.service;

import com.uimirror.ws.api.security.base.ClientSession;

/**
 * @author Jayaram
 *
 */
public interface ClientSecurityService {

	/**
	 * <p>This will load the client session from the cache or the storage
	 * system, based on the availability by apiKey</p>
	 * @param apiKey
	 * @return
	 */
	public ClientSession getClientSession(final String apiKey);
}

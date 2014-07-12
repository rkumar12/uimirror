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
package com.uimirror.ws.api.security;

import com.uimirror.ws.api.security.base.Client;
import com.uimirror.ws.api.security.base.ClientAccessInfo;
import com.uimirror.ws.api.security.base.ClientSession;

/**
 * <p>This wil old uimirror ws client access info</p>
 * @author Jayaram
 */
public final class UimClientSession extends ClientSession{

	private static final long serialVersionUID = -4597676136856201630L;

	/**
	 * @param client
	 * @param clientAccessInfo
	 */
	public UimClientSession(Client client, ClientAccessInfo clientAccessInfo) {
		super(client, clientAccessInfo);
	}

}

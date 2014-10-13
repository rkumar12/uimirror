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

import com.uimirror.core.auth.Scope;

/**
 * Basic class that user has the granted access to the client
 * it specifies which type of access user has granted
 * @author Jay
 */
public class ClientAuthorizedScope{

	private String clientId;
	private Scope scope;

	public ClientAuthorizedScope(String clientId, Scope scope) {
	
		super();
		this.clientId = clientId;
		this.scope = scope;
	}
	public String getClientId() {
		return clientId;
	}
	public Scope getScope() {
		return scope;
	}
}
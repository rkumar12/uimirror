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

import java.util.Set;

/**
 * <p>Bean to hold all the client info of uimirror
 * <p>Class has been marked as immutable </p>
 * <code>{@link Client}</code>
 * @author Jayaram
 */
public final class UimClient extends Client {

	private static final long serialVersionUID = -1347152923071428219L;

	/**
	 * @param uimClientDetails
	 * @param roles
	 */
	public UimClient(UimClientDetails uimClientDetails, Set<Role> roles) {
		super(uimClientDetails, roles);
	}

	/**
	 * @param apiKey
	 * @param uimClientDetails
	 * @param createdOn
	 * @param roles
	 * @param isActive
	 */
	public UimClient(String apiKey, UimClientDetails uimClientDetails,
			long createdOn, Set<Role> roles, boolean isActive) {
		super(apiKey, uimClientDetails, createdOn, roles, isActive);
	}
	
}

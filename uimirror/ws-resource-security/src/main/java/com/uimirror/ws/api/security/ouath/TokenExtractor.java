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
package com.uimirror.ws.api.security.ouath;

import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * @author Jay
 */
public interface TokenExtractor {

	/**
	 * Extract a token value from an incoming request without authentication.
	 * 
	 * @param request the current ServletRequest
	 * @return an authentication token whose principal is an access token (or null if there is none)
	 */
	public Principal extract(ContainerRequestContext request); 
}

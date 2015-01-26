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


/**
 * This extracts the ouath token from the header value or from the query param
 * @author Jay
 */
public interface TokenExtractor {

	/**
	 * Extract a token value from an incoming request without authentication.
	 * 
	 * @param header the current token from header
	 * @param queryParam accessToken in the query param
	 * @return an authentication token whose principal is an access token (or null if there is none)
	 */
	public String extract(String header, String queryParam); 
}

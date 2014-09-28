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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * {@link TokenExtractor} that strips the authenticator from a bearer token request (with an Authorization header in the
 * form "Bearer <code><TOKEN></code>", or as a request parameter if that fails). The access token is the principal in
 * the authentication token that is extracted.
 * @author Jay
 */
public class BearerTokenExtractor implements TokenExtractor {

	protected static final Logger LOG = LoggerFactory.getLogger(BearerTokenExtractor.class);

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.ouath.TokenExtractor#extract(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public String extract(String from) {
		return extractToken(from);
	}
	
	/**
	 * Extracts the access token from the header parameters
	 * @param from
	 * @return
	 */
	public static String extractAccessToken(String from) {
		return extractToken(from);
	}

	/**
	 * Extract the OAuth bearer token from a header.
	 * 
	 * @param from The request.
	 * @return The token, or null if no OAuth authorization header was supplied.
	 */
	protected static String extractToken(String from) {
		if(StringUtils.hasText(from)){
			String authHeaderValue = from.substring(SecurityConstants.BEARER.length()).trim();
			int commaIndex = authHeaderValue.indexOf(',');
			if (commaIndex > 0) {
				authHeaderValue = authHeaderValue.substring(0, commaIndex);
			}
			return authHeaderValue;
		}
		return null;
	}

}

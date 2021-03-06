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
 * form "Bearer TOKEN", or as a request parameter if that fails). The access token is the principal in
 * the authentication token that is extracted.
 * @author Jay
 */
public class BearerTokenExtractor implements TokenExtractor {

	protected static final Logger LOG = LoggerFactory.getLogger(BearerTokenExtractor.class);

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.ouath.TokenExtractor#extract(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public String extract(String header, String queryParam) {
		return extractAccessToken(header, queryParam);
	}
	
	/**
	 * Extracts the access token from the header parameters
	 * @param header from where token will be extracted
	 * @param queryParam query param from which token extracted if not present in header
	 * @return ouath2 token if found else null
	 */
	public static String extractAccessToken(String header, String queryParam) {
		// first check the header...
		String token = extractHeaderToken(header);
		// bearer type allows a request parameter as well
		token = (token == null ? queryParam : token); 
		if (token == null) {
			LOG.debug("Token not found in request parameters.  Not an OAuth2 request.");
		}
		return token;
	}

	/**
	 * Extract the OAuth bearer token from a header.
	 * @param header header from where token will be extracted
	 * @return extracted token else <code>null</code>
	 */
	protected static String extractHeaderToken(String header) {
		if(StringUtils.hasText(header)){
			String authHeaderValue = header.substring(SecurityConstants.BEARER.length()).trim();
			int commaIndex = authHeaderValue.indexOf(',');
			if (commaIndex > 0) {
				authHeaderValue = authHeaderValue.substring(0, commaIndex);
			}
			return authHeaderValue;
		}
		return null;
	}

}

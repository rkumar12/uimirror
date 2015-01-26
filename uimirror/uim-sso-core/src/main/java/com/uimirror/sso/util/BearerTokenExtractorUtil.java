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
package com.uimirror.sso.util;

import static com.uimirror.sso.SecurityFieldConstants.AUTHORIZATION;
import static com.uimirror.core.auth.AuthConstants.ACCESS_TOKEN;
import static com.uimirror.core.auth.SecurityConstants.BEARER;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Extracts the bearer token from {@link ContainerRequestContext}
 * This will basically comes to use, when using with filters as security check points to get the 
 * authorization token from the header.
 * @author Jay
 */
public class BearerTokenExtractorUtil {

	private static Logger LOG = LoggerFactory.getLogger(BearerTokenExtractorUtil.class);
	
	//Prevent new instance
	private BearerTokenExtractorUtil() {
		//NOP
	}
	
	public static String extract(ContainerRequestContext request) {
		return extractToken(request);
	}
	
	private static String extractToken(ContainerRequestContext request) {
		// first check the header...
		String token = extractHeaderToken(request);

		// bearer type allows a request parameter as well
		if (token == null) {
			LOG.debug("Token not found in headers. Trying request parameters.");
			MultivaluedMap<String, String> params = request.getUriInfo().getQueryParameters();
			if(!CollectionUtils.isEmpty(params) && params.containsKey(ACCESS_TOKEN)){
				token = params.getFirst(ACCESS_TOKEN);
				if (token == null) {
					LOG.debug("Token not found in request parameters.  Not an OAuth2 request.");
				}
			}
		}

		return token;
	}
	
	/**
	 * Extract the OAuth bearer token from a header.
	 * 
	 * @param request The request.
	 * @return The token, or null if no OAuth authorization header was supplied.
	 */
	private static String extractHeaderToken(ContainerRequestContext request) {
		final String token = request.getHeaderString(AUTHORIZATION);
		if(StringUtils.hasText(token)){
			String authHeaderValue = token.substring(BEARER.length()).trim();
			int commaIndex = authHeaderValue.indexOf(',');
			if (commaIndex > 0) {
				authHeaderValue = authHeaderValue.substring(0, commaIndex);
			}
			return authHeaderValue;
		}
		return null;
	}

}

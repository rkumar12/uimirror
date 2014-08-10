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
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.ws.api.security.common.SecurityFieldConstants;

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
	public Principal extract(ContainerRequestContext request) {
		String tokenValue = extractToken(request);
		if (tokenValue != null) {
			Principal authentication = new PreAuthenticatedAuthenticationToken(tokenValue);
			return authentication;
		}
		return null;
	}
	
	protected String extractToken(ContainerRequestContext request) {
		// first check the header...
		String token = extractHeaderToken(request);

		// bearer type allows a request parameter as well
		if (token == null) {
			LOG.debug("Token not found in headers. Trying request parameters.");
			MultivaluedMap<String, String> params = request.getUriInfo().getQueryParameters();
			if(!CollectionUtils.isEmpty(params) && params.containsKey(UIMSecurityContext.ACCESS_TOKEN)){
				token = params.getFirst(UIMSecurityContext.ACCESS_TOKEN);
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
	protected String extractHeaderToken(ContainerRequestContext request) {
		final String token = request.getHeaderString(SecurityFieldConstants.AUTHORIZATION);
		if(StringUtils.hasText(token)){
			String authHeaderValue = token.substring(UIMSecurityContext.BEARER.length()).trim();
			int commaIndex = authHeaderValue.indexOf(',');
			if (commaIndex > 0) {
				authHeaderValue = authHeaderValue.substring(0, commaIndex);
			}
			return authHeaderValue;
		}
		return null;
	}

}

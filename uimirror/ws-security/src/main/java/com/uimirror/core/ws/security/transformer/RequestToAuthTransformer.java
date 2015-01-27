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
package com.uimirror.core.ws.security.transformer;

import static com.uimirror.core.Constants.IP;
import static com.uimirror.core.Constants.USER_AGENT;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.SecurityConstants;
import com.uimirror.core.rest.extra.UnAuthorizedException;
import com.uimirror.core.service.TransformerService;
import com.uimirror.sso.SecurityFieldConstants;
import com.uimirror.sso.auth.OAuth2Authentication;

/**
 * A transformer implementation which will transform the {@linkplain ContainerRequestContext}
 * to {@linkplain OAuth2Authentication}
 * @author Jay
 */
public class RequestToAuthTransformer implements TransformerService<ContainerRequestContext, OAuth2Authentication>{

	protected static final Logger LOG = LoggerFactory.getLogger(RequestToAuthTransformer.class);

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public OAuth2Authentication transform(ContainerRequestContext src) {
		Assert.notNull(src, "Source Can't be empty");
		String ip = src.getHeaderString(IP);
		String host = src.getHeaderString(USER_AGENT);
		return new OAuth2Authentication(extractAccessToken(src), ip, host);
	}
	
	private String extractAccessToken(ContainerRequestContext src){
		String tokenValue = extractToken(src);
		if (tokenValue == null) 
			throw new UnAuthorizedException();
		return tokenValue;
	}
	
	protected String extractToken(ContainerRequestContext request) {
		// first check the header...
		String token = extractHeaderToken(request);
		// bearer type allows a request parameter as well
		if (token == null) {
			MultivaluedMap<String, String> params = request.getUriInfo().getQueryParameters();
			if(!CollectionUtils.isEmpty(params) && params.containsKey(AuthConstants.ACCESS_TOKEN)){
				token = params.getFirst(AuthConstants.ACCESS_TOKEN);
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
			String authHeaderValue = token.substring(SecurityConstants.BEARER.length()).trim();
			int commaIndex = authHeaderValue.indexOf(',');
			if (commaIndex > 0) {
				authHeaderValue = authHeaderValue.substring(0, commaIndex);
			}
			return authHeaderValue;
		}
		return null;
	}

}

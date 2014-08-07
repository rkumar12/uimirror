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
package com.uimirror.ws.api.security.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.ws.api.security.bean.base.AccessToken;
import com.uimirror.ws.api.security.common.SecurityFieldConstants;
import com.uimirror.ws.api.security.ouath.UIMSecurityContext;
import com.uimirror.ws.api.security.ouath.UIMirrorSecurity;
import com.uimirror.ws.api.security.service.AccessTokenService;

/**
 * <p>This is the common resource filter to populate the access token details.</p>
 * <p>This intercepts the header and took the bearer token, decode it and populate the token details</p>
 * <p>if token details are not valid then it will send invalid response directly from here.</p>
 * @author Jay
 */
@Priority(Priorities.AUTHORIZATION)
public class SecurityContextFilter implements ContainerRequestFilter{
	
	protected static final Logger LOG = LoggerFactory.getLogger(SecurityContextFilter.class);
	
	private final AccessTokenService accessTokenService;
	
	/**
	 *<p>This will initialize the access token service, which will help to populate the token details to validate details</p>
	 *@param accessTokenService
	 *@throws IllegalStateException in case accessTokenService is null 
	 */
	public SecurityContextFilter(AccessTokenService accessTokenService) {
		if(accessTokenService == null)
			throw new IllegalStateException("Access Token Service should have been initialized before.");
		this.accessTokenService = accessTokenService;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void filter(ContainerRequestContext request) throws IOException {
		LOG.debug("[AUTH-START]-Request Getting Intercepted");
		// Get session id from request header
		final String token = request.getHeaderString(SecurityFieldConstants.AUTHORIZATION);
		//TODO get the token details
		//TODO handle exception here
		AccessToken accessToken = accessTokenService.getActiveAccessTokenDetailsByTokenId(token);
		request.setProperty(SecurityFieldConstants._AUTH_SCHEME, UIMSecurityContext.BEARER);
		//TODO work on the URI info
		request.setSecurityContext(new UIMirrorSecurity(accessToken, request.getUriInfo()));
		LOG.debug("[AUTH-END]-Request Getting Intercepted");
		
	}

}

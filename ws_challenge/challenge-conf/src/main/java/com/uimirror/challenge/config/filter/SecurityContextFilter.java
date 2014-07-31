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
package com.uimirror.challenge.config.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.challenge.config.Constants;
import com.uimirror.ws.api.security.bean.base.AccessToken;
import com.uimirror.ws.api.security.ouath.UIMSecurityContext;
import com.uimirror.ws.api.security.ouath.UIMirrorSecurity;
import com.uimirror.ws.api.security.service.ClientSecurityService;

/**
 * @author Jayaram
 *
 */
//@PreMatching
@Priority(Priorities.AUTHORIZATION)
public class SecurityContextFilter implements ContainerRequestFilter{
	protected static final Logger LOG = LoggerFactory.getLogger(SecurityContextFilter.class);
	
	public SecurityContextFilter(){
		//TODO DO Some initialization
	}
	
	@Autowired
	private ClientSecurityService clientSecurityService;
	
	@Override
	public void filter(final ContainerRequestContext request) throws IOException {
		LOG.debug("[AUTH]-Request Getting Intercepted");
		// Get session id from request header
		final String token = request.getHeaderString(Constants.AUTHORIZATION);
//		Session session = null;
		AccessToken tokenDetails = clientSecurityService.getAccessTokenDetails(token);
		// Get the session details from the data base or cache
		//session = userAuthenticationService.getUserSessionByAPIKey(apiKey);
		request.setProperty("authScheme", UIMSecurityContext.BEARER);
		request.setSecurityContext(new UIMirrorSecurity(new AccessToken(null, null, null, null, null, null), request.getUriInfo()));

	}

}

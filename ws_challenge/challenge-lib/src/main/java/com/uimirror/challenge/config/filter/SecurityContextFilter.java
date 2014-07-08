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

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author Jayaram
 *
 */
public class SecurityContextFilter implements ContainerRequestFilter{
	protected static final Logger LOG = LoggerFactory.getLogger(SecurityContextFilter.class);
	
	public SecurityContextFilter(){
		//TODO DO Some initialization
	}
	
	@Override
	public void filter(final ContainerRequestContext request) throws IOException {
		LOG.debug("[AUTH]-Request Getting Intercepted");
		// Get session id from request header
//		final String apiKey = request.getHeaderString(API_KEY);
//		Session session = null;
//		if (!StringUtils.hasText(apiKey)) {
//			// Get the session details from the data base or cache
//			session = userAuthenticationService.getUserSessionByAPIKey(apiKey);
//		}
//		request.setSecurityContext(new UIMirrorSecurityContext(session));

	}

}

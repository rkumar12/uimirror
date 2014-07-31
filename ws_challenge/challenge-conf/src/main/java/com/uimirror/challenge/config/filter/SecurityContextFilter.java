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
import javax.ws.rs.container.PreMatching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.uimirror.challenge.config.Constants;
import com.uimirror.ws.api.security.UIMirrorSecurityContext;
import com.uimirror.ws.api.security.base.ClientSession;
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
		final String apiKey = request.getHeaderString(Constants.API_KEY);
		System.out.println("outh2"+request.getHeaderString("Authorization"));
//		Session session = null;
		if (!StringUtils.hasText(apiKey)) {
			ClientSession session = clientSecurityService.getClientSession(apiKey);
			// Get the session details from the data base or cache
			//session = userAuthenticationService.getUserSessionByAPIKey(apiKey);
			request.setSecurityContext(new UIMirrorSecurityContext(session));
			//TODO analyse session Like DOes he have crossed daily and monthly limit etc
		}

	}

}

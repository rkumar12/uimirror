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
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.Priorities;
import com.uimirror.ws.api.security.service.PrincipalService;

/**
 * <p>This will populate and validate the token details</p>
 * @author Jay
 */
@Priority(Priorities.TOKENVALIDATION)
public class AccessTokenValidationFilter implements ContainerRequestFilter{

	//TODO Fix this if constructor way its not working
	private final PrincipalService principalService;
	
	public AccessTokenValidationFilter(PrincipalService principalService) {
		this.principalService = principalService;
	}
	
	protected static final Logger LOG = LoggerFactory.getLogger(AccessTokenValidationFilter.class);
	/* (non-Javadoc)
	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void filter(ContainerRequestContext request) throws IOException {
		LOG.debug("[AUTH-START]-Request Getting Intercepted for access token validation");
		principalService.populateTokenOnlySecurityConext(request);
		LOG.debug("[AUTH-END]-Request Getting Intercepted for access token validation");
	}

}

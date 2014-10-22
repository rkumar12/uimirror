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
package com.uimirror.ws.api.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Simple CORS Filter to be added to the response</p>
 * @author Jay
 */
public class UimCORSFilter implements ContainerResponseFilter{
	
	protected static final Logger LOG = LoggerFactory.getLogger(UimCORSFilter.class);

	private static final String CONT_ACC_AUTH_API = "Content-Type, Accept, Authorization, access_code";
	private static final String GET_POST_DELETE = "GET, POST, DELETE, PUT, OPTIONS";
	private static final String ALL = "*";
	private static final String ACCESS_C_A_O = "Access-Control-Allow-Origin";
    private static final String ACCESS_C_A_M = "Access-Control-Allow-Methods";
    private static final String ACCESS_C_A_H = "Access-Control-Allow-Headers";
    private static final String ACCESS_C_M_A = "Access-Control-Max-Age";
    private static final String ACCESS_C_MAX_AGE = "3600";
    private static final String CONTENT_LANG = "Content-Language"; 
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext cResponse) throws IOException {
		LOG.debug("[START]-Putting cross region parameters to response");
		cResponse.getHeaders().putSingle(ACCESS_C_A_O, ALL);
		cResponse.getHeaders().putSingle(ACCESS_C_A_M, GET_POST_DELETE);
		cResponse.getHeaders().putSingle(ACCESS_C_A_H, CONT_ACC_AUTH_API);
		cResponse.getHeaders().putSingle(ACCESS_C_M_A, ACCESS_C_MAX_AGE);
		cResponse.getHeaders().putSingle(CONTENT_LANG, "en_US");
		LOG.debug("[END]-Putting cross region parameters to response");
	}

}

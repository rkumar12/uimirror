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

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.uimirror.core.auth.AuthConstants;

/**
 * This response filter will put back the authorization code to the caller in the head section
 * caller needs to handle it.
 * 
 * @author Jayaram
 */
public class AccessTokenResponseFilter implements ContainerResponseFilter{
    
    protected static final Logger LOG = LoggerFactory.getLogger(AccessTokenResponseFilter.class);

    public AccessTokenResponseFilter() {
    }

	@Override
	public void filter(ContainerRequestContext cRequest, ContainerResponseContext cResponse) throws IOException {
		LOG.info("[START]-Adding the response body token details to send back to the caller");
		String access_token = cRequest.getHeaderString(AuthConstants.AUTHORIZATION_TOKEN);
		String pp = cRequest.getHeaderString(AuthConstants.TOKEN_ENCRYPTION_STARTEGY);
		if(StringUtils.hasText(access_token)){
			cResponse.getHeaders().remove(AuthConstants.AUTHORIZATION_TOKEN);
			cResponse.getHeaders().putSingle(AuthConstants.AUTHORIZATION_TOKEN, access_token);
			if(StringUtils.hasText(pp)){
				cResponse.getHeaders().remove(AuthConstants.TOKEN_ENCRYPTION_STARTEGY);
				cResponse.getHeaders().putSingle(AuthConstants.TOKEN_ENCRYPTION_STARTEGY, pp);
			}
		}
		LOG.info("[END]-Adding the response body token details to send back to the caller");
	}


}
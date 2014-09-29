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
package com.uimirror.auth.endpoint;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.form.AccessKeyHeaderAuthenticationForm;

/**
 * Controller which will be for the common path, any 
 * request that needs to be validate using the {@link AccessToken}
 * 
 * @author Jay
 */
@Path(AuthenticationEndPointConstant.ACCESS_HOME_PATH)
public class AccessTokenAuthenticationEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(AccessTokenAuthenticationEndPoint.class);
	public AccessTokenAuthenticationEndPoint() {
	}
	
	/**
	 * Validate the given access token and returns 
	 * true if valid with status code as 304
	 * This can also generate a new token if necessary with the status code
	 * as 200
	 * 
	 * @param form
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.ACCESS_TOKEN_VALIDATION_PATH)
	public Object doValidate(@BeanParam AccessKeyHeaderAuthenticationForm form){
		LOG.info("[ENTRY]- Received requst for access key validation");
		LOG.info("[EXIT]- Received requst for access key validation");
		return null;
	}

}

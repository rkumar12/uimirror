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
package com.uimirror.account.endpoint;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.account.create.bean.UserRegisterFormBean;

/**
 * Controller which will be for the common path, any 
 * request that relates to register a user
 * 
 * @author Jay
 */
@Path(EndPointConstant.HOME)
public class RegistrationEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(RegistrationEndPoint.class);
	public RegistrationEndPoint() {
	}
	
	/**
	 * Validate the given access token and returns 
	 * populated token if valid with status code as 304
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
	@Path(EndPointConstant.REGISTER)
	public Object register(@BeanParam UserRegisterFormBean form){
		LOG.info("[ENTRY]- Received requst for access key validation");
		LOG.info("[EXIT]- Received requst for access key validation");
		return null;
	}

}

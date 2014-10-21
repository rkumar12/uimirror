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
package com.uimirror.account.auth.endpoint;

import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.form.AuthenticatedHeaderForm;

/**
 * Controller which will be for the common path, any 
 * request that needs to be validate using the {@link AccessToken}
 * 
 * @author Jay
 */
@Path(AuthenticationEndPointConstant.ACCESS_HOME_PATH)
@Singleton
public class AccessTokenAuthenticationEndPoint{

	private @Autowired Processor<AuthenticatedHeaderForm, String> validateAccessTokenProcessor;
	private @Autowired Processor<AuthenticatedHeaderForm, String> refreshAbleAccessTokenProcessor;
	
	private static Logger LOG = LoggerFactory.getLogger(AccessTokenAuthenticationEndPoint.class);
	
	public AccessTokenAuthenticationEndPoint() {
		//NOP
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
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.ACCESS_TOKEN_VALIDATION_PATH)
	public Object doValidate(@BeanParam AuthenticatedHeaderForm form){
		LOG.info("[ENTRY]- Received requst for access key validation");
		Object response = validateAccessTokenProcessor.invoke(form);
		LOG.info("[EXIT]- Received requst for access key validation");
		return response;
	}
	
	/**
	 * This will be a internal call by the different internal applications
	 * for resource level security to make sure the requesting client 
	 * Represents a valid caller
	 * 
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.OUATH_2_TOEKEN_VALIDATE_REFRESH_PATH)
	public Object validateAndRefreshAccessKey(@BeanParam AuthenticatedHeaderForm form){
		LOG.info("[ENTRY]- Received request for client AcessToken Validation and re generation iff necessary");
		Object response = refreshAbleAccessTokenProcessor.invoke(form);
		LOG.info("[EXIT]- Received request for client AcessToken Validation and re generation iff necessary");
		return response;
	}

}

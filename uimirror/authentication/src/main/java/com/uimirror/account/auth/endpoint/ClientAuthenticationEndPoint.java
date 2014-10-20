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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.auth.client.form.ClientAPIForm;
import com.uimirror.account.auth.client.form.ClientSecretKeyForm;
import com.uimirror.core.Processor;
import com.uimirror.core.form.AuthenticatedHeaderForm;

/**
 * Controller which will handle all the client releated request such as 
 * api key validation and issue of new code or issue of new access token.
 * 
 * @author Jay
 */
@Path(AuthenticationEndPointConstant.OUATH2_HOME)
@Singleton
public class ClientAuthenticationEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(ClientAuthenticationEndPoint.class);
	
	private @Autowired Processor<ClientSecretKeyForm> secretKeyProcessor;
	private @Autowired Processor<ClientAPIForm> apiKeyProcessor;
	private @Autowired Processor<AuthenticatedHeaderForm> accessTokenProcessor;
	
	/**
	 * Handles for the incoming request that needs validate the client,
	 * if client api KEY is valid then it will generate a temporal token, using which 
	 * a user authentication form will process.
	 * 
	 * A standard url will look like /auth?response_type=code&
  	 *	client_id=CLIENT_ID&redirect_uri=REDIRECT_URI&scope=read
  	 *
  	 * A standard response will be 
  	 * response {
     *	"token":"RsT5OjbzRn430zqMLgV3Ia",
     *  "type" : "temporal"
	 *	}
	 * @return
	 */
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Path(AuthenticationEndPointConstant.OUATH_2_SECRET_CODE_PATH)
	public Object getSecretCode(@BeanParam ClientAPIForm form){
		LOG.info("[ENTRY]- Received request for client Secret Code with the param {}", form);
		Object response = apiKeyProcessor.invoke(form);
		LOG.info("[EXIT]- Received request for client Secret Code");
		return response;
	}
	
	/**
	 * handles the client secret token in the below format
	 * POST https://api.oauth2server.com/token
     *	grant_type=authorization_code&
     *	code=AUTH_CODE_HERE&
     *	redirect_uri=REDIRECT_URI&
     *	client_id=CLIENT_ID&
     *	client_secret=CLIENT_SECRET
     *
     * in case of success validation will issue a new accestoken for this response
     * 
     * response {
     *	"token":"RsT5OjbzRn430zqMLgV3Ia",
     *  "type" : "Access"
	 *	}
	 * 
	 * @param form
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Path(AuthenticationEndPointConstant.OUATH_2_TOEKEN_PATH)
	public Object requestAccessToken(@BeanParam ClientSecretKeyForm form){
		LOG.info("[ENTRY]- Received request for client access toekn");
		Object response = secretKeyProcessor.invoke(form);
		LOG.info("[EXIT]- Received request for client access toekn");
		return response;
	}
	
	/**
	 * This will be a internal call by the different internal applications
	 * for resource level security to make sure the requesting client 
	 * Represents a valid user
	 * 
	 * @return
	 *///TODO review pending
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Path(AuthenticationEndPointConstant.OUATH_2_TOEKEN_VALIDATE_REFRESH_PATH)
	public Object validateAndRefreshAccessKey(@BeanParam AuthenticatedHeaderForm form){
		LOG.info("[ENTRY]- Received request for client AcessToken Validation and re generation iff necessary");
		Object response = accessTokenProcessor.invoke(form);
		LOG.info("[EXIT]- Received request for client AcessToken Validation and re generation iff necessary");
		return response;
	}

}

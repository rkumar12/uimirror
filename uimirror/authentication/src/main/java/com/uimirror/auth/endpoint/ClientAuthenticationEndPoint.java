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

import com.uimirror.auth.client.ClientLoginAuthenticationForm;
import com.uimirror.auth.client.ClientSecretAuthenticationForm;

/**
 * Controller which will handle all the client releated request such as 
 * api key validation and issue of new code or issue of new access token.
 * 
 * @author Jay
 */
@Path("/oauth2")
public class ClientAuthenticationEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(ClientAuthenticationEndPoint.class);
	public ClientAuthenticationEndPoint() {
	}
	
	/**
	 * Handle the client API Key validation and issue a temporarl code
	 * using that Client should request for access token
	 * This will be initial entry point for the client API to get validated
	 * 
	 * @param loginForm
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.OUATH_2_AUTH_PATH)
	public Object doAuthenticate(@BeanParam ClientLoginAuthenticationForm loginForm){
		LOG.info("[ENTRY]- Received requst for client authentication");
		LOG.info("[EXIT]- Received requst for client authentication");
		return null;
	}
	
	/**
	 * handles the client secret token and responsible for the user login screen page 
	 * redirect.
	 * 
	 * @param form
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.OUATH_2_TOEKEN_PATH)
	public Object requestAccessToken(ClientSecretAuthenticationForm form){
		LOG.info("[ENTRY]- Received request for client access toekn");
		LOG.info("[EXIT]- Received request for client access toekn");
		return null;
	}

}

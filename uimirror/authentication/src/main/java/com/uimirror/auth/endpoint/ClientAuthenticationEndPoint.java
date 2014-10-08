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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.auth.bean.Authentication;
import com.uimirror.auth.client.bean.OAuth2APIKeyAuthentication;
import com.uimirror.auth.client.bean.OAuth2Authentication;
import com.uimirror.auth.client.bean.OAuth2SecretKeyAuthentication;
import com.uimirror.auth.client.bean.form.ClientAPIForm;
import com.uimirror.auth.client.bean.form.ClientSecretKeyForm;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;
import com.uimirror.core.service.TransformerService;

/**
 * Controller which will handle all the client releated request such as 
 * api key validation and issue of new code or issue of new access token.
 * 
 * @author Jay
 */
@Path("/oauth2")
public class ClientAuthenticationEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(ClientAuthenticationEndPoint.class);
	private TransformerService<ClientSecretKeyForm, OAuth2SecretKeyAuthentication> secretKeyToAuthTransformer;
	private TransformerService<ClientAPIForm, OAuth2APIKeyAuthentication> apiKeyToAuthTransformer;
	private TransformerService<AuthenticatedHeaderForm, OAuth2Authentication> accessTokenToAuthTransformer;
	/**
	 * handles the client secret token in the below format
	 * POST https://api.oauth2server.com/token
     *	grant_type=authorization_code&
     *	code=AUTH_CODE_HERE&
     *	redirect_uri=REDIRECT_URI&
     *	client_id=CLIENT_ID&
     *	client_secret=CLIENT_SECRET
     * in case of success validation will issue a new accestoken for this response
     * 
     * response {
     *	"access_token":"RsT5OjbzRn430zqMLgV3Ia"
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
		Authentication auth = secretKeyToAuthTransformer.transform(form);
		LOG.info("[EXIT]- Received request for client access toekn");
		return null;
	}
	
	/**
	 * Handles for the incoming request that needs validate the client,
	 * if client api KEY is valid then it will generate a temporal token, using which 
	 * a user authentication form will process.
	 * 
	 * A standard url will look like /auth?response_type=code&
  	 *	client_id=CLIENT_ID&redirect_uri=REDIRECT_URI&scope=read&app=rti
	 * @return
	 */
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Path(AuthenticationEndPointConstant.OUATH_2_AUTH_PATH)
	public Object getSecretCode(@BeanParam ClientAPIForm form){
		LOG.info("[ENTRY]- Received request for client Secret Code with the param {}", form);
		Authentication auth = apiKeyToAuthTransformer.transform(form);
		LOG.info("[EXIT]- Received request for client Secret Code");
		return null;
	}
	
	/**
	 * This will be a internal call by the different internal applications
	 * for resource level security to make sure the requesting client 
	 * Represents a valid user
	 * 
	 * @return
	 */
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Path(AuthenticationEndPointConstant.OUATH_2_TOEKEN_VALIDATE_REFRESH_PATH)
	public Object validateAndRefreshAccessKey(@BeanParam AuthenticatedHeaderForm form){
		LOG.info("[ENTRY]- Received request for client AcessToken Validation and re generation iff necessary");
		accessTokenToAuthTransformer.transform(form);
		LOG.info("[EXIT]- Received request for client AcessToken Validation and re generation iff necessary");
		return null;
	}

}

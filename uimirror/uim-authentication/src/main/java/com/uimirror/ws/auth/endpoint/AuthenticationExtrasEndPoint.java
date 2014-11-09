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
package com.uimirror.ws.auth.endpoint;

import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import com.uimirror.core.form.AuthenticatedHeaderForm;
import com.uimirror.ws.auth.form.AuthorizeClientAuthenticationForm;
import com.uimirror.ws.auth.form.ForgetAClientForm;

/**
 * Controller which will be for the common path, Any client granting access permission
 * or forgetting a client operation will be taken care.
 * 
 * @author Jay
 */
@Path(AuthenticationEndPointConstant.GRANT_ACCESS_TO_CLIENT_PATH)
@Singleton
public class AuthenticationExtrasEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(AuthenticationExtrasEndPoint.class);
	private @Autowired Processor<AuthorizeClientAuthenticationForm, String> authorizationClientProcessor;

	/**
	 * This will make sure, user is granting access to the client for using user information on behalf.
	 * and assigning him the appropriate the permissions.
	 * 
	 * POST https://api.oauth2server.com/allow
     *	Authorization=authorization_code&
     *	scope=SCOPE_HERE&
     *  approval=Y&
     * in case of success validation will issue a new accestoken for this response
     * 
     * response {
     *	"token":"RsT5OjbzRn430zqMLgV3Ia",
     *  "type" : "secret"
	 *	}
	 * or
	 * response {
	 *	"msg": "invalid"
	 *	}
	 * 
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Object grantAccessToClient(@BeanParam AuthorizeClientAuthenticationForm form){
		LOG.info("[ENTRY]- Received request for granting access to the client, so that it can use the user data on behalf");
		Object response = authorizationClientProcessor.invoke(form);
		LOG.info("[EXIT]- Received request for granting access to the client, so that it can use the user data on behalf");
		return response;
	}
	
	/**
	 * This will remove the client removed from the user's granting list, so that user needs to give him access latter. 
	 * 
	 * @return
	 *////TODO pending implementation
	@DELETE
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	public Object forgetAClient(@BeanParam ForgetAClientForm form){
		LOG.info("[ENTRY]- Received request for forgtting a client, so that client can't access further data without permission again");
		LOG.info("[EXIT]- Received request for forgtting a client, so that client can't access further data without permission again");
		return null;
	}
	
	
	/**
	 * This will check if the client who is trying to access the user information is authenticated by the user or not,
	 * and has appropriate permission or not.
	 * 
	 * @return
	 *///TODO pending implementation
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	public Object isClientHasUserPermission(@BeanParam AuthenticatedHeaderForm form){
		LOG.info("[ENTRY]- Received request for verifying grant access to the client, so that it can use the user data on behalf");
		LOG.info("[EXIT]- Received request for verifying grant access to the client, so that it can use the user data on behalf");
		return null;
	}
	
}

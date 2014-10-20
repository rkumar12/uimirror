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

import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.account.auth.bean.form.InvalidateLoginSessionsForm;
import com.uimirror.account.auth.bean.form.LoginSessionForm;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;

/**
 * Controller which will be for the common path, any user will try to be get authenticated.
 * User which represnts a individual entity will be validated and issue with a accesstoken with expiry and refresh token
 * known as {@link AccessToken}
 * 
 * @author Jay
 */
//TODO extra architecture required to validate the authentication header
//TODO Move to the authentication extra project which will be away of this
//As this shouldn't do the authentication to the request, this should only perform the login
//details and give back to the client
@Path(AuthenticationEndPointConstant.LOGIN_SESSION_PATH)
public class AuthenticationSessionEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(AuthenticationSessionEndPoint.class);
	
	/**
	 * This will get all the associated login sessions for a user.
	 * this accepts a maximum query limit to get the maximum result set
	 * ?limit=10
	 * 
	 * @return
	 */
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	public Object getLoginSessions(@BeanParam LoginSessionForm form){
		LOG.info("[ENTRY]- Received request for getting list of login sessions currently associated with the user {}", form);
		LOG.info("[EXIT]- Received request for getting list of login sessions currently associated with the user");
		return null;
	}
	
	/**
	 * This will delete a login session by invalidating the token.
	 * this will have a path @param, which session needs to be deleted.
	 * @return
	 */
	@DELETE
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Path(AuthenticationEndPointConstant.LOGIN_SESSION_INVALID_PATH)
	public Object invalidateALoginSession(@BeanParam InvalidateLoginSessionsForm form){
		LOG.info("[ENTRY]- Received request for invalidating a login session associated with the user");
		LOG.info("[EXIT]- Received request for invalidating a login session associated with the user");
		return null;
	}
	
	/**
	 * This will mark all the current active sessions of the user as invalid.
	 * 
	 * @return
	 */
	@DELETE
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Path(AuthenticationEndPointConstant.LOGIN_SESSION_INVALID_ALL_PATH)
	public Object invalidateAllLoginSession(@BeanParam AuthenticatedHeaderForm form){
		LOG.info("[ENTRY]- Received request for invalidating all actieve login session associated with the user");
		LOG.info("[EXIT]- Received request for invalidating all actieve login session associated with the user");
		return null;
	}
}

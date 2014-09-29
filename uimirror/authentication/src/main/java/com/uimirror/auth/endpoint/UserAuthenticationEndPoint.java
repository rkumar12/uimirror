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
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.controller.AuthenticationController;
import com.uimirror.auth.user.bean.form.ScreenLockAuthenticationForm;
import com.uimirror.auth.user.bean.form.UserLoginFormAuthenticationForm;
import com.uimirror.core.auth.bean.AccessToken;

/**
 * Controller which will be for the common path, any user will try to be get authenticated.
 * User which represnts a individual entity will be validated and issue with a accesstoken with expiry and refresh token
 * known as {@link AccessToken}
 * 
 * @author Jay
 */
@Path("/")
public class UserAuthenticationEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(UserAuthenticationEndPoint.class);
	@Autowired
	private AuthenticationController userAuthenticationController;
	public UserAuthenticationEndPoint() {
	}
	
	/**
	 * <p>This will deseralize the loginform map and try to authenticate,
	 * on successful authentication issue a token object i.e {@link AccessToken} to the client </p>
	 * This also audit the login process.
	 * @param loginForm
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Object doAuthenticate(@BeanParam UserLoginFormAuthenticationForm loginForm){
		LOG.info("[ENTRY]- Received requst for authentication");
		Object response = userAuthenticationController.getAccessToken(loginForm);
		LOG.info("[EXIT]- Received requst for authentication");
		return response;
	}
	
	/**
	 * Unlock the screen by validating the accesstoken provided,
	 * if necessary, it will renew the token and send back. 
	 * @param form
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.UNLOCK_PATH)
	public Object unlockScreen(ScreenLockAuthenticationForm form){
		LOG.info("[ENTRY]- Received request for unlocking screen");
		LOG.info("[EXIT]- Received request for unlocking screen");
		return null;
	}
	
	/**
	 * Will perform the 2FA for the user.
	 * @param form
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.TWO_FACTO_PATH)
	public Object dp2FA(ScreenLockAuthenticationForm form){
		LOG.info("[ENTRY]- Received request for 2 Factor Authentication");
		LOG.info("[EXIT]- Received request for 2 Factor Authentication");
		return null;
	}

}
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

import com.uimirror.auth.user.bean.form.ScreenLockAuthenticationForm;
import com.uimirror.auth.user.bean.form.TwoFactorUserLoginAuthenticationForm;
import com.uimirror.auth.user.bean.form.UserLoginFormAuthenticationForm;
import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.controller.AuthenticationController;

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
	private @Autowired AuthenticationController userAuthenticationController;
	private @Autowired AuthenticationController screenLockAuthenticationController;
	private @Autowired AuthenticationController twoFactorAuthController;
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
		Object response = userAuthenticationController.doAuthenticate(loginForm);
		LOG.info("[EXIT]- Received requst for authentication");
		return response;
	}
	
	/**
	 * Unlock the screen by validating the access-token provided,
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
		Object response = screenLockAuthenticationController.doAuthenticate(form);
		LOG.info("[EXIT]- Received request for unlocking screen");
		return response;
	}
	
	/**
	 * Will perform the 2FA for the user, based on the key and access token provided
	 * if the details provided are valid, will return a new {@linkplain AccessToken}
	 * @param form
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.TWO_FACTO_PATH)
	public Object dp2FA(TwoFactorUserLoginAuthenticationForm form){
		LOG.info("[ENTRY]- Received request for 2 Factor Authentication");
		Object response = twoFactorAuthController.doAuthenticate(form);
		LOG.info("[EXIT]- Received request for 2 Factor Authentication");
		return response;
	}

}

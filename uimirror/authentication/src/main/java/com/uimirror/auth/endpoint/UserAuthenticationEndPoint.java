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
	 * De-serialize the authentication form submitted
	 * and try to authenticate the eariller token issued, to validate if the request is from a valid source
	 * if request is from a valid source it tries to authenticate the user provided details and 
	 * in case user doesn't have any _2FA enabled, it will generate a security token and return back to the user.
	 * 
	 * @param loginForm
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.LOGIN_PATH)
	public Object login(@BeanParam UserLoginFormAuthenticationForm loginForm){
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
	 * De-serialize the form, tries to validate the earlier token issued to process this request,
	 * if everything is correct, it will process for OTP validation, if OTP matched, it will generate a new security code
	 * and send back to the caller
	 * 
	 * @param form
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.TWO_FACTO_PATH)
	public Object otp(TwoFactorUserLoginAuthenticationForm form){
		LOG.info("[ENTRY]- Received request for 2 Factor OTP Authentication");
		Object response = twoFactorAuthController.doAuthenticate(form);
		LOG.info("[EXIT]- Received request for 2 Factor OTP Authentication");
		return response;
	}
	
}

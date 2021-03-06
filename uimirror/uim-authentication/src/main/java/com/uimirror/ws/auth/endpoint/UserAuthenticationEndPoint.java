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
import com.uimirror.ws.auth.form.LoginForm;
import com.uimirror.ws.auth.form.OTPAuthenticationForm;
import com.uimirror.ws.auth.form.ScreenLockAuthenticationForm;

/**
 * This authentication end point will be responsible for the user login {@link #login(LoginForm)},
 * {@link #unlockScreen(ScreenLockAuthenticationForm)}, {@link #otp(OTPAuthenticationForm)}
 * the result of all this operations will be a token 
 * known as {@link AccessToken}
 * 
 * @author Jay
 */
@Path(AuthenticationEndPointConstant.AUTH)
@Singleton
public class UserAuthenticationEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(UserAuthenticationEndPoint.class);
	private @Autowired Processor<LoginForm, String> loginFormAuthProcessor;
	private @Autowired Processor<ScreenLockAuthenticationForm, String> screenLockAuthProcessor;
	private @Autowired Processor<OTPAuthenticationForm, String> otpAuthProcessor;
	private @Autowired Processor<AuthenticatedHeaderForm, String> logOutAuthProcessor;
	
	/**
	 * De-serialize the authentication form submitted
	 * and try to authenticate the eariller token issued, to validate if the request is from a valid source
	 * if request is from a valid source it tries to authenticate the user provided details and 
	 * in case user doesn't have any _2FA enabled, it will generate a security token and return back to the user.
	 * 
	 * POST https://api.oauth2server.com/login
     *	Authorization=authorization_code&
     *	uid=USER_ID_HERE&
     *	password=PASSWORD_HERE&
     *  keepmelogin=y
     * in case of success validation will issue a new accestoken for this response
     * 
     * response {
     *	"token":"RsT5OjbzRn430zqMLgV3Ia",
     *  "type" : "secret" or "otp"
	 *	}
	 * or
	 * response {
	 *	"token":"RsT5OjbzRn430zqMLgV3Ia",
	 *  "type" : "USER_PERMISSION"
	 *  "msg" : {
	 *          "clientname" : "XYZ",
	 *          "scope"      : "read"
	 *  	}
	 *	}
	 * 
	 * @param loginForm
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.LOGIN_PATH)
	public Object login(@BeanParam LoginForm loginForm){
		LOG.info("[ENTRY]- Received requst for user authentication by user name and password");
		Object response = loginFormAuthProcessor.invoke(loginForm);
		LOG.info("[EXIT]- Received requst for user authentication by user name and password");
		return response;
	}
	
	/**
	 * De-serialize the form, tries to validate the earlier token issued to process this request,
	 * if everything is correct, it will process for OTP validation, if OTP matched, it will generate a new security code
	 * and send back to the caller
	 * POST https://api.oauth2server.com/login
     *	Authorization=authorization_code&
     *	otp=OTP_HERE&
     * in case of success validation will issue a new accestoken for this response
     * 
     * response {
     *	"token":"RsT5OjbzRn430zqMLgV3Ia",
     *  "type" : "secret"
	 *	}
	 * or
	 * response {
	 *	"token":"RsT5OjbzRn430zqMLgV3Ia",
	 *  "type" : "USER_PERMISSION"
	 *  "msg" : {
	 *          "clientname" : "XYZ",
	 *          "scope"      : "read"
	 *  	}
	 *	}
	 * @param form
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.TWO_FACTO_PATH)
	public Object otp(@BeanParam OTPAuthenticationForm form){
		LOG.info("[ENTRY]- Received request for OTP Authentication");
		Object response = otpAuthProcessor.invoke(form);
		LOG.info("[EXIT]- Received request for OTP Authentication");
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
	public Object unlockScreen(@BeanParam ScreenLockAuthenticationForm form){
		LOG.info("[ENTRY]- Received request for unlocking screen");
		Object response = screenLockAuthProcessor.invoke(form);
		LOG.info("[EXIT]- Received request for unlocking screen");
		return response;
	}
	
	/**
	 * De-serialize the authentication form submitted
	 * and in-validate the provided token.
	 * 
	 * POST https://api.oauth2server.com/login
     *	Authorization=authorization_code
     * in case of success
     * 
     * response {
     *	"msg":"sucess"
	 *	}
	 * 
	 * @param form {@link AuthenticatedHeaderForm}
	 * @return response with the action performed
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AuthenticationEndPointConstant.LOGOUT_PATH)
	public Object loggOut(@BeanParam AuthenticatedHeaderForm form){
		LOG.info("[ENTRY]- Received requst for user authentication by user name and password");
		Object response = logOutAuthProcessor.invoke(form);
		LOG.info("[EXIT]- Received requst for user authentication by user name and password");
		return response;
	}
	
}

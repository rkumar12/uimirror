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
package com.uimirror.account.endpoint;

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

import com.uimirror.account.auth.user.form.OTPAuthenticationForm;
import com.uimirror.account.auth.user.form.ScreenLockAuthenticationForm;
import com.uimirror.account.user.form.RegisterForm;
import com.uimirror.account.user.form.VerifyForm;
import com.uimirror.core.auth.AccessToken;

/**
 * This authentication end point will be responsible for the user login {@link #create(RegisterForm)},
 * {@link #unlockScreen(ScreenLockAuthenticationForm)}, {@link #otp(OTPAuthenticationForm)}
 * the result of all this operations will be a token 
 * known as {@link AccessToken}
 * 
 * @author Jay
 */
@Path(AccountEndPointConstant.USER)
@Singleton
public class UserAccountEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(UserAccountEndPoint.class);
	
	public UserAccountEndPoint() {
		//NOP
	}
	
	/**
	 * De-serialize the Register form submitted
	 * and try to authenticate the client id , to validate if the request is from a valid source
	 * if request is from a valid source it tries to register the user.
	 * 
	 * POST https://account.uimirror.com/user/create
     *	client_id=client_id_here&
     *	first_name=FIRST_NAME_HERE&
     *	last_name=LAST_NAME_HERE&
     *  email=EMAIL_HERE&
     *  password=PASSWORD_HERE&
     *  gender = GENDER_HERE&
     *  dob = DOB_HERE&
     * in case of success new accestoken for this response
     * 
     * response {
     *	"token":"RsT5OjbzRn430zqMLgV3Ia",
     *  "type" : "temporal"
	 *	}
	 * or
	 * response {
	 *	"msg":"invalid request",
	 *	}
	 * 
	 * @param form
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AccountEndPointConstant.CREATE)
	public Object create(@BeanParam RegisterForm form){
		LOG.info("[ENTRY]- Received requst for User Creation.");
		System.out.println(form);
		LOG.info("[EXIT]- Received requst for User Creation.");
		return "Helo";
	}
	
	/**Handles the incoming request for the new user register verifications
	 * Request will look like below
	 * POST https://account.uimirror.com/user/verify
     *	Authorization=auth_code_here&
     *	source=E
     * or 
     * POST https://account.uimirror.com/user/verify
     *	Authorization=auth_code_here&
     *	source=W&
     *  code = CODE_HERE
     *  
     *  Response will be 
     *  response {
     *	"token":"RsT5OjbzRn430zqMLgV3Ia",
     *  "type" : "secret"
	 *	}
	 * or
	 * response {
	 *	"msg":"invalid request",
	 *	}
	 * @param form
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(AccountEndPointConstant.VERIFY)
	public Object verify(@BeanParam VerifyForm form){
		LOG.info("[ENTRY]- Received requst for new user registeration verifications.");
		System.out.println(form);
		LOG.info("[EXIT]- Received requst for new user registeration verifications.");
		return "Helo";
	}
	
}

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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;

/**
 * Controller which will be for the common path, any user will try to be get authenticated.
 * User which represnts a individual entity will be validated and issue with a accesstoken with expiry and refresh token
 * known as {@link AccessToken}
 * 
 * @author Jay
 */
@Path("/")
public class AuthenticationMailerEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(AuthenticationMailerEndPoint.class);
	
	/**
	 * De-serialize the form, tries to validate the earlier token issued to process this request,
	 * if everything is correct, it will process to send the mail again to the user for the otp,
	 * this will invalidate the earlier issued code and create a new temporal code. 
	 * 
	 * @param form
	 * @return
	 */
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Path(AuthenticationEndPointConstant.RESEND_MAIL_PATH)
	public Object sendMail(@BeanParam AuthenticatedHeaderForm form){
		LOG.info("[ENTRY]- Received request for 2 Factor mail token resend {}", form);
		LOG.info("[EXIT]- Received request for 2 Factor mail token resend {}", form);
		return null;
	}
	
}

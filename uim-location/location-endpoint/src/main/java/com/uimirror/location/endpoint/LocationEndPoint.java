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
package com.uimirror.location.endpoint;

import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.location.form.LocationQueryForm;

/**
 * <p>Location endpoint to handle all the query
 * regarding to the location endpoints such as get location by
 * location id, by location name, find by longitude and latitude etc.
 * </p>
 * @author Jay
 */
@Path(LocationEndPointConstant.HOME)
@Singleton
public class LocationEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(LocationEndPoint.class);
	
	public LocationEndPoint() {
		//NOP
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
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	public Object otp(@BeanParam LocationQueryForm form){
		LOG.info("[ENTRY]- Received request for 2 Factor OTP Authentication");
		LOG.info("[EXIT]- Received request for 2 Factor OTP Authentication");
		return "Hello";
	}
	
}

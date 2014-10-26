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
package com.uimirror.account.client.endpoint;

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
import com.uimirror.ouath.client.form.ClientRegisterForm;
import com.uimirror.ws.api.security.annotation.PreAuthorize;

/**
 * End point for the various client operation
 * 
 * @author Jay
 */
@Path(ClientEndPointConstant.CLIENT)
@Singleton
public class ClientAccountEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(ClientAccountEndPoint.class);
	private @Autowired Processor<ClientRegisterForm, String> createClientAccountProcessor;
	
	
	public ClientAccountEndPoint() {
		//NOP
	}
	
	/**
	 * De-serialize the Register form submitted
	 * and try to authenticate the client id , to validate if the request is from a valid source
	 * if request is from a valid source it tries to register the user.
	 * 
	 * POST https://account.uimirror.com/client/create
     *	Authorization=auth_code_here&
     *	name=CLIENT_NAME_HERE&
     *	redirect_uri=REDIRECT_URI_HERE&
     *  app_url=APP_URL(OPTIONAL)&
     *  
     * in case of success new accestoken for this response
     * 
     * response {
     *	"token":{"token":"RsT5OjbzRn430zqMLgV3Ia",
     *  		"type" : "access"
     *          },
     *  "apikey": "API KEY",
     *  "secret" : "SECRET_KEY"        
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
	@Path(ClientEndPointConstant.CREATE)
	@PreAuthorize
	public Object create(@BeanParam ClientRegisterForm form){
		LOG.info("[ENTRY]- Received requst for Client Creation.");
		Object response = createClientAccountProcessor.invoke(form);
		LOG.info("[EXIT]- Received requst for Client Creation.");
		return response;
	}
	
	
}

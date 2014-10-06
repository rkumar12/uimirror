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

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.auth.client.bean.form.ClientTokenAuthenticationForm;

/**
 * Controller which will handle all the client releated request such as 
 * api key validation and issue of new code or issue of new access token.
 * 
 * @author Jay
 */
@Path("/oauth2")
public class ClientAuthenticationEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(ClientAuthenticationEndPoint.class);
	public ClientAuthenticationEndPoint() {
	}
	
	/**
	 * handles the client secret token and responsible for the user login screen page 
	 * redirect.
	 * POST https://api.oauth2server.com/token
     *	grant_type=authorization_code&
     *	code=AUTH_CODE_HERE&
     *	redirect_uri=REDIRECT_URI&
     *	client_id=CLIENT_ID&
     *	client_secret=CLIENT_SECRET
     * response {
     *	"access_token":"RsT5OjbzRn430zqMLgV3Ia"
	 *	}
	 * 
	 * @param form
	 * @return
	 */
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Path(AuthenticationEndPointConstant.OUATH_2_TOEKEN_PATH)
	public Object requestAccessToken(ClientTokenAuthenticationForm form){
		LOG.info("[ENTRY]- Received request for client access toekn");
		LOG.info("[EXIT]- Received request for client access toekn");
		return null;
	}

}

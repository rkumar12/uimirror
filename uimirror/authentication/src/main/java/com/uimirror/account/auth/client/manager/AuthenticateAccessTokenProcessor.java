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
package com.uimirror.account.auth.client.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.auth.client.OAuth2Authentication;
import com.uimirror.account.auth.controller.AuthenticationProvider;
import com.uimirror.account.auth.exception.AuthToApplicationExceptionMapper;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.form.AuthenticatedHeaderForm;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.service.TransformerService;

/**
 * A processor, that validate the incoming request 
 * and tries to authenticate the same, if authenticated returns the authenticated principal  
 * 
 * @author Jay
 */
public class AuthenticateAccessTokenProcessor implements Processor<AuthenticatedHeaderForm, Authentication>{

	protected static final Logger LOG = LoggerFactory.getLogger(AuthenticateAccessTokenProcessor.class);
	
	private @Autowired TransformerService<AuthenticatedHeaderForm, OAuth2Authentication> accessTokenToAuthTransformer;
	private @Autowired AuthenticationProvider accessKeyAuthProvider;
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public Authentication invoke(AuthenticatedHeaderForm param) throws ApplicationException{
		LOG.debug("[START]- Authenticating the access token");
		//Step 1- Transform the bean to Authentication
		Authentication auth = getTransformedObject(param);
		//Let GC take this ASAP
		param = null;
		//Step 2- Authenticate and issue a token
		Authentication authPrincipal = authenticate(auth);
		LOG.debug("[END]- Authenticating the access token");
		//Remove Unnecessary information from the accessToken Before Sending to the user
		return authPrincipal;
	}
	
	/**
	 * converts the form bean object into a {@link Authentication} object
	 * 
	 * @param param
	 * @return
	 */
	private Authentication getTransformedObject(AuthenticatedHeaderForm param){
		return accessTokenToAuthTransformer.transform(param);
	}
	
	/**
	 * On basics of {@link CredentialType}, it will simply validate or generate 
	 * the access token {@link AccessToken}
	 * @param auth
	 * @return
	 */
	private Authentication authenticate(Authentication auth){
		return accessKeyAuthProvider.authenticate(auth);
	}

}

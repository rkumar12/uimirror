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
package com.uimirror.auth.client.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.client.bean.OAuth2Authentication;
import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.exception.AuthToApplicationExceptionMapper;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.service.TransformerService;

/**
 * Extracts the field, interact with the {@link AuthenticationManager}
 * and respond back to the caller with the valid response.
 * 
 * @author Jay
 */
public class AccessTokenProcessor implements Processor<AuthenticatedHeaderForm>{

	protected static final Logger LOG = LoggerFactory.getLogger(AccessTokenProcessor.class);
	
	private @Autowired TransformerService<AuthenticatedHeaderForm, OAuth2Authentication> accessTokenToAuthTransformer;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired AuthenticationProvider accessKeyAuthProvider;
	
	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public Object invoke(AuthenticatedHeaderForm param) throws ApplicationException{
		LOG.debug("[START]- Authenticating the user and trying to to get the authentication details");
		//Step 1- Transform the bean to Authentication
		Authentication auth = getTransformedObject(param);
		//Let GC take this ASAP
		param = null;
		//Step 2- Authenticate and issue a token
		Authentication authPrincipal = authenticateAndIssueToken(auth);
		AccessToken token = (AccessToken)authPrincipal.getPrincipal();
		LOG.debug("[END]- Authenticating the user and trying to to get the authentication details {}", auth);
		//Remove Unnecessary information from the accessToken Before Sending to the user
		return jsonResponseTransFormer.doTransForm(token);
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
	private Authentication authenticateAndIssueToken(Authentication auth){
		return accessKeyAuthProvider.authenticate(auth);
	}

}

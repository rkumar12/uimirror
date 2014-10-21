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
package com.uimirror.account.auth.token.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.auth.core.AuthenticationManager;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.exceptions.ApplicationExceptionMapper;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.form.AuthenticatedHeaderForm;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;

/**
 * Extracts the field, interact with the {@link AuthenticationManager}
 * and respond back to the caller with the valid response.
 * ****NOTE*****It doesn't clean the token, so remember this call should be from the authorized 
 * source only
 * @author Jay
 */
public class RefreshAbleAccessTokenProcessor implements Processor<AuthenticatedHeaderForm, String>{

	protected static final Logger LOG = LoggerFactory.getLogger(RefreshAbleAccessTokenProcessor.class);
	
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired Processor<AuthenticatedHeaderForm, Authentication> refreshAccessTokenProcessor;
	
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use=ApplicationExceptionMapper.NAME)
	public String invoke(AuthenticatedHeaderForm param) throws ApplicationException{
		LOG.debug("[START]- Authenticating the user and trying to to get the authentication details");
		//Step 1- get authenticated token principal
		Authentication auth = refreshAccessTokenProcessor.invoke(param);
		//Let GC take this ASAP
		param = null;
		//Step 2- Authenticate and issue a token
		AccessToken token = (AccessToken)auth.getPrincipal();
		LOG.debug("[END]- Authenticating the user and trying to to get the authentication details {}", auth);
		//Don't clean it as end points who is responsible for the sending to user will clean the same
		return jsonResponseTransFormer.doTransForm(token);
	}

}

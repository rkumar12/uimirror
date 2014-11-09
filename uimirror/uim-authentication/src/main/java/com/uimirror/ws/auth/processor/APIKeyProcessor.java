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
package com.uimirror.ws.auth.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.service.TransformerService;
import com.uimirror.sso.auth.APIKeyAuthentication;
import com.uimirror.sso.auth.provider.AuthenticationProvider;
import com.uimirror.sso.exception.AuthToApplicationExceptionMapper;
import com.uimirror.sso.form.ClientAPIForm;

/**
 * The step of operations for this processor is defined as below:
 * <ol>
 * <li>Extract the provided form to a well defined authentication bean</li>
 * <li>Authenticate the provided client details and issue a new token</li>
 * <li>Transform the generated response into json</li>
 * </ol>
 * 
 * @author Jay
 */
public class APIKeyProcessor implements Processor<ClientAPIForm, String>{

	protected static final Logger LOG = LoggerFactory.getLogger(APIKeyProcessor.class);
	
	private @Autowired TransformerService<ClientAPIForm, APIKeyAuthentication> apiKeyToAuthTransformer;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired AuthenticationProvider apiKeyAuthProvider;
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public String invoke(ClientAPIForm param) throws ApplicationException{
		LOG.debug("[START]- Authenticating the client API KEY");
		//Step 1- Transform the bean to Authentication
		Authentication auth = getTransformedObject(param);
		//Let GC take this ASAP
		param = null;
		//Step 2- Authenticate and issue a token
		Authentication authPrincipal = authenticateAndIssueToken(auth);
		AccessToken token = (AccessToken)authPrincipal.getPrincipal();
		//Remove Unnecessary information from the accessToken Before Sending to the user
		LOG.debug("[END]- Authenticating the client API KEY {}", auth);
		//Step 3- Transform to json
		return jsonResponseTransFormer.doTransForm(token.toResponseMap());
	}
	
	/**
	 * converts the form bean object into a {@link Authentication} object
	 * 
	 * @param param
	 * @return
	 */
	private Authentication getTransformedObject(ClientAPIForm param){
		return apiKeyToAuthTransformer.transform(param);
	}
	
	/**
	 * This will validate the API key and generate a token, that will be used by
	 * API KEY submit.
	 * 
	 * @param auth
	 * @return
	 */
	private Authentication authenticateAndIssueToken(Authentication auth){
		return apiKeyAuthProvider.authenticate(auth);
	}

}

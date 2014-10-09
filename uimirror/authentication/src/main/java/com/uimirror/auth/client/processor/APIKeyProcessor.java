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

import com.uimirror.auth.bean.CredentialType;
import com.uimirror.auth.client.bean.OAuth2APIKeyAuthentication;
import com.uimirror.auth.client.bean.form.ClientAPIForm;
import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.controller.Processor;
import com.uimirror.auth.exception.AuthToApplicationExceptionMapper;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.service.TransformerService;

/**
 * This will process the API key validation by interacting with
 * {@linkplain AuthenticationProvider#authenticate(Authentication)}
 * 
 * @author Jay
 */
public class APIKeyProcessor implements Processor<ClientAPIForm>{

	protected static final Logger LOG = LoggerFactory.getLogger(APIKeyProcessor.class);
	
	private @Autowired TransformerService<ClientAPIForm, OAuth2APIKeyAuthentication> apiKeyToAuthTransformer;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired AuthenticationProvider apiKeyAuthProvider;
	
	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public Object invoke(ClientAPIForm param) throws ApplicationException{
		LOG.debug("[START]- Authenticating the user and trying to to get the authentications details");
		//Step 1- Transform the bean to Authentication
		Authentication auth = getTransformedObject(param);
		//Let GC take this ASAP
		param = null;
		LOG.debug("[END]- Authenticating the user and trying to to get the authentication details {}", auth);
		//Remove Unnecessary information from the accessToken Before Sending to the user
		Authentication authPrincipal = generateToken(auth);
		AccessToken token = (AccessToken)authPrincipal.getPrincipal();
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
	 * On basics of {@link CredentialType}, it will simply validate or generate 
	 * the access token {@link AccessToken}
	 * @param auth
	 * @return
	 */
	private Authentication generateToken(Authentication auth){
		return apiKeyAuthProvider.authenticate(auth);
	}

}

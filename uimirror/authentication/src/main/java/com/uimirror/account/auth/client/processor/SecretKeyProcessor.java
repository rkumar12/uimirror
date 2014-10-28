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
package com.uimirror.account.auth.client.processor;

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
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.sso.auth.OAuth2SecretKeyAuthentication;
import com.uimirror.sso.auth.provider.AuthenticationProvider;
import com.uimirror.sso.exception.AuthToApplicationExceptionMapper;
import com.uimirror.sso.form.ClientSecretKeyForm;
import com.uimirror.sso.token.InvalidateTokenProcessor;

/**
 * The main steps are as below:
 * 
 * <ol>
 * <li>Validates the provided Client details.</li>
 * <li>Generate a new temporal token</li>
 * <li>Store the token</li>
 * <li>clean token</li>
 * <li>Invalidate the previous token</li>
 * <li>generate a json response</li>
 * </ol>
 * 
 * @author Jay
 */
public class SecretKeyProcessor implements Processor<ClientSecretKeyForm, String>{

	protected static final Logger LOG = LoggerFactory.getLogger(SecretKeyProcessor.class);
	
	private @Autowired TransformerService<ClientSecretKeyForm, OAuth2SecretKeyAuthentication> secretKeyToAuthTransformer;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired AuthenticationProvider secretCodeAuthProvider;
	private @Autowired BackgroundProcessorFactory<String, Object> backgroundProcessorFactory;
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public String invoke(ClientSecretKeyForm param) throws ApplicationException{
		LOG.debug("[START]- Issuing a new access token.");
		String prevToken = param.getSecretCode();
		//Step 1- Transform the bean to Authentication
		Authentication auth = getTransformedObject(param);
		//Let GC take this ASAP
		param = null;
		//Step 2- Authenticate and issue a token
		Authentication authPrincipal = authenticateAndIssueToken(auth);
		AccessToken token = (AccessToken)authPrincipal.getPrincipal();
		LOG.debug("[END]- Issuing a new access token.");
		//Invalidate the previous Token
		backgroundProcessorFactory.getProcessor(InvalidateTokenProcessor.NAME).invoke(prevToken);
		//Remove Unnecessary information from the accessToken Before Sending to the user
		return jsonResponseTransFormer.doTransForm(token.toResponseMap());
	}
	
	/**
	 * converts the form bean object into a {@link Authentication} object
	 * 
	 * @param param
	 * @return
	 */
	private Authentication getTransformedObject(ClientSecretKeyForm param){
		return secretKeyToAuthTransformer.transform(param);
	}
	
	/**
	 * On basics of {@link CredentialType}, it will simply validate or generate 
	 * the access token {@link AccessToken}
	 * @param auth
	 * @return
	 */
	private Authentication authenticateAndIssueToken(Authentication auth){
		return secretCodeAuthProvider.authenticate(auth);
	}

}

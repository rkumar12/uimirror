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
package com.uimirror.account.auth.user.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.auth.controller.AuthenticationProvider;
import com.uimirror.account.auth.core.AuthenticationManager;
import com.uimirror.account.auth.core.processor.InvalidateTokenProcessor;
import com.uimirror.account.auth.exception.AuthToApplicationExceptionMapper;
import com.uimirror.account.auth.user.bean.ClientAuthorizationAuthentication;
import com.uimirror.account.auth.user.form.AuthorizeClientAuthenticationForm;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;

/**
 * Extracts the field, interact with the {@link AuthenticationManager}
 * and respond back to the caller with the valid response.
 * This authentication process involves in accepting the Client.
 * 
 * Steps for this operations are as follows:
 * <ol>
 * <li>Extract the provided form to a well defined authentication bean i.e {@link ClientAuthorizationAuthentication}</li>
 * <li>Authenticate the provided previous token</li>
 * <li>Invalidate the previous token</li>
 * <li>Transform the generated response into json</li>
 * </ol>
 * 
 * @author Jay
 */
public class AuthorizationClientProcessor implements Processor<AuthorizeClientAuthenticationForm, String>{

	protected static final Logger LOG = LoggerFactory.getLogger(AuthorizationClientProcessor.class);
	
	private @Autowired TransformerService<AuthorizeClientAuthenticationForm, ClientAuthorizationAuthentication> clientAuthorizationFormToAuthTransformer;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired AuthenticationProvider clientAuthorizationAuthProvider;
	private @Autowired BackgroundProcessorFactory<String, Object> backgroundProcessorFactory;
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public String invoke(AuthorizeClientAuthenticationForm param) throws ApplicationException{
		LOG.debug("[START]- Generating a new accesstoken based on the previous accesstoken and Client Authorization by user");
		String prevToken = param.getToken();
		//Step 1- Transform the bean to Authentication
		Authentication auth = getTransformedObject(param);
		//Let GC take this ASAP
		param = null;
		//Remove Unnecessary information from the accessToken Before Sending to the user
		Authentication authToken = authenticateAndIssueToken(auth);
		AccessToken token = authToken == null ? null : (AccessToken)authToken.getPrincipal();
		//Invalidate the previous Token
		backgroundProcessorFactory.getProcessor(InvalidateTokenProcessor.NAME).invoke(prevToken);
		LOG.debug("[END]- Generating a new accesstoken based on the previous accesstoken and Client Authorization by user");
		return jsonResponseTransFormer.doTransForm(token == null ? null : token.toResponseMap());
	}
	
	/**
	 * converts the form bean object into a {@link Authentication} object
	 * @param param
	 * @return
	 */
	private Authentication getTransformedObject(AuthorizeClientAuthenticationForm param) {
		return clientAuthorizationFormToAuthTransformer.transform(param);
	}

	/**
	 * On basics of {@link CredentialType}, it will simply validate or generate 
	 * the access token {@link AccessToken}
	 * @param auth
	 * @return
	 */
	private Authentication authenticateAndIssueToken(Authentication auth){
		return clientAuthorizationAuthProvider.authenticate(auth);
	}

}

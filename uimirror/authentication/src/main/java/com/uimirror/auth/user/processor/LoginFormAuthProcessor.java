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
package com.uimirror.auth.user.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.exception.AuthToApplicationExceptionMapper;
import com.uimirror.auth.user.bean.LoginAuthentication;
import com.uimirror.auth.user.bean.form.LoginForm;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.service.TransformerService;

/**
 * The step of operations for this processor is defined as below:
 * <ol>
 * <li>Extract the provided form to a well defined authentication bean i.e {@link LoginAuthentication}</li>
 * <li>Authenticate the provided previous token, User credentials and generate token</li>
 * <li>Transform the generated response into json</li>
 * <li>Invalidate the previous token</li>
 * </ol>
 * 
 * @author Jay
 */
//TODO invalidate the previous token
public class LoginFormAuthProcessor implements Processor<LoginForm>{

	protected static final Logger LOG = LoggerFactory.getLogger(LoginFormAuthProcessor.class);
	
	private @Autowired TransformerService<LoginForm, LoginAuthentication> loginFormToAuthTransformer;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired AuthenticationProvider loginFormAuthProvider;

	
	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public Object invoke(LoginForm param) throws ApplicationException{
		LOG.debug("[START]- Authenticating the user.");
		//Step 1- Transform the bean to Authentication
		Authentication auth = getTransformedObject(param);
		//Let GC take this ASAP
		param = null;
		Authentication authToken = authenticateAndIssueToken(auth);
		//Remove Unnecessary information from the accessToken Before Sending to the user
		AccessToken token = (AccessToken)authToken.getPrincipal();
		LOG.debug("[END]- Authenticating the user.");
		return jsonResponseTransFormer.doTransForm(token.toResponseMap());
	}
	
	/**
	 * converts the form bean object into a {@link Authentication} object
	 * 
	 * @param param
	 * @return
	 */
	private Authentication getTransformedObject(LoginForm param){
		return loginFormToAuthTransformer.transform(param);
	}
	
	/**
	 * On basics of {@link CredentialType}, it will simply validate or generate 
	 * the access token {@link AccessToken}
	 * @param auth
	 * @return
	 */
	private Authentication authenticateAndIssueToken(Authentication auth){
		return loginFormAuthProvider.authenticate(auth);
	}

}

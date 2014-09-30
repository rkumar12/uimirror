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
package com.uimirror.auth.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.user.LoginFormAuthProvider;
import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.bean.form.BasicAuthenticationForm;
import com.uimirror.core.auth.controller.AuthParamExtractor;
import com.uimirror.core.auth.controller.AuthenticationController;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;

/**
 * Extracts the field, interact with the {@link AuthenticationManager}
 * and respond back to the caller with the valid response.
 * 
 * @author Jay
 */
public class LoginFormBasedAuthController implements AuthenticationController{

	protected static final Logger LOG = LoggerFactory.getLogger(LoginFormBasedAuthController.class);
	
	private @Autowired AuthParamExtractor loginFormAuthParamExtractor;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired LoginFormAuthProvider loginFormAuthProvider;
	
	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use="AUTHTOAPPEM")
	public Object doAuthenticate(BasicAuthenticationForm param) throws ApplicationException{
		LOG.debug("[START]- Getting the accesstoken based on the credentials");
		//Step 1- Extract authentication details
		Authentication auth = extractAuthentication(param);
		//Let GC take this ASAP
		param = null;
		LOG.debug("[END]- Getting the accesstoken based on the credentials {}", auth);
		return jsonResponseTransFormer.doTransForm(generateToken(auth));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAuthentication(java.lang.Object)
	 */
	@Override
	public Authentication extractAuthentication(BasicAuthenticationForm param) throws ApplicationException {
		return loginFormAuthParamExtractor.extractAuthParam(param);
	}
	
	/**
	 * On basics of {@link CredentialType}, it will simply validate or generate 
	 * the access token {@link AccessToken}
	 * @param auth
	 * @return
	 */
	private AccessToken generateToken(Authentication auth){
		AccessToken token = null;
		token = loginFormAuthProvider.getAuthenticateToken(auth);
		return token;
	}

}

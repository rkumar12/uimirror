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
package com.uimirror.auth.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uimirror.auth.controller.AuthenticationController;
import com.uimirror.auth.user.bean.form.UserLoginFormAuthenticationForm;
import com.uimirror.core.auth.AuthParamExtractor;
import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;

/**
 * Extracts the field, interact with the {@link AuthenticationManager}
 * and respond back to the caller with the valid response.
 * @author Jay
 */
@Service
public class UserAuthenticationController implements AuthenticationController{

	protected static final Logger LOG = LoggerFactory.getLogger(UserAuthenticationController.class);
	
	private @Autowired AuthParamExtractor userAuthParamExtractor;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired AuthenticationManager userAuthenticationManager;
	
	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use="AUTHTOAPPEM")
	public Object getAccessToken(Object param) throws ApplicationException{
		LOG.debug("[START]- Getting the accesstoken based on the credentials");
		//Step 1- Extract authentication details
		Authentication auth = getAuthentication((UserLoginFormAuthenticationForm)param);
		//Let GC take this ASAP
		param = null;
		LOG.debug("[END]- Getting the accesstoken based on the credentials {}", auth);
		return jsonResponseTransFormer.doTransForm(validateOrGenerateToken(auth));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAuthentication(java.lang.Object)
	 */
	@Override
	public Authentication getAuthentication(Object param) throws ApplicationException {
		return userAuthParamExtractor.extractAuthParam((UserLoginFormAuthenticationForm)param);
	}
	
	/**
	 * On basics of {@link CredentialType}, it will simply validate or generate 
	 * the access token {@link AccessToken}
	 * @param auth
	 * @return
	 */
	private AccessToken validateOrGenerateToken(Authentication auth){
		AccessToken token = null;
		switch (auth.getCredentialType()) {
			case LOGINFORM:
				token = userAuthenticationManager.authenticate(auth);
				break;
			case ACCESSKEY:
				token = userAuthenticationManager.authenticate(auth);
				break;
			case SCREENLOCK:
				//TODO handle it separate and later
				break;
			case APIKEY:
				//TODO handle it separate and later
				break;
		}
		return token;
	}

}
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

import com.uimirror.auth.AuthToApplicationExceptionMapper;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.bean.form.BasicAuthenticationForm;
import com.uimirror.core.auth.controller.AuthenticationController;
import com.uimirror.core.auth.controller.AuthenticationProvider;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;

/**
 * Extracts the field, interact with the {@link AuthenticationManager}
 * and respond back to the caller with the valid response.
 * This authentication process involves in unlocking the user screen.
 * 
 * Its expected user should have given the earlier accessToken along with
 * the screen unlock password
 * @author Jay
 */
public class ScreenLockAuthController implements AuthenticationController{

	protected static final Logger LOG = LoggerFactory.getLogger(ScreenLockAuthController.class);
	
	private @Autowired AuthConstants screenLockAuthParamExtractor;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired AuthenticationProvider screenLockAuthProvider;
	
	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public Object doAuthenticate(BasicAuthenticationForm param) throws ApplicationException{
		LOG.debug("[START]- Generating a new accesstoken based on the previous accesstoken and password for screen unlock");
		//Step 1- Extract authentication details
		Authentication auth = extractAuthentication(param);
		//Let GC take this ASAP
		param = null;
		LOG.debug("[END]- Generating a new accesstoken based on the previous accesstoken and password for screen unlock {}", auth);
		//Remove Unnecessary information from the accessToken Before Sending to the user
		return jsonResponseTransFormer.doTransForm(generateToken(auth).toResponseMap());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAuthentication(java.lang.Object)
	 */
	@Override
	public Authentication extractAuthentication(BasicAuthenticationForm param) throws ApplicationException {
		return screenLockAuthParamExtractor.extractAuthParam(param);
	}
	
	/**
	 * On basics of {@link CredentialType}, it will simply validate or generate 
	 * the access token {@link AccessToken}
	 * @param auth
	 * @return
	 */
	private AccessToken generateToken(Authentication auth){
		return screenLockAuthProvider.getAuthenticationToken(auth);
	}

}

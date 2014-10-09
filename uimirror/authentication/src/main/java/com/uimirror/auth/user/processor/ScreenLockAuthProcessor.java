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

import com.uimirror.auth.bean.CredentialType;
import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.controller.Processor;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.exception.AuthToApplicationExceptionMapper;
import com.uimirror.auth.user.bean.ScreenLockAuthentication;
import com.uimirror.auth.user.bean.form.ScreenLockAuthenticationForm;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.service.TransformerService;

/**
 * Extracts the field, interact with the {@link AuthenticationManager}
 * and respond back to the caller with the valid response.
 * This authentication process involves in unlocking the user screen.
 * 
 * Its expected user should have given the earlier accessToken along with
 * the screen unlock password
 * @author Jay
 */
public class ScreenLockAuthProcessor implements Processor<ScreenLockAuthenticationForm>{

	protected static final Logger LOG = LoggerFactory.getLogger(ScreenLockAuthProcessor.class);
	
	private @Autowired TransformerService<ScreenLockAuthenticationForm, ScreenLockAuthentication> screenLockFormToAuthTransformer;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired AuthenticationProvider screenLockAuthProvider;
	
	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public Object invoke(ScreenLockAuthenticationForm param) throws ApplicationException{
		LOG.debug("[START]- Generating a new accesstoken based on the previous accesstoken and password for screen unlock");
		//Step 1- Extract authentication details
		Authentication auth = getTransformedObject(param);
		//Let GC take this ASAP
		param = null;
		LOG.debug("[END]- Generating a new accesstoken based on the previous accesstoken and password for screen unlock {}", auth);
		//Remove Unnecessary information from the accessToken Before Sending to the user
		Authentication authToken = generateToken(auth);
		AccessToken token = (AccessToken)authToken.getPrincipal();
		return jsonResponseTransFormer.doTransForm(token.toResponseMap());
	}

	/**
	 * converts the form bean object into a {@link Authentication} object
	 * @param param
	 * @return
	 */
	public Authentication getTransformedObject(ScreenLockAuthenticationForm param) throws ApplicationException {
		return screenLockFormToAuthTransformer.transform(param);
	}
	
	/**
	 * On basics of {@link CredentialType}, it will simply validate or generate 
	 * the access token {@link AccessToken}
	 * @param auth
	 * @return
	 */
	private Authentication generateToken(Authentication auth){
		return screenLockAuthProvider.authenticate(auth);
	}

}

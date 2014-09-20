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

import javax.ws.rs.WebApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uimirror.auth.AuthParamExtractor;
import com.uimirror.auth.controller.AuthenticationController;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.AuthenticationException;
import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.rest.extra.UnAuthorizedException;

/**
 * Extracts the field, interact with the {@link AuthenticationManager}
 * and respond back to the caller with the valid response.
 * @author Jay
 */
@Service
public class UserAuthenticationController implements AuthenticationController{

	protected static final Logger LOG = LoggerFactory.getLogger(UserAuthenticationController.class);
	
	private @Autowired AuthParamExtractor<UserAuthenticationForm> userAuthParamExtractor;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired AuthenticationManager userAuthenticationManager;
	
	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	public Object getAccessToken(Object param) {
		LOG.debug("[START]- Getting the accesstoken based on the credentials");
		//Step 1- Extract authentication details
		Authentication auth = getAuthentication((UserAuthenticationForm)param);
		
		//Let GC take this ASAP
		param = null;
		AccessToken token = null;
		try{
			token = userAuthenticationManager.authenticate(auth);
		}catch(AuthenticationException e){
			throw new UnAuthorizedException();
		}
		LOG.debug("[END]- Getting the accesstoken based on the credentials {}", auth);
		return jsonResponseTransFormer.doTransForm(token);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAuthentication(java.lang.Object)
	 */
	@Override
	public Authentication getAuthentication(Object param) throws WebApplicationException {
		try{
			return userAuthParamExtractor.extractAuthParam((UserAuthenticationForm)param);
		}catch(IllegalArgumentException e){
			throw new UnAuthorizedException();
		}
	}

}

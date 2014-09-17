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
import com.uimirror.auth.controller.CommonAuthenticationController;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.AuthenticationManager;

/**
 * Extracts the field, interact with the {@link AuthenticationManager}
 * and respond back to the caller with the valid response.
 * @author Jay
 */
@Service
public class UserAuthenticationController extends CommonAuthenticationController{

	protected static final Logger LOG = LoggerFactory.getLogger(UserAuthenticationController.class);
	
	@Autowired
	private AuthParamExtractor<UserAuthenticationForm> userAuthParamExtractor;
	
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
		LOG.debug("[END]- Getting the accesstoken based on the credentials {}", auth);
		return auth;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAuthentication(java.lang.Object)
	 */
	@Override
	public Authentication getAuthentication(Object param) throws WebApplicationException {
		try{
			return userAuthParamExtractor.extractAuthParam((UserAuthenticationForm)param);
		}catch(IllegalArgumentException e){
			throw new WebApplicationException(unAuthorizedResponse());
		}
	}

}

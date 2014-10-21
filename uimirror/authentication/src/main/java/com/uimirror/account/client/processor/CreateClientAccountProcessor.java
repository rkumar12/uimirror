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
package com.uimirror.account.client.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.auth.controller.AccessTokenProvider;
import com.uimirror.account.client.form.RegisterForm;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.rest.extra.ApplicationException;

/**
 * Processor for the client account creation, it will first check for the client existence
 * if not found will try to create a new else, else stop
 * Steps are as below
 * <ol>
 * <li>Validate and get the token details</li>
 * <li>Check if the same owner has already the same account</li>
 * <li>create client account</li>
 * </ol>
 * 
 * @author Jay
 */
//TODO write some Access Token processor, which will validate the access token and if required try to regenrate and send back
public class CreateClientAccountProcessor implements Processor<RegisterForm, String>{

	protected static final Logger LOG = LoggerFactory.getLogger(CreateClientAccountProcessor.class);
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

	public CreateClientAccountProcessor() {
		// NOP
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public String invoke(RegisterForm param) throws ApplicationException {
		LOG.info("[START]- Registering a new Client.");
		AccessToken token = getValidToken(param.getToken());
		LOG.info("[END]- Registering a new Client.");
		return null;
	}
	
	private AccessToken getValidToken(String token){
		return persistedAccessTokenProvider.getValid(token);
	}

}

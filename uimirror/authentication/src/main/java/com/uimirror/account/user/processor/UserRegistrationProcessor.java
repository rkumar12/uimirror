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
package com.uimirror.account.user.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.auth.client.APIKeyAuthentication;
import com.uimirror.account.auth.client.form.ClientAPIForm;
import com.uimirror.account.client.bean.Client;
import com.uimirror.account.user.form.RegisterForm;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.exceptions.ApplicationExceptionMapper;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.user.DefaultUser;

/**
 * Processor for the user account creation, it will first check for the user existence
 * if not found will try to create a new else, else stop
 * Steps are as below
 * <ol>
 * <li>Validate and get the token details</li>
 * <li>Check if provided details are valid</li>
 * <li>Transform to the {@link DefaultUser}</li>
 * <li>Send verification mail in background</li>
 * </ol>
 * 
 * @author Jay
 */
public class UserRegistrationProcessor implements Processor<RegisterForm, String>{

	protected static final Logger LOG = LoggerFactory.getLogger(UserRegistrationProcessor.class);
	private @Autowired Processor<Authentication, Client> apiKeyAuthenticateProcessor;
	private @Autowired TransformerService<ClientAPIForm, APIKeyAuthentication> apiKeyToAuthTransformer;

	public UserRegistrationProcessor() {
		// NOP
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	@MapException(use=ApplicationExceptionMapper.NAME)
	public String invoke(RegisterForm param) throws ApplicationException {
		LOG.info("[START]- Registering a new User.");
		Client client = authenticateAndGetClient(param);
		LOG.info("[END]- Registering a new User.");
		return null;
	}
	
	private Client authenticateAndGetClient(RegisterForm param){
		Authentication auth = apiKeyToAuthTransformer.transform(param);
		Client client = apiKeyAuthenticateProcessor.invoke(auth);
		return client;
	}

}

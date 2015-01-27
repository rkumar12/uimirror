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
package com.uimirror.core.ws.security.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.core.Processor;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.client.Client;
import com.uimirror.core.client.store.ClientStore;
import com.uimirror.core.ws.security.processor.APIKeyAuthenticateProcessor;
import com.uimirror.core.ws.security.processor.AllowClientProcessor;
import com.uimirror.sso.client.UserAuthorizedClient;
import com.uimirror.sso.client.store.UserAuthorizedClientStore;

/**
 * Contains the bean definition for all the authentication controller process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfAuthProcessor {
	
	@Bean
	@Autowired
	public Processor<Authentication, Client> apiKeyAuthenticateProcessor(ClientStore persistedClientStore){
		APIKeyAuthenticateProcessor apiKeyAuthenticateProcessor = new APIKeyAuthenticateProcessor();
		apiKeyAuthenticateProcessor.setPersistedClientStore(persistedClientStore);
		return apiKeyAuthenticateProcessor;
	}
	
	@Bean
	@Autowired
	public Processor<UserAuthorizedClient, Object> allowClientprocessor(UserAuthorizedClientStore persistedUserAuthorizedClientMongoStore){
		AllowClientProcessor allowClientProcessor = new AllowClientProcessor();
		allowClientProcessor.setPersistedUserAuthorizedClientMongoStore(persistedUserAuthorizedClientMongoStore);
		return allowClientProcessor;
	}

}

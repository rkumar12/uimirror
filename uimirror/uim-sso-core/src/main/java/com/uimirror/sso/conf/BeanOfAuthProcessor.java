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
package com.uimirror.sso.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.core.Processor;
import com.uimirror.core.auth.Authentication;
import com.uimirror.ouath.client.Client;
import com.uimirror.sso.client.AllowClientProcessor;
import com.uimirror.sso.client.UserAuthorizedClient;
import com.uimirror.sso.processor.APIKeyAuthenticateProcessor;

/**
 * Contains the bean definition for all the authentication controller process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfAuthProcessor {
	
	@Bean
	public Processor<Authentication, Client> apiKeyAuthenticateProcessor(){
		return new APIKeyAuthenticateProcessor();
	}
	
	@Bean
	public Processor<UserAuthorizedClient, Object> allowClientprocessor(){
		return new AllowClientProcessor();
	}

}

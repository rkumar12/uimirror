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
package com.uimirror.api.security.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.uimirror.api.security.processor.AllowAuthorizationClientProcessor;
import com.uimirror.api.security.processor.DenyAuthorizationClientProcessor;
import com.uimirror.api.security.processor.OTPMailProcessor;
import com.uimirror.api.security.processor.UserRestoreProcessor;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.util.thread.BackgroundProcessor;
import com.uimirror.sso.client.UserAuthorizedClient;
import com.uimirror.sso.token.InvalidateTokenProcessor;
import com.uimirror.user.store.UserCredentialsStore;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfBackGroundProcessor {
	
	@Bean(name=AllowAuthorizationClientProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Autowired
	public BackgroundProcessor<AccessToken, Object> allowAuthorizationClientProcessor(TransformerService<AccessToken, UserAuthorizedClient> tokenToAuthorizedClientTransformer,
			Processor<UserAuthorizedClient, Object> allowClientprocessor){
		AllowAuthorizationClientProcessor allowAuthorizationClientProcessor = new AllowAuthorizationClientProcessor();
		allowAuthorizationClientProcessor.setAllowClientprocessor(allowClientprocessor);
		allowAuthorizationClientProcessor.setTokenToAuthorizedClientTransformer(tokenToAuthorizedClientTransformer);
		return allowAuthorizationClientProcessor;
	}
	
	@Bean(name=DenyAuthorizationClientProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public BackgroundProcessor<AccessToken, Object> denyAuthorizationClientProcessor(){
		return new DenyAuthorizationClientProcessor();
	}
	
	@Bean(name=InvalidateTokenProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public BackgroundProcessor<String, Object> invalidateTokenProcessor(){
		return new InvalidateTokenProcessor();
	}
	
	@Bean(name=UserRestoreProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Autowired
	public BackgroundProcessor<String, Object> userRestoreProcessor(UserCredentialsStore persistedUserCredentialMongoStore){
		UserRestoreProcessor userRestoreProcessor = new UserRestoreProcessor();
		userRestoreProcessor.setPersistedUserCredentialMongoStore(persistedUserCredentialMongoStore);
		return userRestoreProcessor;
	}
	
	@Bean(name=OTPMailProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Autowired
	public BackgroundProcessor<AccessToken, Object> otpMailProcessor(TransformerService<AccessToken, UserAuthorizedClient> tokenToAuthorizedClientTransformer,
			Processor<UserAuthorizedClient, Object> allowClientprocessor){
		OTPMailProcessor otpMailProcessor = new OTPMailProcessor();
		otpMailProcessor.setAllowClientprocessor(allowClientprocessor);
		otpMailProcessor.setTokenToAuthorizedClientTransformer(tokenToAuthorizedClientTransformer);
		return otpMailProcessor;
	}

}

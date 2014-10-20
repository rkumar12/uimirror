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
package com.uimirror.account.auth.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.uimirror.account.auth.controller.AccessTokenProvider;
import com.uimirror.account.auth.core.PasswordMatcher;
import com.uimirror.account.auth.core.PersistedAccessTokenMongoProvider;
import com.uimirror.core.crypto.CryptoMatcherService;
import com.uimirror.core.crypto.MatcherServiceImpl;
import com.uimirror.core.mail.EmailBeanInitializr;
import com.uimirror.core.rest.extra.JsonResponseTransFormer;
import com.uimirror.core.rest.extra.ResponseTransFormer;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
@Import({BeanOfExceptionIntitializer.class, BeanOfAuthProcessor.class
	, BeanOfAuthManagers.class, BeanOfAuthProviders.class
	, BeanOfTransformers.class, EmailBeanInitializr.class
	, BeanOfBackGroundProcessor.class, BeanOfSchedulers.class})
public class BeanIntitializer {

	@Bean(name=JsonResponseTransFormer.NAME)
	public ResponseTransFormer<String> jsonResponseTransFormer(){
		return new JsonResponseTransFormer();
	}
	
	@Bean
	public CryptoMatcherService cryptoMatcherService(){
		return new MatcherServiceImpl();
	}
	
	@Bean
	@Autowired
	public PasswordMatcher passwordMatcher(CryptoMatcherService cryptoMatcherService){
		return new PasswordMatcher(cryptoMatcherService);
	}
	
	//****Access Token providers****
	@Bean
	public AccessTokenProvider persistedAccessTokenProvider(){
		return new PersistedAccessTokenMongoProvider();
	}
	//****Access Token providers end****

}

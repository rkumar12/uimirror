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
package com.uimirror.account.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.uimirror.account.auth.conf.AuthBeanIntitializer;
import com.uimirror.core.crypto.CryptoMatcherService;
import com.uimirror.core.crypto.MatcherServiceImpl;
import com.uimirror.core.rest.extra.JsonResponseTransFormer;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.ouath.client.conf.BeanOfOAuthClient;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
@Import({AuthBeanIntitializer.class, DaoBeanIntitializer.class
	, BeanOfProcessor.class, BeanOfTransformer.class
	, BeanOfValidator.class, BeanOfOAuthClient.class})
public class BeanIntitializer {

	@Bean(name=JsonResponseTransFormer.NAME)
	public ResponseTransFormer<String> jsonResponseTransFormer(){
		return new JsonResponseTransFormer();
	}
	
	@Bean
	public CryptoMatcherService cryptoMatcherService(){
		return new MatcherServiceImpl();
	}

}

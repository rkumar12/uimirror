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

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.core.crypto.CryptoMatcherService;
import com.uimirror.core.crypto.MatcherServiceImpl;
import com.uimirror.core.rest.extra.JsonResponseTransFormer;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.rest.extra.ResponseTransFormerFactory;
import com.uimirror.core.rest.extra.TransformResponseAspect;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanIntitializer {

	@Bean(name=JsonResponseTransFormer.NAME)
	public ResponseTransFormer<String> jsonResponseTransFormer(){
		return new JsonResponseTransFormer();
	}
	
	@Bean
	//TODO delete this
	public TransformResponseAspect transformResponseAspect(){
		return new TransformResponseAspect();
	}
	
	@Bean
	public ServiceLocatorFactoryBean responseTransFormerFactory(){
		ServiceLocatorFactoryBean sb = new ServiceLocatorFactoryBean();
		sb.setServiceLocatorInterface(ResponseTransFormerFactory.class);
		return sb;
	}
	
	@Bean
	public CryptoMatcherService cryptoMatcherService(){
		return new MatcherServiceImpl();
	}

}

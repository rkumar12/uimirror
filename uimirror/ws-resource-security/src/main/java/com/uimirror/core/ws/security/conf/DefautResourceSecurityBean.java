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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.core.ws.security.AccessTokenProcessor;
import com.uimirror.core.ws.security.processor.DefaultAccessTokenProcessor;

@Configuration
public class DefautResourceSecurityBean {

	@Value("${auth.server:http://127.0.0.1:8080/uim/auth}")
	private String serviceUrl;
	
	@Bean
	public AccessTokenProcessor accessTokenProcessor(){
		DefaultAccessTokenProcessor processor = new DefaultAccessTokenProcessor();
		processor.setServiceUrl(serviceUrl);
		return processor;
	}
}

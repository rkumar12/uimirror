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

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.util.thread.BackgroundProcessor;
import com.uimirror.sso.client.AllowAuthorizationClientProcessor;
import com.uimirror.sso.client.DenyAuthorizationClientProcessor;
import com.uimirror.sso.token.InvalidateTokenProcessor;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfBackGroundProcessor {
	
	@Bean(name=AllowAuthorizationClientProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public BackgroundProcessor<AccessToken, Object> allowAuthorizationClientProcessor(){
		return new AllowAuthorizationClientProcessor();
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

}

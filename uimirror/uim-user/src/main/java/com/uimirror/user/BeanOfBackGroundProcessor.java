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
package com.uimirror.user;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.util.thread.BackgroundProcessor;
import com.uimirror.user.processor.BackGroundCreateMissingUserProcessor;
import com.uimirror.user.processor.BackGroundCreateUserProcessor;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfBackGroundProcessor {
	
	@Bean(name=BackGroundCreateUserProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public BackgroundProcessor<DefaultUser, Object> backGroundCreateUserProcessor(){
		return new BackGroundCreateUserProcessor();
	}

	@Bean(name=BackGroundCreateMissingUserProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public BackgroundProcessor<DefaultUser, Object> backGroundCreateMissingUserProcessor(){
		return new BackGroundCreateMissingUserProcessor();
	}

}

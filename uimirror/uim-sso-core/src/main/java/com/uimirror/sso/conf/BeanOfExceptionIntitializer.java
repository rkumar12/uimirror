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

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.core.exceptions.ExceptionMapper;
import com.uimirror.core.exceptions.ExceptionMapperFactory;
import com.uimirror.sso.exception.AuthExceptionMapper;
import com.uimirror.sso.exception.AuthToApplicationExceptionMapper;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfExceptionIntitializer {

	@Bean
	public ServiceLocatorFactoryBean exceptionMapperFactory(){
		ServiceLocatorFactoryBean sb = new ServiceLocatorFactoryBean();
		sb.setServiceLocatorInterface(ExceptionMapperFactory.class);
		return sb;
	}
	
	@Bean(name=AuthToApplicationExceptionMapper.NAME)
	public ExceptionMapper authToApplicationExceptionMapper(){
		return new AuthToApplicationExceptionMapper();
	}
	
	@Bean(name=AuthExceptionMapper.NAME)
	public ExceptionMapper authExceptionMapper(){
		return new AuthExceptionMapper();
	}

}

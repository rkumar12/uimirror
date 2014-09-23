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
package com.uimirror.auth.conf;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.auth.AuthExceptionMapper;
import com.uimirror.auth.AuthToApplicationExceptionMapper;
import com.uimirror.core.ExceptionMapper;
import com.uimirror.core.ExceptionMapperFactory;
import com.uimirror.core.dao.MongoExceptionMapper;

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
	
	@Bean(name="AUTHTOAPPEM")
	public ExceptionMapper authToApplicationExceptionMapper(){
		return new AuthToApplicationExceptionMapper();
	}
	
	@Bean(name="AUTHEM")
	public ExceptionMapper authExceptionMapper(){
		return new AuthExceptionMapper();
	}
	
	@Bean(name="MONGOEM")
	public ExceptionMapper mongoExceptionMapper(){
		return new MongoExceptionMapper();
	}

}

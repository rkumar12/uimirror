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
package com.uimirror.ws.auth.conf;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.core.dao.MongoExceptionMapper;
import com.uimirror.core.exceptions.ApplicationExceptionMapper;
import com.uimirror.core.exceptions.ExceptionMapper;
import com.uimirror.core.exceptions.ExceptionMapperFactory;
import com.uimirror.core.extra.MapExceptionAspect;

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
	
	@Bean(name=MongoExceptionMapper.NAME)
	public ExceptionMapper mongoExceptionMapper(){
		return new MongoExceptionMapper();
	}
	
	@Bean(name=ApplicationExceptionMapper.NAME)
	public ExceptionMapper applicationExceptionMapper(){
		return new ApplicationExceptionMapper();
	}
	
	@Bean
	public MapExceptionAspect mapExceptionAspect(){
		return new MapExceptionAspect();
	}

}

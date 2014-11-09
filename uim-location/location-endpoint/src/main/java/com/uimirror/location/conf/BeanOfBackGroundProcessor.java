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
package com.uimirror.location.conf;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.uimirror.core.util.thread.BackgroundProcessor;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.processor.LocationStoreBackGroundProcessor;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfBackGroundProcessor {

	@Bean
	public ServiceLocatorFactoryBean backgroundProcessorFactory(){
		ServiceLocatorFactoryBean sb = new ServiceLocatorFactoryBean();
		sb.setServiceLocatorInterface(BackgroundProcessorFactory.class);
		return sb;
	}
	
	@Bean(name=LocationStoreBackGroundProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public BackgroundProcessor<DefaultLocation, DefaultLocation> locationStoreBackGroundProcessor(){
		return new LocationStoreBackGroundProcessor();
	}

}
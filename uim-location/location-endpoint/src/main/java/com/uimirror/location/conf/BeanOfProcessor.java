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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.core.GeoLongLat;
import com.uimirror.core.Processor;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.form.LocationQueryForm;
import com.uimirror.location.processor.LocationCollateProcessor;
import com.uimirror.location.processor.LocationSearchById;
import com.uimirror.location.processor.LocationSearchByLatLong;
import com.uimirror.location.processor.LocationSearchLocator;
import com.uimirror.location.processor.LocationStoreProcessor;

/**
 * Contains the bean definition for all the location process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfProcessor {

	@Bean
	public Processor<DefaultLocation, DefaultLocation> locationCollateProcessor(){
		return new LocationCollateProcessor();
	}

	@Bean
	public Processor<String, DefaultLocation> locationSearchById(){
		return new LocationSearchById();
	}
	
	@Bean
	public Processor<GeoLongLat, DefaultLocation> locationSearchByLatLong(){
		return new LocationSearchByLatLong();
	}
	
	@Bean
	public Processor<LocationQueryForm, String> locationSearchLocator(){
		return new LocationSearchLocator();
	}
	
	@Bean
	public Processor<DefaultLocation, DefaultLocation> locationStoreProcessor(){
		return new LocationStoreProcessor();
	}
}

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
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.uimirror.core.rest.extra.JsonResponseTransFormer;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.location.google.v3.GoogleGeocoder;
import com.uimirror.location.google.v3.GoogleGeocoderFactory;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
@Import({DaoBeanIntitializer.class ,BeanOfProcessor.class
	, BeanOfTransformer.class ,BeanOfValidator.class
	, BeanOfStore.class, BeanOfExceptionIntitializer.class
	, BeanOfBackGroundProcessor.class})
@EnableAspectJAutoProxy
public class BeanIntitializer {

	@Bean(name=JsonResponseTransFormer.NAME)
	public ResponseTransFormer<String> jsonResponseTransFormer(){
		return new JsonResponseTransFormer();
	}
	
	@Bean
	public GoogleGeocoder googleGeocoder(){
		return GoogleGeocoderFactory.createDefaultGoogleGeocoder();
	}

}

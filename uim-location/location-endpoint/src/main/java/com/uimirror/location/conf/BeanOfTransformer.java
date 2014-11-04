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

import com.uimirror.core.service.TransformerService;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.core.GeocodeResponse;
import com.uimirror.location.transformer.GoogleResponseToLocationTransformer;

/**
 * Contains the bean definition for all the location transformers
 * 
 * @author Jay
 */
@Configuration
public class BeanOfTransformer {

	@Bean
	public TransformerService<GeocodeResponse, DefaultLocation> googleResponseToLocationTransformer(){
		return new GoogleResponseToLocationTransformer();
	}

}

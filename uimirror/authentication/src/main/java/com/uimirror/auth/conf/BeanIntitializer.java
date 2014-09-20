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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.uimirror.core.rest.extra.JsonResponseTransFormer;
import com.uimirror.core.rest.extra.ResponseTransFormer;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanIntitializer {

	@Bean
	public ResponseTransFormer<String> jsonResponseTransFormer(){
		return new JsonResponseTransFormer();
	}

}

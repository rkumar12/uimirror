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
package com.uimirror.challenge.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.ws.api.security.service.ClientSecurityService;
import com.uimirror.ws.api.security.service.UimClientSecurityServiceImpl;

/**
 * <p>Configures and register the beans 
 * @author Jayaram
 */
@Configuration
public class BeanIntitializer {
	
	@Bean
	public ClientSecurityService clientSecurityService(){
		return new UimClientSecurityServiceImpl();
	}
}

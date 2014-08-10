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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.Mongo;
import com.uimirror.ws.api.security.repo.AccessTokenDao;
import com.uimirror.ws.api.security.repo.AccessTokenDaoImpl;
import com.uimirror.ws.api.security.service.AccessTokenService;
import com.uimirror.ws.api.security.service.AccessTokenServiceImpl;

/**
 * <p>Initialize all the security beans</p>
 * @author Jay
 */
@Configuration
public class SecurityBeanIntitializer {

	@Autowired
	private Mongo mongo;
	
	@Bean
	public AccessTokenDao accessTokenDao(){
		return new AccessTokenDaoImpl(mongo);
	}
	@Bean
	public AccessTokenService accessTokenService(){
		return new AccessTokenServiceImpl(accessTokenDao());
	}

}

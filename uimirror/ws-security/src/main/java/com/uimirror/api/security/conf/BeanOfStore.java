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
package com.uimirror.api.security.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DBCollection;
import com.uimirror.sso.client.store.PersistedUserAuthorizedClientMongoStore;
import com.uimirror.sso.client.store.UserAuthorizedClientStore;
import com.uimirror.sso.token.store.AccessTokenStore;
import com.uimirror.sso.token.store.PersistedAccessTokenMongoStore;

/**
 * @author Jay
 */
@Configuration
public class BeanOfStore {
	//****Stores for the SSO****
	@Bean
	@Autowired
	public AccessTokenStore persistedAccessTokenMongoStore(@Qualifier("tokenOuathCol") DBCollection collection){
		return new PersistedAccessTokenMongoStore(collection);
	}
	
	@Bean
	@Autowired
	public UserAuthorizedClientStore persistedUserAuthorizedClientMongoStore(@Qualifier("userAuthorizedClientCol") DBCollection collection){
		return new PersistedUserAuthorizedClientMongoStore(collection);
	}
	//****Stores for the SSO end****

}

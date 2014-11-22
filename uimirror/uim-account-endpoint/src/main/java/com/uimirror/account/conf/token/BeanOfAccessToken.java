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
package com.uimirror.account.conf.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import com.mongodb.DBCollection;
import com.uimirror.api.security.processor.AllowAuthorizationClientProcessor;
import com.uimirror.api.security.processor.AllowClientProcessor;
import com.uimirror.api.security.provider.PersistedAccessTokenProvider;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.util.thread.BackgroundProcessor;
import com.uimirror.sso.auth.provider.AccessTokenProvider;
import com.uimirror.sso.client.UserAuthorizedClient;
import com.uimirror.sso.client.store.PersistedUserAuthorizedClientMongoStore;
import com.uimirror.sso.client.store.UserAuthorizedClientStore;
import com.uimirror.sso.token.store.AccessTokenStore;
import com.uimirror.sso.token.store.PersistedAccessTokenMongoStore;

/**
 * Have all the bean definition for the access token details.
 * 
 * @author Jay
 */
@Configuration
@Import({ BeanOfTokenDaoIntitializer.class })
public class BeanOfAccessToken {

	// ****Stores for the SSO****
	@Bean
	@Autowired
	public AccessTokenStore persistedAccessTokenMongoStore(
			@Qualifier("tokenOuathCol") DBCollection collection) {
		return new PersistedAccessTokenMongoStore(collection);
	}

	@Bean
	@Autowired
	public UserAuthorizedClientStore persistedUserAuthorizedClientMongoStore(
			@Qualifier("userAuthorizedClientCol") DBCollection collection) {
		return new PersistedUserAuthorizedClientMongoStore(collection);
	}

	// ****Stores for the SSO end****

	// ****Access Token providers****
	@Bean
	@Autowired
	public AccessTokenProvider persistedAccessTokenProvider(
			AccessTokenStore persistedAccessTokenMongoStore) {
		PersistedAccessTokenProvider persistedAccessTokenProvider = new PersistedAccessTokenProvider();
		persistedAccessTokenProvider
				.setPersistedAccessTokenMongoStore(persistedAccessTokenMongoStore);
		return persistedAccessTokenProvider;
	}

	// ****Access Token providers****

	@Bean(name = AllowAuthorizationClientProcessor.NAME)
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Autowired
	public BackgroundProcessor<AccessToken, Object> allowAuthorizationClientProcessor(
			TransformerService<AccessToken, UserAuthorizedClient> tokenToAuthorizedClientTransformer,
			Processor<UserAuthorizedClient, Object> allowClientprocessor) {
		AllowAuthorizationClientProcessor allowAuthorizationClientProcessor = new AllowAuthorizationClientProcessor();
		allowAuthorizationClientProcessor
				.setAllowClientprocessor(allowClientprocessor);
		allowAuthorizationClientProcessor
				.setTokenToAuthorizedClientTransformer(tokenToAuthorizedClientTransformer);
		return allowAuthorizationClientProcessor;
	}

	@Bean
	@Autowired
	public Processor<UserAuthorizedClient, Object> allowClientprocessor(
			UserAuthorizedClientStore persistedUserAuthorizedClientMongoStore) {
		AllowClientProcessor allowClientProcessor = new AllowClientProcessor();
		allowClientProcessor
				.setPersistedUserAuthorizedClientMongoStore(persistedUserAuthorizedClientMongoStore);
		return allowClientProcessor;
	}

}

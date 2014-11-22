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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.api.security.manager.APIKeyAuthManager;
import com.uimirror.api.security.manager.AccessKeyAuthManager;
import com.uimirror.api.security.manager.ClientAuthorizationAuthenticationManager;
import com.uimirror.api.security.manager.LoginFormAuthenticationManager;
import com.uimirror.api.security.manager.OTPAuthenticationManager;
import com.uimirror.api.security.manager.ScreenLockAuthenticationManager;
import com.uimirror.api.security.manager.SecretKeyAuthManager;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.Authentication;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.store.ClientStore;
import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.auth.provider.AccessTokenProvider;
import com.uimirror.sso.client.store.UserAuthorizedClientStore;

/**
 * @author Jay
 */
@Configuration
public class BeanOfAuthManager {
	//****Authentication managers****
	@Bean
	@Autowired
	public AuthenticationManager secretKeyAuthManager(ClientStore persistedClientStore, AccessTokenProvider persistedAccessTokenProvider){
		SecretKeyAuthManager secretKeyAuthManager = new SecretKeyAuthManager();
		secretKeyAuthManager.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		secretKeyAuthManager.setPersistedClientStore(persistedClientStore);
		return secretKeyAuthManager;
	}
	@Bean
	@Autowired
	public AuthenticationManager apiKeyAuthManager(Processor<Authentication, Client> apiKeyAuthenticateProcessor){
		APIKeyAuthManager apiKeyAuthManager = new APIKeyAuthManager();
		apiKeyAuthManager.setApiKeyAuthenticateProcessor(apiKeyAuthenticateProcessor);
		return apiKeyAuthManager;
	}
	
	@Bean
	@Autowired
	public AuthenticationManager accessKeyAuthManager(AccessTokenProvider persistedAccessTokenProvider){
		AccessKeyAuthManager accessKeyAuthManager = new AccessKeyAuthManager();
		accessKeyAuthManager.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		return accessKeyAuthManager;
	}
	
	@Bean
	@Autowired
	public AuthenticationManager otpAuthManager(AccessTokenProvider persistedAccessTokenProvider,
			UserAuthorizedClientStore persistedUserAuthorizedClientStore){
		OTPAuthenticationManager otpAuthenticationManager = new OTPAuthenticationManager();
		otpAuthenticationManager.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		otpAuthenticationManager.setPersistedUserAuthorizedClientStore(persistedUserAuthorizedClientStore);
		return otpAuthenticationManager;
	}
	
	@Bean
	@Autowired
	public AuthenticationManager clientAuthorizationAuthManager(AccessTokenProvider persistedAccessTokenProvider){
		ClientAuthorizationAuthenticationManager clientAuthorizationAuthenticationManager = new ClientAuthorizationAuthenticationManager();
		clientAuthorizationAuthenticationManager.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		return clientAuthorizationAuthenticationManager;
	}
	
	@Bean
	public AuthenticationManager screenLockAuthManager(){
		return new ScreenLockAuthenticationManager();
	}
	
	@Bean
	public AuthenticationManager loginFormAuthManager(){
		return new LoginFormAuthenticationManager();
	}
	//****Authentication managers end****

}

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
package com.uimirror.core.ws.security.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.core.Processor;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.client.Client;
import com.uimirror.core.client.store.ClientStore;
import com.uimirror.core.user.store.UserCredentialsStore;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.core.ws.security.manager.APIKeyAuthManager;
import com.uimirror.core.ws.security.manager.AccessKeyAuthManager;
import com.uimirror.core.ws.security.manager.ClientAuthorizationAuthenticationManager;
import com.uimirror.core.ws.security.manager.LoginFormAuthenticationManager;
import com.uimirror.core.ws.security.manager.OTPAuthenticationManager;
import com.uimirror.core.ws.security.manager.ScreenLockAuthenticationManager;
import com.uimirror.core.ws.security.manager.SecretKeyAuthManager;
import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.PasswordMatcher;
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
	@Autowired
	public AuthenticationManager screenLockAuthManager(UserCredentialsStore userCredentialStore,
			AccessTokenProvider persistedAccessTokenProvider, PasswordMatcher passwordMatcher){
		ScreenLockAuthenticationManager screenLockAuthenticationManager = new ScreenLockAuthenticationManager();
		screenLockAuthenticationManager.setPasswordMatcher(passwordMatcher);
		screenLockAuthenticationManager.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		screenLockAuthenticationManager.setUserCredentialStore(userCredentialStore);
		return screenLockAuthenticationManager;
	}
	
	@Bean
	@Autowired
	public AuthenticationManager loginFormAuthManager(UserCredentialsStore userCredentialStore,
			AccessTokenProvider persistedAccessTokenProvider, PasswordMatcher passwordMatcher, 
			UserAuthorizedClientStore persistedUserAuthorizedClientStore, BackgroundProcessorFactory<String, Object> backgroundProcessorFactory){
		LoginFormAuthenticationManager loginFormAuthenticationManager = new LoginFormAuthenticationManager();
		loginFormAuthenticationManager.setBackgroundProcessorFactory(backgroundProcessorFactory);
		loginFormAuthenticationManager.setPasswordMatcher(passwordMatcher);
		loginFormAuthenticationManager.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		loginFormAuthenticationManager.setPersistedUserAuthorizedClientStore(persistedUserAuthorizedClientStore);
		loginFormAuthenticationManager.setUserCredentialStore(userCredentialStore);
		return loginFormAuthenticationManager;
	}
	//****Authentication managers end****

}

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

import com.uimirror.api.security.provider.APIKeyAuthProvider;
import com.uimirror.api.security.provider.AccessKeyAuthProvider;
import com.uimirror.api.security.provider.ClientAuthorizationAuthProvider;
import com.uimirror.api.security.provider.LoginFormAuthProvider;
import com.uimirror.api.security.provider.OTPAuthProvider;
import com.uimirror.api.security.provider.PersistedAccessTokenProvider;
import com.uimirror.api.security.provider.ScreenLockAuthProvider;
import com.uimirror.api.security.provider.SecretCodeAuthProvider;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.ouath.client.store.ClientStore;
import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.auth.provider.AccessTokenProvider;
import com.uimirror.sso.auth.provider.AuthenticationProvider;
import com.uimirror.sso.token.store.AccessTokenStore;

/**
 * @author Jay
 */
@Configuration
public class BeanOfAuthProvider {

	// ****Access Token providers****
	@Bean
	@Autowired
	public AccessTokenProvider persistedAccessTokenProvider(AccessTokenStore persistedAccessTokenMongoStore) {
		PersistedAccessTokenProvider persistedAccessTokenProvider = new PersistedAccessTokenProvider();
		persistedAccessTokenProvider.setPersistedAccessTokenMongoStore(persistedAccessTokenMongoStore);
		return persistedAccessTokenProvider;
	}
	
	@Bean
	@Autowired
	public AuthenticationProvider apiKeyAuthProvider(AuthenticationManager apiKeyAuthManager, AccessTokenProvider persistedAccessTokenProvider){
		APIKeyAuthProvider apiKeyAuthProvider = new APIKeyAuthProvider();
		apiKeyAuthProvider.setApiKeyAuthManager(apiKeyAuthManager);
		apiKeyAuthProvider.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		return apiKeyAuthProvider;
	}
	
	@Bean
	@Autowired
	public AuthenticationProvider secretCodeAuthProvider(AuthenticationManager secretKeyAuthManager, AccessTokenProvider persistedAccessTokenProvider){
		SecretCodeAuthProvider secretCodeAuthProvider = new SecretCodeAuthProvider();
		secretCodeAuthProvider.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		secretCodeAuthProvider.setSecretKeyAuthManager(secretKeyAuthManager);
		return secretCodeAuthProvider;
	}
	
	@Bean
	@Autowired
	public AuthenticationProvider accessKeyAuthProvider(AuthenticationManager accessKeyAuthManager){
		AccessKeyAuthProvider accessKeyAuthProvider = new AccessKeyAuthProvider();
		accessKeyAuthProvider.setAccessKeyAuthManager(accessKeyAuthManager);
		return accessKeyAuthProvider;
	}
	@Bean
	@Autowired
	public AuthenticationProvider clientAuthorizationAuthProvider(AccessTokenProvider persistedAccessTokenProvider,
			AuthenticationManager clientAuthorizationAuthManager, BackgroundProcessorFactory<AccessToken, Object> backgroundProcessorFactory,
			ClientStore persistedClientStore){
		ClientAuthorizationAuthProvider clientAuthorizationAuthProvider = new ClientAuthorizationAuthProvider();
		clientAuthorizationAuthProvider.setBackgroundProcessorFactory(backgroundProcessorFactory);
		clientAuthorizationAuthProvider.setClientAuthorizationAuthManager(clientAuthorizationAuthManager);
		clientAuthorizationAuthProvider.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		clientAuthorizationAuthProvider.setPersistedClientStore(persistedClientStore);
		return clientAuthorizationAuthProvider;
	}
	
	@Bean
	@Autowired
	public AuthenticationProvider otpAuthProvider(AccessTokenProvider persistedAccessTokenProvider, AuthenticationManager otpAuthManager, ClientStore persistedClientStore){
		OTPAuthProvider otpAuthProvider = new OTPAuthProvider();
		otpAuthProvider.setOtpAuthManager(otpAuthManager);
		otpAuthProvider.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		otpAuthProvider.setPersistedClientStore(persistedClientStore);
		return otpAuthProvider;
	}
	
	@Bean
	@Autowired
	public AuthenticationProvider screenLockAuthProvider(AuthenticationManager screenLockAuthManager, 
			AccessTokenProvider persistedAccessTokenProvider){
		ScreenLockAuthProvider screenLockAuthProvider = new ScreenLockAuthProvider();
		screenLockAuthProvider.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		screenLockAuthProvider.setScreenLockAuthManager(screenLockAuthManager);
		return screenLockAuthProvider;
	}
	
	@Bean
	@Autowired
	public AuthenticationProvider loginFormAuthProvider(AuthenticationManager loginFormAuthManager,
			AccessTokenProvider persistedAccessTokenProvider, BackgroundProcessorFactory<AccessToken, Object> backgroundProcessorFactory,
			ClientStore persistedClientStore){
		LoginFormAuthProvider loginFormAuthProvider = new LoginFormAuthProvider();
		loginFormAuthProvider.setBackgroundProcessorFactory(backgroundProcessorFactory);
		loginFormAuthProvider.setLoginFormAuthManager(loginFormAuthManager);
		loginFormAuthProvider.setPersistedAccessTokenProvider(persistedAccessTokenProvider);
		loginFormAuthProvider.setPersistedClientStore(persistedClientStore);
		return new LoginFormAuthProvider();
	}
	// ****Access Token providers end****

}

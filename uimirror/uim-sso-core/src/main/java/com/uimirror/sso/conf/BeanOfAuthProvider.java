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
package com.uimirror.sso.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.sso.auth.provider.APIKeyAuthProvider;
import com.uimirror.sso.auth.provider.AccessKeyAuthProvider;
import com.uimirror.sso.auth.provider.AuthenticationProvider;
import com.uimirror.sso.auth.provider.OTPAuthProvider;
import com.uimirror.sso.auth.provider.SecretCodeAuthProvider;
import com.uimirror.sso.client.ClientAuthorizationAuthProvider;
import com.uimirror.sso.token.AccessTokenProvider;
import com.uimirror.sso.token.PersistedAccessTokenProvider;

/**
 * @author Jay
 */
@Configuration
public class BeanOfAuthProvider {

	// ****Access Token providers****
	@Bean
	public AccessTokenProvider persistedAccessTokenProvider() {
		return new PersistedAccessTokenProvider();
	}
	
	@Bean
	public AuthenticationProvider apiKeyAuthProvider(){
		return new APIKeyAuthProvider();
	}
	
	@Bean
	public AuthenticationProvider secretCodeAuthProvider(){
		return new SecretCodeAuthProvider();
	}
	
	@Bean
	public AuthenticationProvider accessKeyAuthProvider(){
		return new AccessKeyAuthProvider();
	}
	@Bean
	public AuthenticationProvider clientAuthorizationAuthProvider(){
		return new ClientAuthorizationAuthProvider();
	}
	
	@Bean
	public AuthenticationProvider otpAuthProvider(){
		return new OTPAuthProvider();
	}
	// ****Access Token providers end****

}

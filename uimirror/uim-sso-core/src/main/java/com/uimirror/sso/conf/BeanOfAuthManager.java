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

import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.client.manager.ClientAuthorizationAuthenticationManager;
import com.uimirror.sso.manager.APIKeyAuthManager;
import com.uimirror.sso.manager.OTPAuthenticationManager;
import com.uimirror.sso.manager.SecretKeyAuthManager;
import com.uimirror.sso.token.AccessKeyAuthManager;

/**
 * @author Jay
 */
@Configuration
public class BeanOfAuthManager {
	//****Authentication managers****
	@Bean
	public AuthenticationManager secretKeyAuthManager(){
		return new SecretKeyAuthManager();
	}
	@Bean
	public AuthenticationManager apiKeyAuthManager(){
		return new APIKeyAuthManager();
	}
	
	@Bean
	public AuthenticationManager accessKeyAuthManager(){
		return new AccessKeyAuthManager();
	}
	
	@Bean
	public AuthenticationManager otpAuthManager(){
		return new OTPAuthenticationManager();
	}
	
	@Bean
	public AuthenticationManager clientAuthorizationAuthManager(){
		return new ClientAuthorizationAuthenticationManager();
	}
	//****Authentication managers end****

}

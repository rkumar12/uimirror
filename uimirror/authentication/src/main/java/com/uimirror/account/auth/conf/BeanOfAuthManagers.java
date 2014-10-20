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
package com.uimirror.account.auth.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.account.auth.client.manager.APIKeyAuthManager;
import com.uimirror.account.auth.client.manager.SecretKeyAuthManager;
import com.uimirror.account.auth.core.AuthenticationManager;
import com.uimirror.account.auth.user.ClientAuthorizationAuthenticationManager;
import com.uimirror.account.auth.user.LoginFormAuthenticationManager;
import com.uimirror.account.auth.user.OTPAuthenticationManager;
import com.uimirror.account.auth.user.ScreenLockAuthenticationManager;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfAuthManagers {

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
	public AuthenticationManager screenLockAuthManager(){
		return new ScreenLockAuthenticationManager();
	}
	@Bean
	public AuthenticationManager otpAuthManager(){
		return new OTPAuthenticationManager();
	}
	@Bean
	public AuthenticationManager loginFormAuthManager(){
		return new LoginFormAuthenticationManager();
	}
	@Bean
	public AuthenticationManager clientAuthorizationAuthManager(){
		return new ClientAuthorizationAuthenticationManager();
	}
	@Bean
	public AuthenticationManager accessKeyAuthManager(){
		//TODO change to appropriate bean
		return new LoginFormAuthenticationManager();
	}
	//****Authentication managers end****
}

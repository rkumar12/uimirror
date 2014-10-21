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

import com.uimirror.account.auth.client.provider.APIKeyAuthProvider;
import com.uimirror.account.auth.client.provider.SecretCodeAuthProvider;
import com.uimirror.account.auth.controller.AuthenticationProvider;
import com.uimirror.account.auth.token.provider.AccessKeyAuthProvider;
import com.uimirror.account.auth.user.provider.ClientAuthorizationAuthProvider;
import com.uimirror.account.auth.user.provider.LoginFormAuthProvider;
import com.uimirror.account.auth.user.provider.OTPAuthProvider;
import com.uimirror.account.auth.user.provider.ScreenLockAuthProvider;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfAuthProviders {

	//****Authentication providers****
	@Bean
	public AuthenticationProvider secretCodeAuthProvider(){
		return new SecretCodeAuthProvider();
	}

	@Bean
	public AuthenticationProvider accessKeyAuthProvider(){
		return new AccessKeyAuthProvider();
	}

	@Bean
	public AuthenticationProvider apiKeyAuthProvider(){
		return new APIKeyAuthProvider();
	}

	@Bean
	public AuthenticationProvider screenLockAuthProvider(){
		return new ScreenLockAuthProvider();
	}

	@Bean
	public AuthenticationProvider otpAuthProvider(){
		return new OTPAuthProvider();
	}
	
	@Bean
	public AuthenticationProvider loginFormAuthProvider(){
		return new LoginFormAuthProvider();
	}

	@Bean
	public AuthenticationProvider clientAuthorizationAuthProvider(){
		return new ClientAuthorizationAuthProvider();
	}
	//****Authentication providers end****

}

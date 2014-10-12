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
package com.uimirror.auth.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.auth.client.provider.APIKeyAuthProvider;
import com.uimirror.auth.client.provider.AccessKeyAuthProvider;
import com.uimirror.auth.client.provider.SecretCodeAuthProvider;
import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.user.provider.LoginFormAuthProvider;
import com.uimirror.auth.user.provider.OTPAuthProvider;
import com.uimirror.auth.user.provider.ScreenLockAuthProvider;

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
	//****Authentication providers end****

}

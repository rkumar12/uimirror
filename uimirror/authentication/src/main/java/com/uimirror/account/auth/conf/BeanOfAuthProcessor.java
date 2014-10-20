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

import com.uimirror.account.auth.client.form.ClientAPIForm;
import com.uimirror.account.auth.client.form.ClientSecretKeyForm;
import com.uimirror.account.auth.client.processor.APIKeyProcessor;
import com.uimirror.account.auth.client.processor.AccessTokenProcessor;
import com.uimirror.account.auth.client.processor.SecretKeyProcessor;
import com.uimirror.account.auth.core.processor.OTPAuthProcessor;
import com.uimirror.account.auth.core.processor.ScreenLockAuthProcessor;
import com.uimirror.account.auth.user.form.AuthorizeClientAuthenticationForm;
import com.uimirror.account.auth.user.form.LoginForm;
import com.uimirror.account.auth.user.form.OTPAuthenticationForm;
import com.uimirror.account.auth.user.form.ScreenLockAuthenticationForm;
import com.uimirror.account.auth.user.processor.AllowClientProcessor;
import com.uimirror.account.auth.user.processor.AuthorizationClientProcessor;
import com.uimirror.account.auth.user.processor.LoginFormAuthProcessor;
import com.uimirror.account.user.bean.UserAuthorizedClient;
import com.uimirror.core.Processor;
import com.uimirror.core.form.AuthenticatedHeaderForm;

/**
 * Contains the bean definition for all the authentication controller process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfAuthProcessor {
	
	@Bean
	public Processor<LoginForm> loginFormAuthProcessor(){
		return new LoginFormAuthProcessor();
	}
	
	@Bean
	public Processor<OTPAuthenticationForm> otpAuthProcessor(){
		return new OTPAuthProcessor();
	}
	
	@Bean
	public Processor<ScreenLockAuthenticationForm> screenLockAuthProcessor(){
		return new ScreenLockAuthProcessor();
	}
	
	@Bean
	public Processor<ClientSecretKeyForm> secretKeyProcessor(){
		return new SecretKeyProcessor();
	}
	
	@Bean
	public Processor<ClientAPIForm> apiKeyProcessor(){
		return new APIKeyProcessor();
	}

	@Bean
	public Processor<AuthenticatedHeaderForm> accessTokenProcessor(){
		return new AccessTokenProcessor();
	}

	@Bean
	public Processor<AuthorizeClientAuthenticationForm> authorizationClientProcessor(){
		return new AuthorizationClientProcessor();
	}

	@Bean
	public Processor<UserAuthorizedClient> allowClientprocessor(){
		return new AllowClientProcessor();
	}

}

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
import com.uimirror.account.auth.client.processor.APIKeyAuthenticateProcessor;
import com.uimirror.account.auth.client.processor.APIKeyProcessor;
import com.uimirror.account.auth.client.processor.SecretKeyProcessor;
import com.uimirror.account.auth.core.processor.OTPAuthProcessor;
import com.uimirror.account.auth.core.processor.ScreenLockAuthProcessor;
import com.uimirror.account.auth.token.processor.AuthenticateAccessTokenProcessor;
import com.uimirror.account.auth.token.processor.RefreshAbleAccessTokenProcessor;
import com.uimirror.account.auth.token.processor.RefreshAccessTokenProcessor;
import com.uimirror.account.auth.token.processor.ValidateAccessTokenProcessor;
import com.uimirror.account.auth.user.form.AuthorizeClientAuthenticationForm;
import com.uimirror.account.auth.user.form.LoginForm;
import com.uimirror.account.auth.user.form.OTPAuthenticationForm;
import com.uimirror.account.auth.user.form.ScreenLockAuthenticationForm;
import com.uimirror.account.auth.user.processor.AllowClientProcessor;
import com.uimirror.account.auth.user.processor.AuthorizationClientProcessor;
import com.uimirror.account.auth.user.processor.LoginFormAuthProcessor;
import com.uimirror.account.client.bean.Client;
import com.uimirror.account.user.UserAuthorizedClient;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.form.AuthenticatedHeaderForm;

/**
 * Contains the bean definition for all the authentication controller process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfAuthProcessor {
	
	@Bean
	public Processor<LoginForm, String> loginFormAuthProcessor(){
		return new LoginFormAuthProcessor();
	}
	
	@Bean
	public Processor<OTPAuthenticationForm, String> otpAuthProcessor(){
		return new OTPAuthProcessor();
	}
	
	@Bean
	public Processor<ScreenLockAuthenticationForm, String> screenLockAuthProcessor(){
		return new ScreenLockAuthProcessor();
	}
	
	@Bean
	public Processor<ClientSecretKeyForm, String> secretKeyProcessor(){
		return new SecretKeyProcessor();
	}
	
	@Bean
	public Processor<ClientAPIForm, String> apiKeyProcessor(){
		return new APIKeyProcessor();
	}
	
	@Bean
	public Processor<AuthenticatedHeaderForm, Authentication> refreshAccessTokenProcessor(){
		return new RefreshAccessTokenProcessor();
	}

	@Bean
	public Processor<AuthenticatedHeaderForm, String> refreshAbleAccessTokenProcessor(){
		return new RefreshAbleAccessTokenProcessor();
	}
	
	@Bean
	public Processor<AuthenticatedHeaderForm, String> validateAccessTokenProcessor(){
		return new ValidateAccessTokenProcessor();
	}

	@Bean
	public Processor<AuthenticatedHeaderForm, Authentication> authenticateAccessTokenProcessor(){
		return new AuthenticateAccessTokenProcessor();
	}

	@Bean
	public Processor<AuthorizeClientAuthenticationForm, String> authorizationClientProcessor(){
		return new AuthorizationClientProcessor();
	}

	@Bean
	public Processor<UserAuthorizedClient, Object> allowClientprocessor(){
		return new AllowClientProcessor();
	}

	
	@Bean
	public Processor<Authentication, Client> apiKeyAuthenticateProcessor(){
		return new APIKeyAuthenticateProcessor();
	}

}

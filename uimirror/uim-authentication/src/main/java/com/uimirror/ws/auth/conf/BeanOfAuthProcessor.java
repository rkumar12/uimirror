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
package com.uimirror.ws.auth.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.core.Processor;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.form.AuthenticatedHeaderForm;
import com.uimirror.sso.form.ClientAPIForm;
import com.uimirror.sso.form.ClientSecretKeyForm;
import com.uimirror.ws.auth.form.AuthorizeClientAuthenticationForm;
import com.uimirror.ws.auth.form.LoginForm;
import com.uimirror.ws.auth.form.OTPAuthenticationForm;
import com.uimirror.ws.auth.form.ScreenLockAuthenticationForm;
import com.uimirror.ws.auth.processor.APIKeyProcessor;
import com.uimirror.ws.auth.processor.AuthorizationClientProcessor;
import com.uimirror.ws.auth.processor.LogOutAuthProcessor;
import com.uimirror.ws.auth.processor.LoginFormAuthProcessor;
import com.uimirror.ws.auth.processor.OTPAuthProcessor;
import com.uimirror.ws.auth.processor.ScreenLockAuthProcessor;
import com.uimirror.ws.auth.processor.SecretKeyProcessor;
import com.uimirror.ws.auth.token.AuthenticateAccessTokenProcessor;
import com.uimirror.ws.auth.token.RefreshAbleAccessTokenProcessor;
import com.uimirror.ws.auth.token.RefreshAccessTokenProcessor;
import com.uimirror.ws.auth.token.ValidateAccessTokenProcessor;

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
	public Processor<AuthenticatedHeaderForm, String> logOutAuthProcessor(){
		return new LogOutAuthProcessor();
	}

}

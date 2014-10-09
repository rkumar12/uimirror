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

import com.uimirror.auth.client.bean.form.ClientAPIForm;
import com.uimirror.auth.client.bean.form.ClientSecretKeyForm;
import com.uimirror.auth.client.processor.AccessTokenProcessor;
import com.uimirror.auth.client.processor.SecurityTokenProcessor;
import com.uimirror.auth.client.processor.APIKeyProcessor;
import com.uimirror.auth.controller.Processor;
import com.uimirror.auth.user.bean.form.LoginFormAuthenticationForm;
import com.uimirror.auth.user.bean.form.OTPAuthenticationForm;
import com.uimirror.auth.user.bean.form.ScreenLockAuthenticationForm;
import com.uimirror.auth.user.processor.LoginFormAuthProcessor;
import com.uimirror.auth.user.processor.OTPAuthProcessor;
import com.uimirror.auth.user.processor.ScreenLockAuthProcessor;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;

/**
 * Contains the bean definition for all the authentication controller process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfAuthProcessor {
	
	@Bean
	public Processor<LoginFormAuthenticationForm> loginFormAuthProcessor(){
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
	public Processor<ClientSecretKeyForm> accessTokenProcessor(){
		return new SecurityTokenProcessor();
	}
	
	@Bean
	public Processor<ClientAPIForm> secretCodeProcessor(){
		return new APIKeyProcessor();
	}

	@Bean
	public Processor<AuthenticatedHeaderForm> accessTokenExtraProcessor(){
		return new AccessTokenProcessor();
	}

}

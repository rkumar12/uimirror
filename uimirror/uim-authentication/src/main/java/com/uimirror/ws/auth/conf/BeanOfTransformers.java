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

import com.uimirror.core.service.TransformerService;
import com.uimirror.sso.auth.ClientAuthorizationAuthentication;
import com.uimirror.sso.auth.LoginAuthentication;
import com.uimirror.sso.auth.OTPAuthentication;
import com.uimirror.sso.auth.ScreenLockAuthentication;
import com.uimirror.ws.auth.form.AuthorizeClientAuthenticationForm;
import com.uimirror.ws.auth.form.LoginForm;
import com.uimirror.ws.auth.form.OTPAuthenticationForm;
import com.uimirror.ws.auth.form.ScreenLockAuthenticationForm;
import com.uimirror.ws.auth.transformer.ClientAutorizeFormToAuthTransformer;
import com.uimirror.ws.auth.transformer.LoginFormToAuthTransformer;
import com.uimirror.ws.auth.transformer.OTPFormToAuthTransformer;
import com.uimirror.ws.auth.transformer.ScreenLockFormToAuthTransformer;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfTransformers {

	//****Transformers****
	@Bean
	public TransformerService<ScreenLockAuthenticationForm, ScreenLockAuthentication> screenLockFormToAuthTransformer(){
		return new ScreenLockFormToAuthTransformer();
	}

	@Bean
	public TransformerService<OTPAuthenticationForm, OTPAuthentication> otpFormToAuthTransformer(){
		return new OTPFormToAuthTransformer();
	}

	@Bean
	public TransformerService<LoginForm, LoginAuthentication> loginFormToAuthTransformer(){
		return new LoginFormToAuthTransformer();
	}

	@Bean
	public TransformerService<AuthorizeClientAuthenticationForm, ClientAuthorizationAuthentication> clientAuthorizationFormToAuthTransformer(){
		return new ClientAutorizeFormToAuthTransformer();
	}
	//****Transformers end****

}

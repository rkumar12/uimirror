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

import com.uimirror.auth.client.bean.OAuth2APIKeyAuthentication;
import com.uimirror.auth.client.bean.OAuth2Authentication;
import com.uimirror.auth.client.bean.OAuth2SecretKeyAuthentication;
import com.uimirror.auth.client.bean.form.ClientAPIForm;
import com.uimirror.auth.client.bean.form.ClientSecretKeyForm;
import com.uimirror.auth.client.transformer.APIKeyToAuthTransformer;
import com.uimirror.auth.client.transformer.AccessTokenToAuthTransformer;
import com.uimirror.auth.client.transformer.SecretKeyToAuthTransformer;
import com.uimirror.auth.user.UserAuthorizedClient;
import com.uimirror.auth.user.bean.ClientAuthorizationAuthentication;
import com.uimirror.auth.user.bean.LoginFormAuthentication;
import com.uimirror.auth.user.bean.OTPAuthentication;
import com.uimirror.auth.user.bean.ScreenLockAuthentication;
import com.uimirror.auth.user.bean.form.AuthorizeClientAuthenticationForm;
import com.uimirror.auth.user.bean.form.LoginFormAuthenticationForm;
import com.uimirror.auth.user.bean.form.OTPAuthenticationForm;
import com.uimirror.auth.user.bean.form.ScreenLockAuthenticationForm;
import com.uimirror.auth.user.transformer.ClientAutorizeFormToAuthTransformer;
import com.uimirror.auth.user.transformer.LoginFormToAuthTransformer;
import com.uimirror.auth.user.transformer.OTPFormToAuthTransformer;
import com.uimirror.auth.user.transformer.ScreenLockFormToAuthTransformer;
import com.uimirror.auth.user.transformer.TokenToAuthorizedClientTransformer;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;
import com.uimirror.core.service.TransformerService;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfTransformers {

	//****Transformers****
	@Bean
	public TransformerService<ClientSecretKeyForm, OAuth2SecretKeyAuthentication> secretKeyToAuthTransformer(){
		return new SecretKeyToAuthTransformer();
	}
	
	@Bean
	public TransformerService<AuthenticatedHeaderForm, OAuth2Authentication> accessTokenToAuthTransformer(){
		return new AccessTokenToAuthTransformer();
	}
	
	@Bean
	public TransformerService<ClientAPIForm, OAuth2APIKeyAuthentication> apiKeyToAuthTransformer(){
		return new APIKeyToAuthTransformer();
	}

	@Bean
	public TransformerService<ScreenLockAuthenticationForm, ScreenLockAuthentication> screenLockFormToAuthTransformer(){
		return new ScreenLockFormToAuthTransformer();
	}

	@Bean
	public TransformerService<OTPAuthenticationForm, OTPAuthentication> otpFormToAuthTransformer(){
		return new OTPFormToAuthTransformer();
	}

	@Bean
	public TransformerService<LoginFormAuthenticationForm, LoginFormAuthentication> loginFormToAuthTransformer(){
		return new LoginFormToAuthTransformer();
	}

	@Bean
	public TransformerService<AuthorizeClientAuthenticationForm, ClientAuthorizationAuthentication> clientAuthorizationFormToAuthTransformer(){
		return new ClientAutorizeFormToAuthTransformer();
	}
	@Bean
	public TransformerService<AccessToken, UserAuthorizedClient> tokenToAuthorizedClientTransformer(){
		return new TokenToAuthorizedClientTransformer();
	}
	
	//****Transformers end****

}

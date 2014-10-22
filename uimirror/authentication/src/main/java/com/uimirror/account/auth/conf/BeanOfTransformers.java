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

import com.uimirror.account.auth.client.APIKeyAuthentication;
import com.uimirror.account.auth.client.OAuth2Authentication;
import com.uimirror.account.auth.client.OAuth2SecretKeyAuthentication;
import com.uimirror.account.auth.client.form.ClientAPIForm;
import com.uimirror.account.auth.client.form.ClientSecretKeyForm;
import com.uimirror.account.auth.client.transformer.APIKeyToAuthTransformer;
import com.uimirror.account.auth.client.transformer.SecretKeyToAuthTransformer;
import com.uimirror.account.auth.token.transformer.AccessTokenToAuthTransformer;
import com.uimirror.account.auth.user.bean.ClientAuthorizationAuthentication;
import com.uimirror.account.auth.user.bean.LoginAuthentication;
import com.uimirror.account.auth.user.bean.OTPAuthentication;
import com.uimirror.account.auth.user.bean.ScreenLockAuthentication;
import com.uimirror.account.auth.user.form.AuthorizeClientAuthenticationForm;
import com.uimirror.account.auth.user.form.LoginForm;
import com.uimirror.account.auth.user.form.OTPAuthenticationForm;
import com.uimirror.account.auth.user.form.ScreenLockAuthenticationForm;
import com.uimirror.account.auth.user.transformer.ClientAutorizeFormToAuthTransformer;
import com.uimirror.account.auth.user.transformer.LoginFormToAuthTransformer;
import com.uimirror.account.auth.user.transformer.OTPFormToAuthTransformer;
import com.uimirror.account.auth.user.transformer.ScreenLockFormToAuthTransformer;
import com.uimirror.account.auth.user.transformer.TokenToAuthorizedClientTransformer;
import com.uimirror.account.user.UserAuthorizedClient;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.form.AuthenticatedHeaderForm;
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
	public TransformerService<ClientAPIForm, APIKeyAuthentication> apiKeyToAuthTransformer(){
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
	public TransformerService<LoginForm, LoginAuthentication> loginFormToAuthTransformer(){
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

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
package com.uimirror.sso.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.form.AuthenticatedHeaderForm;
import com.uimirror.core.service.TransformerService;
import com.uimirror.sso.auth.APIKeyAuthentication;
import com.uimirror.sso.auth.OAuth2Authentication;
import com.uimirror.sso.auth.OAuth2SecretKeyAuthentication;
import com.uimirror.sso.client.UserAuthorizedClient;
import com.uimirror.sso.client.transformer.TokenToAuthorizedClientTransformer;
import com.uimirror.sso.form.ClientAPIForm;
import com.uimirror.sso.form.ClientSecretKeyForm;
import com.uimirror.sso.transformer.APIKeyToAuthTransformer;
import com.uimirror.sso.transformer.AccessTokenToAuthTransformer;
import com.uimirror.sso.transformer.SecretKeyToAuthTransformer;

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
	public TransformerService<ClientAPIForm, APIKeyAuthentication> apiKeyToAuthTransformer(){
		return new APIKeyToAuthTransformer();
	}
	
	@Bean
	public TransformerService<AccessToken, UserAuthorizedClient> tokenToAuthorizedClientTransformer(){
		return new TokenToAuthorizedClientTransformer();
	}
	
	@Bean
	public TransformerService<AuthenticatedHeaderForm, OAuth2Authentication> accessTokenToAuthTransformer(){
		return new AccessTokenToAuthTransformer();
	}
	//****Transformers end****

}

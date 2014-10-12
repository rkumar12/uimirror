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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.uimirror.auth.client.bean.OAuth2APIKeyAuthentication;
import com.uimirror.auth.client.bean.OAuth2Authentication;
import com.uimirror.auth.client.bean.OAuth2SecretKeyAuthentication;
import com.uimirror.auth.client.bean.form.ClientAPIForm;
import com.uimirror.auth.client.bean.form.ClientSecretKeyForm;
import com.uimirror.auth.client.transformer.APIKeyToAuthTransformer;
import com.uimirror.auth.client.transformer.AccessTokenToAuthTransformer;
import com.uimirror.auth.client.transformer.SecretKeyToAuthTransformer;
import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.core.PasswordMatcher;
import com.uimirror.auth.core.PersistedAccessTokenProvider;
import com.uimirror.auth.user.LoginFormAuthenticationManager;
import com.uimirror.auth.user.bean.LoginFormAuthentication;
import com.uimirror.auth.user.bean.OTPAuthentication;
import com.uimirror.auth.user.bean.ScreenLockAuthentication;
import com.uimirror.auth.user.bean.form.LoginFormAuthenticationForm;
import com.uimirror.auth.user.bean.form.OTPAuthenticationForm;
import com.uimirror.auth.user.bean.form.ScreenLockAuthenticationForm;
import com.uimirror.auth.user.provider.LoginFormAuthProvider;
import com.uimirror.auth.user.transformer.LoginFormToAuthTransformer;
import com.uimirror.auth.user.transformer.OTPFormToAuthTransformer;
import com.uimirror.auth.user.transformer.ScreenLockFormToAuthTransformer;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;
import com.uimirror.core.crypto.CryptoMatcherService;
import com.uimirror.core.crypto.MatcherServiceImpl;
import com.uimirror.core.rest.extra.JsonResponseTransFormer;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.rest.extra.ResponseTransFormerFactory;
import com.uimirror.core.rest.extra.TransformResponseAspect;
import com.uimirror.core.service.TransformerService;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
@Import({BeanOfExceptionIntitializer.class, BeanOfAuthProcessor.class})
public class BeanIntitializer {

	@Bean(name="json")
	public ResponseTransFormer<String> jsonResponseTransFormer(){
		return new JsonResponseTransFormer();
	}
	
	@Bean
	public TransformResponseAspect transformResponseAspect(){
		return new TransformResponseAspect();
	}
	
	@Bean
	public ServiceLocatorFactoryBean responseTransFormerFactory(){
		ServiceLocatorFactoryBean sb = new ServiceLocatorFactoryBean();
		sb.setServiceLocatorInterface(ResponseTransFormerFactory.class);
		return sb;
	}
	
	@Bean
	public CryptoMatcherService cryptoMatcherService(){
		return new MatcherServiceImpl();
	}
	
	@Bean
	@Autowired
	public PasswordMatcher passwordMatcher(CryptoMatcherService cryptoMatcherService){
		return new PasswordMatcher(cryptoMatcherService);
	}
	
	@Bean
	public AuthenticationManager loginFormAuthenticationManager(){
		return new LoginFormAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider loginFormAuthenticationProvider(){
		return new LoginFormAuthProvider();
	}
	
	@Bean
	public AccessTokenProvider persistedAccessTokenProvider(){
		return new PersistedAccessTokenProvider();
	}
	
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

}

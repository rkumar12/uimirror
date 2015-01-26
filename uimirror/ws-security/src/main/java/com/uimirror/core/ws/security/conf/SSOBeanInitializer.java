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
package com.uimirror.core.ws.security.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.uimirror.core.crypto.CryptoMatcherService;
import com.uimirror.sso.PasswordMatcher;

/**
 * @author Jay
 */
@Configuration
@Import({BeanOfAuthManager.class, BeanOfAuthProvider.class
	, BeanOfStore.class
	, BeanOfAuthProcessor.class, BeanOfTransformers.class
	, BeanOfBackGroundProcessor.class, BeanOfDaoIntitializer.class})
public class SSOBeanInitializer {

	@Bean
	@Autowired
	public PasswordMatcher passwordMatcher(CryptoMatcherService cryptoMatcherService){
		return new PasswordMatcher(cryptoMatcherService);
	}

}

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

import com.uimirror.account.auth.user.provider.LoginFormAuthProvider;
import com.uimirror.account.auth.user.provider.ScreenLockAuthProvider;
import com.uimirror.sso.auth.provider.AuthenticationProvider;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfAuthProviders {

	//****Authentication providers****

	@Bean
	public AuthenticationProvider screenLockAuthProvider(){
		return new ScreenLockAuthProvider();
	}
	
	@Bean
	public AuthenticationProvider loginFormAuthProvider(){
		return new LoginFormAuthProvider();
	}
	
	//****Authentication providers end****

}

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

import com.uimirror.account.auth.user.LoginFormAuthenticationManager;
import com.uimirror.account.auth.user.ScreenLockAuthenticationManager;
import com.uimirror.sso.AuthenticationManager;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfAuthManagers {

	//****Authentication managers****
	@Bean
	public AuthenticationManager screenLockAuthManager(){
		return new ScreenLockAuthenticationManager();
	}
	
	@Bean
	public AuthenticationManager loginFormAuthManager(){
		return new LoginFormAuthenticationManager();
	}
	//****Authentication managers end****
}

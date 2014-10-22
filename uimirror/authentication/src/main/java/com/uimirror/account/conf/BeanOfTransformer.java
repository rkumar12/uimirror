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
package com.uimirror.account.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.account.client.bean.Client;
import com.uimirror.account.client.transformer.ClientRegisterFormToClientTransformer;
import com.uimirror.account.user.form.RegisterForm;
import com.uimirror.account.user.transformer.RegisterFormToUserTransformer;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.user.DefaultUser;

/**
 * Contains the bean definition for all the authentication controller process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfTransformer {

	@Bean
	public TransformerService<com.uimirror.account.client.form.RegisterForm, Client> clientRegisterFormToClientTransformer(){
		return new ClientRegisterFormToClientTransformer();
	}
	
	@Bean
	public TransformerService<RegisterForm, DefaultUser> registerFormToUser(){
		return new RegisterFormToUserTransformer();
	}

}

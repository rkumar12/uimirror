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

import com.uimirror.account.client.form.RegisterForm;
import com.uimirror.account.client.processor.CreateClientAccountProcessor;
import com.uimirror.core.Processor;

/**
 * Contains the bean definition for all the authentication controller process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfProcessor {

	@Bean
	public Processor<RegisterForm, String> createClientAccountProcessor(){
		return new CreateClientAccountProcessor();
	}

}

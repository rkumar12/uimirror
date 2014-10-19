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
import org.springframework.scheduling.annotation.EnableScheduling;

import com.uimirror.auth.scedular.TokenDeleteScheduler;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
@EnableScheduling
public class BeanOfSchedulers {

	//****Scheduler Tasks****
	@Bean
	public TokenDeleteScheduler tokenDeleteScheduler(){
		return new TokenDeleteScheduler();
	}
	//****Scheduler Tasks end****
}

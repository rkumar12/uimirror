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
package com.uimirror.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.uimirror.core.job.store.SimpleJobStore;
import com.uimirror.user.schedular.DeleteUnVerifiedUserScheduler;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
@EnableScheduling
public class BeanOfSchedulers {

	//****Scheduler Tasks****
	@Bean
	@Autowired
	public DeleteUnVerifiedUserScheduler deleteOrphanUserScheduler(SimpleJobStore persistedAccountJobMongoStore){
		return new DeleteUnVerifiedUserScheduler(persistedAccountJobMongoStore);
	}
	//****Scheduler Tasks end****
}

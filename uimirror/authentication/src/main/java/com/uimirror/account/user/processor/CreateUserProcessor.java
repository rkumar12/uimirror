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
package com.uimirror.account.user.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.user.form.RegisterForm;
import com.uimirror.core.Processor;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.user.processor.BackGroundCreateUserProcessor;
import com.uimirror.user.store.DefaultUserStore;

/**
 * Processor for the user account creation, it will first check for the user existence
 * if not found will try to create a new else, else stop
 * Steps are as below
 * <ol>
 * <li>Validate and get the token details</li>
 * <li>Check if provided details are valid</li>
 * <li>Transform to the {@link DefaultUser}</li>
 * <li>Send verification mail in background</li>
 * </ol>
 * 
 * @author Jay
 */
public class CreateUserProcessor implements Processor<RegisterForm, DefaultUser>{

	protected static final Logger LOG = LoggerFactory.getLogger(CreateUserProcessor.class);
	private @Autowired TransformerService<RegisterForm, DefaultUser> registerFormToUser;
	private @Autowired DefaultUserStore persistedDefaultUserMongoStore;
	private @Autowired BackgroundProcessorFactory<DefaultUser, Object> backgroundProcessorFactory; 

	public CreateUserProcessor() {
		// NOP
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public DefaultUser invoke(RegisterForm param) throws ApplicationException {
		LOG.info("[START]- Registering a new User.");
		DefaultUser user = registerFormToUser.transform(param);
		//Step 1- Create a flat user in temp collection first
		user = createUerBasic(user);
		//Step 2- Invoke a background process which will create user in different different collection separately
		createUserInBackGround(user);
		LOG.info("[END]- Registering a new User.");
		return user;
	}

	private void createUserInBackGround(DefaultUser user){
		backgroundProcessorFactory.getProcessor(BackGroundCreateUserProcessor.NAME).invoke(user);
	}

	/**
	 * Create Basic Profile and get the updated user
	 * @param user
	 * @return
	 */
	private DefaultUser createUerBasic(DefaultUser user) {
		DefaultUser tempUser =  persistedDefaultUserMongoStore.store(user);
		return tempUser;
	}

}

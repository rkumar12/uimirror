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

import com.uimirror.account.client.bean.Client;
import com.uimirror.account.user.dao.UserBasicInfoStore;
import com.uimirror.account.user.form.RegisterForm;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.user.BasicUserInfo;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.user.UserCredentials;

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
	private @Autowired Processor<Authentication, Client> apiKeyAuthenticateProcessor;
	private @Autowired TransformerService<RegisterForm, DefaultUser> registerFormToUser;
	private @Autowired UserBasicInfoStore persistedUserBasicInfoMongoStore;
	

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
		BasicUserInfo userInfo = createUerBasic(user.getUserInfo());
		String profileId = userInfo.getProfileId();
		UserCredentials credentials = storeCredentials(user.getCredentials(), profileId);
		LOG.info("[END]- Registering a new User.");
		return null;
	}


	/**
	 * Create Basic Profile and get the updated user
	 * @param userInfo
	 * @return
	 */
	private BasicUserInfo createUerBasic(BasicUserInfo userInfo) {
		BasicUserInfo basicUser =  persistedUserBasicInfoMongoStore.store(userInfo);
		return basicUser;
	}

	/**
	 * update the profile id and store the details
	 * @param credentials
	 * @return
	 */
	private UserCredentials storeCredentials(UserCredentials credentials, String profileId) {
		credentials = credentials.updateProfileId(profileId);
		
		return credentials;
	}
}

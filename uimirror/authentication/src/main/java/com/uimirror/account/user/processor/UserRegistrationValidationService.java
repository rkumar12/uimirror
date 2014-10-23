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

import com.uimirror.account.auth.dao.AccessTokenStore;
import com.uimirror.account.user.dao.DefaultUserStore;
import com.uimirror.account.user.dao.UserBasicInfoStore;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.dao.RecordNotFoundException;
import com.uimirror.core.rest.extra.AlreadyExistException;
import com.uimirror.core.service.ValidatorService;
import com.uimirror.core.user.BasicInfo;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;

/**
 * Processor for the user account validation, it will check if the user provided email has been
 * already exists with us, if exists, then it tries to find if that user has recently registered but not yet verified
 * his account, so tries to send him back to the verify page.
 * </ol>
 * 
 * @author Jay
 */
public class UserRegistrationValidationService implements ValidatorService<String>{

	protected static final Logger LOG = LoggerFactory.getLogger(UserRegistrationValidationService.class);
	private @Autowired AccessTokenStore persistedAccessTokenMongoStore;
	private @Autowired DefaultUserStore persistedDefaultUserMongoStore;
	private @Autowired UserBasicInfoStore persistedUserBasicInfoMongoStore;
	private @Autowired BackgroundProcessorFactory<DefaultUser, Object> backGroundCreateMissingUserProcessor;
	
	public UserRegistrationValidationService() {
		// NOP
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.ValidatorService#validate(java.lang.Object)
	 */
	@Override
	public boolean validate(String src) {
		DefaultUser user = checkInTempStore(src);
		if(user != null){
			AccessToken earillerToken = getIssuedToken(user.getUserInfo().getProfileId());
			earillerToken = earillerToken == null ? null : earillerToken.eraseEsential();
			throw new AlreadyExistException(earillerToken.toResponseMap());
		}
		BasicInfo userInfo = checkInInfoStore(src);
		if(userInfo != null){
			throw new AlreadyExistException("User is Already exist with us");
		}
		return Boolean.TRUE;
	}
	
	/**
	 * @param email
	 * @return
	 */
	private DefaultUser checkInTempStore(String email){
		DefaultUser user = null;
		try{
			user = persistedDefaultUserMongoStore.getUserByEmail(email);
			backGroundCreateMissingUserProcessor.getProcessor(BackGroundCreateMissingUserProcessor.NAME).invoke(user);
		}catch(RecordNotFoundException e){
			//NOP
		}
		return user;
	}
	
	/**
	 * @param email
	 * @return
	 */
	private BasicInfo checkInInfoStore(String email){
		BasicInfo userInfo = null;
		try{
			userInfo = persistedUserBasicInfoMongoStore.getUserInfoByEmail(email);
		}catch(RecordNotFoundException e){
			//NOP
		}
		return userInfo;
	}
	
	/**
	 * Try to find the earlier token issued to the user for the registeration of the account.
	 * @param profileId
	 * @return
	 */
	private AccessToken getIssuedToken(String profileId){
		return persistedAccessTokenMongoStore.getUserRegisteredWOTPToken(profileId);
	}

}

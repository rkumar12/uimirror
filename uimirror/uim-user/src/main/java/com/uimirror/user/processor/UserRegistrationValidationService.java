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
package com.uimirror.user.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.exceptions.db.RecordNotFoundException;
import com.uimirror.core.rest.extra.AlreadyExistException;
import com.uimirror.core.service.ValidatorService;
import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.BasicInfo;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.user.store.AccountTokenStore;
import com.uimirror.user.store.DefaultUserStore;
import com.uimirror.user.store.UserBasicInfoStore;

/**
 * Processor for the user account validation, it will check if the user provided email has been
 * already exists with us, if exists, then it tries to find if that user has recently registered but not yet verified
 * his account, so tries to send him back to the verify page.
 * 
 * @author Jay
 */
public class UserRegistrationValidationService implements ValidatorService<String>{

	protected static final Logger LOG = LoggerFactory.getLogger(UserRegistrationValidationService.class);
	private AccountTokenStore persistedAccountTokenMongoStore;
	private DefaultUserStore persistedDefaultUserMongoStore;
	private UserBasicInfoStore persistedUserBasicInfoMongoStore;
	
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
			reportAccountExistWithRef(user.getUserInfo().getProfileId(), Boolean.TRUE);
		}
		BasicInfo userInfo = checkInInfoStore(src);
		if(userInfo != null){
			if(userInfo.getAccountState() == AccountState.NEW){
				reportAccountExistWithRef(userInfo.getProfileId(), Boolean.FALSE);
			}else{
				throw new AlreadyExistException("User is Already exist with us");
			}
		}
		return Boolean.TRUE;
	}
	
	private void reportAccountExistWithRef(String profileId, boolean getPrevtoken){
		AccessToken earillerToken = null;
		try{
			if(getPrevtoken)
				earillerToken = getIssuedToken(profileId);
		}catch(RecordNotFoundException e){
			//NOP
		}
		earillerToken = earillerToken == null ? null : earillerToken.eraseEsential();
		throw new AlreadyExistException(earillerToken.toResponseMap());
	}
	
	/**
	 * @param email
	 * @return
	 */
	private DefaultUser checkInTempStore(String email){
		DefaultUser user = null;
		try{
			user = persistedDefaultUserMongoStore.getByEmail(email);
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
		return persistedAccountTokenMongoStore.getUserRegisteredWOTPToken(profileId);
	}

	public void setPersistedAccountTokenMongoStore(
			AccountTokenStore persistedAccountTokenMongoStore) {
		this.persistedAccountTokenMongoStore = persistedAccountTokenMongoStore;
	}

	public void setPersistedDefaultUserMongoStore(
			DefaultUserStore persistedDefaultUserMongoStore) {
		this.persistedDefaultUserMongoStore = persistedDefaultUserMongoStore;
	}

	public void setPersistedUserBasicInfoMongoStore(
			UserBasicInfoStore persistedUserBasicInfoMongoStore) {
		this.persistedUserBasicInfoMongoStore = persistedUserBasicInfoMongoStore;
	}

}

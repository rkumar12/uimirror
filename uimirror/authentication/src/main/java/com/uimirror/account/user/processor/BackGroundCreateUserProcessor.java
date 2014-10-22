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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.user.dao.DefaultUserStore;
import com.uimirror.account.user.dao.UserAccountLogStore;
import com.uimirror.account.user.dao.UserBasicDetailsStore;
import com.uimirror.account.user.dao.UserBasicInfoStore;
import com.uimirror.account.user.dao.UserCredentialsStore;
import com.uimirror.core.user.BasicDetails;
import com.uimirror.core.user.BasicInfo;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.user.AccountLogs;
import com.uimirror.core.user.Credentials;
import com.uimirror.core.util.thread.AbstractBackgroundProcessor;

/**
 * Set of operation that needs to be handled for the Token Expiry
 * <ol>
 * <li>Set the token expire as now</li>
 * </ol> 
 * @author Jay
 */
public class BackGroundCreateUserProcessor extends AbstractBackgroundProcessor<DefaultUser, Object>{

	protected static final Logger LOG = LoggerFactory.getLogger(BackGroundCreateUserProcessor.class);
	
	public static final String NAME = "BGCUP";
	private @Autowired DefaultUserStore persistedDefaultUserMongoStore;
	private @Autowired UserBasicInfoStore persistedUserBasicInfoMongoStore;
	private @Autowired UserCredentialsStore persistedUserCredentialMongoStore;
	private @Autowired UserBasicDetailsStore persistedUserBasicDetailsMongoStore;
	private @Autowired UserAccountLogStore persistedUserAccountLogMongoStore;

	public BackGroundCreateUserProcessor(){
		super(1);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.BackgroundProcessor#invoke(java.lang.Object)
	 */
	@Override
	public void invoke(final DefaultUser user){
		LOG.debug("[START]- Creating User in each individual bucket.");
		getAdaptor().submitTasks(createJobs(user));
		LOG.debug("[END]- Creating User in each individual bucket.");
	}
	
	/**
	 * Creates the Job List
	 * @param token
	 * @return
	 */
	private List<Runnable> createJobs(DefaultUser user){
		List<Runnable> backgrounds = new ArrayList<Runnable>();
		backgrounds.add(new RunInBackGround(user));
		return backgrounds;
	}

	/**
	 * On basics of token id, it will simply update the expire date as now
	 * @param auth
	 * @return
	 */
	private void createUserAndDeleteTempRecord(DefaultUser user){
		LOG.info("[BACKGROUND-START]- Creating User Basic Info.");
		BasicInfo userInfo = user.getUserInfo();
		createBasicUserInfo(userInfo);
		LOG.info("[BACKGROUND-END]- Creating User Basic Info.");
		LOG.info("[BACKGROUND-START]- Creating User Credentials.");
		createUserCredentials(user.getCredentials());
		LOG.info("[BACKGROUND-END]- Creating User Credentials.");
		LOG.info("[BACKGROUND-START]- Creating User Details.");
		createUserDetails(user.getBasicDetails());
		LOG.info("[BACKGROUND-END]- Creating User Details.");
		LOG.info("[BACKGROUND-START]- Creating User logs.");
		createUserLogs(user.getLogs());
		LOG.info("[BACKGROUND-END]- Creating User logs.");
		LOG.info("[BACKGROUND-START]- Deleting User from Temp.");
		deleteTempRecord(userInfo.getProfileId());
		LOG.info("[BACKGROUND-END]- Deleting User from Temp.");
	}
	
	/**
	 * This will create the Basic user Info
	 * @param userInfo
	 */
	private void createBasicUserInfo(BasicInfo userInfo){
		persistedUserBasicInfoMongoStore.store(userInfo);
	}

	/**
	 * This will create the User credentials
	 * @param userInfo
	 */
	private void createUserCredentials(Credentials credentials){
		persistedUserCredentialMongoStore.store(credentials);
	}

	/**
	 * This will create the User credentials
	 * @param userInfo
	 */
	private void createUserDetails(BasicDetails userDetails){
		persistedUserBasicDetailsMongoStore.store(userDetails);
	}

	/**
	 * This will create the User credentials
	 * @param userInfo
	 */
	private void createUserLogs(AccountLogs logs){
		persistedUserAccountLogMongoStore.store(logs);
	}
	
	private void deleteTempRecord(String profileId){
		persistedDefaultUserMongoStore.deleteByprofileId(profileId);
	}

	/**
	 * From a task that can be submitted for background process
	 * @author Jay
	 */
	private class RunInBackGround implements Runnable{
		
		private final DefaultUser user;
		public RunInBackGround(DefaultUser user){
			this.user = user;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			//Create User first and delete the temporarily record 
			createUserAndDeleteTempRecord(user);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.util.thread.BackgroundProcessor#getResult()
	 */
	@Override
	public Object getResult() {
		return getResults();
	}

}

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
package com.uimirror.account.processor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.user.AccountState;
import com.uimirror.core.util.thread.AbstractBackgroundProcessor;
import com.uimirror.user.store.UserCredentialsStore;

/**
 * Set of operation that needs to be handled if user account is in {@link AccountState#DISABLED}
 * This will try to enable the account.
 * <ol>
 * <li>Set the account state as enabled in credentials and basic user profile</li>
 * </ol> 
 * @author Jay
 */
//TODO User basic should have state, so implement that as well
public class UserRestoreProcessor extends AbstractBackgroundProcessor<String, Object>{

	protected static final Logger LOG = LoggerFactory.getLogger(UserRestoreProcessor.class);
	
	public static final String NAME = "URP";
	private @Autowired UserCredentialsStore persistedUserCredentialMongoStore;

	public UserRestoreProcessor(){
		super(1);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.BackgroundProcessor#invoke(java.lang.Object)
	 */
	@Override
	public void invoke(String profileId){
		LOG.debug("[START]- Enabling/ Restoring user.");
		getAdaptor().submitTasks(createJobs(profileId));
		LOG.debug("[END]- Enabling/ Restoring user.");
	}
	
	/**
	 * Creates the Job List
	 * @param token
	 * @return
	 */
	private List<Runnable> createJobs(String profileId){
		List<Runnable> backgrounds = new ArrayList<Runnable>();
		backgrounds.add(new RunInBackGround(profileId));
		return backgrounds;
	}
	
	/**
	 * Enable account in the credentials
	 * @param profileId
	 */
	private void enableInCredentials(String profileId){
		persistedUserCredentialMongoStore.enableAccount(profileId);
	}

	/**
	 * From a task that can be submitted for background process
	 * @author Jay
	 */
	private class RunInBackGround implements Runnable{
		
		private final String profileId;
		public RunInBackGround(String profileId){
			this.profileId = profileId;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			//Step 1- Enable in Credentials first
			enableInCredentials(profileId);
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

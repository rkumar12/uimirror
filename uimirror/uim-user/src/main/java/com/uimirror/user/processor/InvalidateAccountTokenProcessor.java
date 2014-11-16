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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.uimirror.core.auth.token.AccessTokenFields;
import com.uimirror.core.util.thread.AbstractBackgroundProcessor;
import com.uimirror.user.store.AccountTokenStore;

/**
 * Set of operation that needs to be handled for the account token removal
 * <ol>
 * <li>Delete the issued tokens based on the profile id</li>
 * </ol> 
 * @author Jay
 */
public class InvalidateAccountTokenProcessor extends AbstractBackgroundProcessor<String, Object>{

	protected static final Logger LOG = LoggerFactory.getLogger(InvalidateAccountTokenProcessor.class);
	
	public static final String NAME = "INVATP";
	private @Autowired AccountTokenStore persistedAccountTokenMongoStore;

	public InvalidateAccountTokenProcessor(){
		super(1);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.BackgroundProcessor#invoke(java.lang.Object)
	 */
	@Override
	public void invoke(final String profileId){
		if(!StringUtils.hasText(profileId))
			return;
		LOG.debug("[START]- Deleting the token issued for the account creation.");
		getAdaptor().submitTasks(createJobs(profileId));
		LOG.debug("[END]- Deleting the token issued for the account creation.");
	}
	
	/**
	 * Creates the Job List
	 * @param profileId for which account token needs to be deleted
	 * @return list of background jobs
	 */
	private List<Runnable> createJobs(String profileId){
		List<Runnable> backgrounds = new ArrayList<Runnable>();
		backgrounds.add(new RunInBackGround(profileId));
		return backgrounds;
	}

	/**
	 * On basics of profile id, it will delete all the issued token
	 * @param profileId
	 */
	private void delete(String profileId){
		Map<String, Object> query = new WeakHashMap<String, Object>(3);
		query.put(AccessTokenFields.AUTH_TKN_OWNER, profileId);
		persistedAccountTokenMongoStore.deleteByQuery(query);
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
			//Mark as Expired 
			delete(profileId);
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

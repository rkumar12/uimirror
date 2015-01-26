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
package com.uimirror.core.user.schedular;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.uimirror.core.auth.token.AccessTokenFields;
import com.uimirror.core.exceptions.db.RecordNotFoundException;
import com.uimirror.core.job.SimpleJob;
import com.uimirror.core.job.Status;
import com.uimirror.core.job.store.SimpleJobStore;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.user.store.AccountTokenStore;
import com.uimirror.core.user.store.DefaultUserStore;
import com.uimirror.core.util.DateTimeUtil;

/**
 * A Scheduler that will run on every 10 mins to delete all the orphan user's whose account is not verifed in last 24 hours
 * @author Jay
 */
public class DeleteUnVerifiedUserScheduler {

	private SimpleJobStore simpleJobStore;
	private DefaultUserStore persistedDefaultUserMongoStore;
	private AccountTokenStore persistedAccountTokenMongoStore;
	private static final Logger LOG = LoggerFactory.getLogger(DeleteUnVerifiedUserScheduler.class);
	private static final String JOB_NAME = "Delete Temp User";
	
	/**
	 *Delete the user account whoch has been created but not verified after 24 Hours. 
	 */
	@Scheduled(fixedDelay=1800000, initialDelay=200000)
	public void deleteExpired(){
		SimpleJob recentJob = simpleJobStore.findMostRecentJob();
		if(recentJob != null){
			if(recentJob.isRunning())
				return;
		}
		recentJob = new SimpleJob.JobBuilder(null).addName(JOB_NAME).addStartedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).addStatus(Status.STRATED).build();
		recentJob = simpleJobStore.store(recentJob);
		try{
			runJob();
			recentJob = recentJob.markAsComplete();
			simpleJobStore.markAsComplete(recentJob.getJobId());
		}catch(Exception e){
			simpleJobStore.markAsFailed(recentJob.getJobId(), e.getMessage());
		}
		deleteOrphanJobs();
		LOG.info("[SCHEDULED-TEMP-ACCOUNT-END]- User's Temporarly account removal opeartion.");
	}
	
	private void runJob(){
		try{
			LOG.info("[SCHEDULED-TEMP-ACCOUNT-START]- User's Temporarly account removal opeartion.");
			List<DefaultUser> users = persistedDefaultUserMongoStore.getUnVerifiedAccountBefore(24*60);
			for(DefaultUser user : users){
				String profileId = user.getProfileId();
				persistedDefaultUserMongoStore.deleteByprofileId(profileId);
				Map<String, Object> query = new WeakHashMap<String, Object>(3);
				query.put(AccessTokenFields.AUTH_TKN_OWNER, profileId);
				persistedAccountTokenMongoStore.deleteByQuery(query);
			}
		}catch(RecordNotFoundException e){
			LOG.info("No Temp User found for purge.");
		}catch(Exception e){
			LOG.error("[MINIOR-ACCOUNT-JOB]- Something went wrong {}.",e);
			throw e;
		}
	}

	private void deleteOrphanJobs(){
		List<String> jobIds = simpleJobStore.findOrphanJobs();
		for(String jobid : jobIds){
			simpleJobStore.deleteById(jobid);
		}
	}
	public DeleteUnVerifiedUserScheduler(SimpleJobStore simpleJobStore) {
		this.simpleJobStore = simpleJobStore;
	}

	public void setSimpleJobStore(SimpleJobStore simpleJobStore) {
		this.simpleJobStore = simpleJobStore;
	}

	public void setPersistedDefaultUserMongoStore(
			DefaultUserStore persistedDefaultUserMongoStore) {
		this.persistedDefaultUserMongoStore = persistedDefaultUserMongoStore;
	}

	public void setPersistedAccountTokenMongoStore(
			AccountTokenStore persistedAccountTokenMongoStore) {
		this.persistedAccountTokenMongoStore = persistedAccountTokenMongoStore;
	}
	
	
	
}

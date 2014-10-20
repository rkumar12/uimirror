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
package com.uimirror.account.auth.core.processor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.uimirror.account.auth.dao.AccessTokenStore;
import com.uimirror.core.util.thread.AbstractBackgroundProcessor;

/**
 * Set of operation that needs to be handled for the Token Expiry
 * <ol>
 * <li>Set the token expire as now</li>
 * </ol> 
 * @author Jay
 */
public class InvalidateTokenProcessor extends AbstractBackgroundProcessor<String, Object>{

	protected static final Logger LOG = LoggerFactory.getLogger(InvalidateTokenProcessor.class);
	
	public static final String NAME = "INVTP";
	private @Autowired AccessTokenStore persistedAccessTokenMongoStore;

	public InvalidateTokenProcessor(){
		super(1);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.BackgroundProcessor#invoke(java.lang.Object)
	 */
	@Override
	public void invoke(final String token){
		if(!StringUtils.hasText(token))
			return;
		LOG.debug("[START]- Marking the token as expired.");
		getAdaptor().submitTasks(createJobs(token));
		LOG.debug("[END]- Marking the token as expired.");
	}
	
	/**
	 * Creates the Job List
	 * @param token
	 * @return
	 */
	private List<Runnable> createJobs(String token){
		List<Runnable> backgrounds = new ArrayList<Runnable>();
		backgrounds.add(new RunInBackGround(token));
		return backgrounds;
	}

	/**
	 * On basics of token id, it will simply update the expire date as now
	 * @param auth
	 * @return
	 */
	private void markAsExpired(String token){
		persistedAccessTokenMongoStore.markAsExpired(token);
	}

	/**
	 * From a task that can be submitted for background process
	 * @author Jay
	 */
	private class RunInBackGround implements Runnable{
		
		private final String token;
		public RunInBackGround(String token){
			this.token = token;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			//Mark as Expired 
			markAsExpired(token);
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

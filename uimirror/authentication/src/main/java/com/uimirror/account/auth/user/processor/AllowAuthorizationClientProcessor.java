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
package com.uimirror.account.auth.user.processor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.user.bean.UserAuthorizedClient;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.util.thread.AbstractBackgroundProcessor;

/**
 * Set of operation that needs to be handled after user has granted access to the client
 * <ol>
 * <li>insert the new Document with the scope and client id</li>
 * </ol> 
 * @author Jay
 */
public class AllowAuthorizationClientProcessor extends AbstractBackgroundProcessor<AccessToken, Object>{

	protected static final Logger LOG = LoggerFactory.getLogger(AllowAuthorizationClientProcessor.class);
	
	public static final String NAME = "AACP";
	private @Autowired TransformerService<AccessToken, UserAuthorizedClient> tokenToAuthorizedClientTransformer;
	private @Autowired Processor<UserAuthorizedClient> allowClientprocessor;

	public AllowAuthorizationClientProcessor(){
		super(1);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.BackgroundProcessor#invoke(java.lang.Object)
	 */
	@Override
	public void invoke(AccessToken token){
		LOG.debug("[START]- Persisting new Authroized client for the user");
		getAdaptor().submitTasks(createJobs(token));
		LOG.debug("[END]- Persisting new Authroized client for the user");
	}
	
	/**
	 * Creates the Job List
	 * @param token
	 * @return
	 */
	private List<Runnable> createJobs(AccessToken token){
		List<Runnable> backgrounds = new ArrayList<Runnable>();
		backgrounds.add(new RunInBackGround(token));
		return backgrounds;
	}
	
	/**
	 * converts the form bean object into a {@link UserAuthorizedClient} object
	 * @param param
	 * @return
	 */
	private UserAuthorizedClient getTransformedObject(AccessToken token) {
		return tokenToAuthorizedClientTransformer.transform(token);
	}

	/**
	 * On basics of {@link CredentialType}, it will simply validate or generate 
	 * the access token {@link AccessToken}
	 * @param auth
	 * @return
	 */
	private void handleAllowedAuthorizedRequest(UserAuthorizedClient auth){
		allowClientprocessor.invoke(auth);
	}

	/**
	 * From a task that can be submitted for background process
	 * @author Jay
	 */
	private class RunInBackGround implements Runnable{
		
		private final AccessToken token;
		public RunInBackGround(AccessToken token){
			this.token = token;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			//Step 1- Transform the token to the UserAuthorizedClient object
			UserAuthorizedClient authorizedClient = getTransformedObject(token);
			//Finally store the authorized client
			handleAllowedAuthorizedRequest(authorizedClient);
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

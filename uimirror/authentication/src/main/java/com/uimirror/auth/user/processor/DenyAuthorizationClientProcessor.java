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
package com.uimirror.auth.user.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.util.thread.BackgroundProcessor;

/**
 * Set of operation that needs to be handled after user has denied access to the client
 * <ol>
 * <li>No operation currently</li>
 * </ol>
 * @author Jay
 */
public class DenyAuthorizationClientProcessor implements BackgroundProcessor<AccessToken, Object>{

	protected static final Logger LOG = LoggerFactory.getLogger(DenyAuthorizationClientProcessor.class);
	
	public static final String NAME = "DACP";

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.BackgroundProcessor#invoke(java.lang.Object)
	 */
	@Override
	public void invoke(AccessToken token){
		LOG.debug("[START]- Persisting new Authroized client for the user");
		//Step 1- Transform the token to the UserAuthorizedClient object
		//Currently Not doing anything
		LOG.debug("[END]- Persisting new Authroized client for the user");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.util.thread.BackgroundProcessor#getResult()
	 */
	@Override
	public Object getResult() throws IllegalThreadStateException {
		// TODO Auto-generated method stub
		return null;
	}

}
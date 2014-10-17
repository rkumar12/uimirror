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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.user.ClientAuthorizedScope;
import com.uimirror.auth.user.UserAuthorizedClient;
import com.uimirror.auth.user.dao.UserAuthorizedClientStore;
import com.uimirror.core.Processor;

/**
 * Set of operation that needs to be handled after user has granted access to the client
 * <ol>
 * <li>pull the client first</li>
 * <li>do a add to set<li>
 * </ol> 
 * @author Jay
 */
public class AllowClientProcessor implements Processor<UserAuthorizedClient>{

	protected static final Logger LOG = LoggerFactory.getLogger(AllowClientProcessor.class);
	
	private @Autowired UserAuthorizedClientStore persistedUserAuthorizedClientMongoStore;

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.BackgroundHandler#handle(java.lang.Object)
	 */
	@Override
	public Object invoke(UserAuthorizedClient param){
		LOG.info("[BACKGOUND-START]- Authorizing a Client");
		//Validate the requested parameters
		if(param != null && param.isValid()){
			LOG.info("[BACKGROUND-STOP]- Process of authroizing client can't be processed as provided details are not valid");
		}else{
			//First Remove the client if found
			persistedUserAuthorizedClientMongoStore.unAuthorizeAClient(param.getProfileId(), getFirstClientId(param.getClients()));
			//Authorize the Client now
			persistedUserAuthorizedClientMongoStore.authorizeClient(param);
		}
		LOG.info("[BACKGOUND-END]- Authorizing a Client");
		return null;
	}
	
	private String getFirstClientId(List<ClientAuthorizedScope> clients){
		return clients.get(0).getClientId();
	}
	
	

}

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
package com.uimirror.account.auth.user.dao;

import java.util.List;

import com.uimirror.account.user.UserAuthorizedClient;

/**
 * A basic store for the User authorized clients.
 *  
 * @author Jay
 */
public interface UserAuthorizedClientStore {

	/**
	 * Find the client that has been authorized by the user
	 * @param profileId
	 * @param clientId
	 * @return
	 */
	UserAuthorizedClient findAuthrorizedClient(String profileId, String clientId);
	
	/**
	 * Find the clients that has been authorized by the user with given scope
	 * 
	 * @param profileId
	 * @param clientId
	 * @param scope
	 * @return
	 */
	UserAuthorizedClient findAuthrorizedClient(String profileId, String clientId, String scope);
	
	/**
	 * Get all the authorized clients by the user.
	 * 
	 * @param profileId
	 * @return
	 */
	List<UserAuthorizedClient> getAllAuthroziedClient(String profileId);
	
	/**
	 * This will pull the client from the authorization list
	 * @param profileId
	 * @param clientId
	 * @return 
	 */
	int unAuthorizeAClient(String profileId, String clientId);
	
	/**
	 * This will authorize the client specified, will try to add to the list
	 * @param userAuthorizedClient
	 * @return
	 */
	int authorizeClient(UserAuthorizedClient userAuthorizedClient);

}

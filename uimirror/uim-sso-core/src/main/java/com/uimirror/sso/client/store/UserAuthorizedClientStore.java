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
package com.uimirror.sso.client.store;

import java.util.List;

import com.uimirror.sso.client.UserAuthorizedClient;

/**
 * A basic store for the User authorized clients.
 *  
 * @author Jay
 */
public interface UserAuthorizedClientStore {

	/**
	 * Find the client that has been authorized by the user
	 * @param profileId as first parameter
	 * @param clientId as second parameter
	 * @return a authorized client 
	 */
	UserAuthorizedClient findAuthrorizedClient(String profileId, String clientId);
	
	/**
	 * Find the clients that has been authorized by the user with given scope
	 * 
	 * @param profileId as first parameter
	 * @param clientId as second parameter
	 * @param scope as third parameter
	 * @return a authorized client 
	 */
	UserAuthorizedClient findAuthrorizedClient(String profileId, String clientId, String scope);
	
	/**
	 * Get all the authorized clients by the user.
	 * 
	 * @param profileId passed as parameter
	 * @return list of authorised client
	 */
	List<UserAuthorizedClient> getAllAuthroziedClient(String profileId);
	
	/**
	 * This will pull the client from the authorization list
	 * @param profileId is a parameter
	 * @param clientId is second parameter
	 * @return a integer value
	 */
	int unAuthorizeAClient(String profileId, String clientId);
	
	/**
	 * This will authorize the client specified, will try to add to the list
	 * @param userAuthorizedClient is parameter
	 * @return a integer value 
	 */
	int authorizeClient(UserAuthorizedClient userAuthorizedClient);

}

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
package com.uimirror.sso.token.store;

import java.util.List;
import java.util.Map;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.dao.RecordNotFoundException;

/**
 * The generated access token will be stored and retrieved.
 * Common DB interface which will be responsible for this.
 *  
 * @author Jay
 */
public interface AccessTokenStore {

	/**
	 * Stores the generated {@link AccessToken}
	 * @param token
	 * @throws DBException
	 */
	void store(AccessToken token) throws DBException;
	/**
	 * Gets the {@link AccessToken} by ID, i.e token
	 * @param token
	 * @return
	 * @throws DBException
	 */
	AccessToken get(String token) throws DBException;
	/**
	 * Retrieves a active access Token else {@link RecordNotFoundException}
	 * 
	 * @param token
	 * @return
	 * @throws DBException
	 */
	AccessToken getValid(String token) throws DBException;
	/**
	 * Retrieves the list of {@linkplain AccessToken} issued to the owner from
	 * various sources.
	 * returns <code>null</code> if no record found
	 * @param ownerId
	 * @return
	 * @throws DBException
	 */
	List<AccessToken> getByOwner(String ownerId) throws DBException;

	/**
	 * Retrieve any token available for the user, which he tries to register but due 
	 * to some reason it was unsuccessful.
	 * returns <code>null</code> if no record found
	 * @param profileId
	 * @return
	 * @throws DBException
	 */
	AccessToken getUserRegisteredWOTPToken(String profileId) throws DBException;
	
	/**
	 * Delete documents based on the search criteria
	 * @param query
	 * @return
	 * @throws DBException
	 */
	int deleteByQuery(Map<String, Object> query) throws DBException;
	
	/**
	 * Mark the token issued for the client as expired
	 * @param clientId
	 * @throws DBException
	 */
	int markAsExpired(String token)throws DBException;
	
}

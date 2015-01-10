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
	 * @param token is a parameter
	 * @throws DBException  if data not found
	 */
	void store(AccessToken token) throws DBException;
	/**
	 * Gets the {@link AccessToken} by ID, i.e token
	 * @param token using as parameter
	 * @return access token
	 * @throws DBException if record not  found
	 */
	AccessToken get(String token) throws DBException;
	/**
	 * Retrieves a active access Token else {@link RecordNotFoundException}
	 * 
	 * @param token used as parameter
	 * @return  access token
	 * @throws DBException if record not found
	 */
	AccessToken getValid(String token) throws DBException;
	/**
	 * Retrieves the list of {@linkplain AccessToken} issued to the owner from
	 * various sources.
	 * returns <code>null</code> if no record found
	 * @param ownerId is parameter
	 * @return list of access token object
	 * @throws DBException if record not found
	 */
	List<AccessToken> getByOwner(String ownerId) throws DBException;
	
	/**
	 * Delete documents based on the search criteria
	 * @param query a map using as parameter
	 * @return integer value
	 * @throws DBException if record not found
	 */
	int deleteByQuery(Map<String, Object> query) throws DBException;
	
	/**
	 * Mark the token issued for the client as expired
	 * @param token as parameter 
	 * @throws DBException if record not found
	 * @return integer if record found
	 */
	int markAsExpired(String token)throws DBException;
	
}

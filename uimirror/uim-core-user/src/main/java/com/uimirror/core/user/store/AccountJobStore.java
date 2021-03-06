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
package com.uimirror.core.user.store;

import java.util.Map;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.exceptions.db.DBException;
import com.uimirror.core.exceptions.db.RecordNotFoundException;

/**
 * The generated access token will be stored and retrieved.
 * Common DB interface which will be responsible for this.
 *  
 * @author Jay
 */
public interface AccountJobStore {

	/**
	 * Stores the generated {@link AccessToken}
	 * @param token as parameter
	 * @throws DBException if record not found
	 */
	void store(AccessToken token) throws DBException;
	/**
	 * Gets the {@link AccessToken} by ID, i.e token
	 * @param token as parameter
	 * @return access token
	 * @throws DBException if record not found
	 */
	AccessToken get(String token) throws DBException;
	/**
	 * Retrieves a active access Token else {@link RecordNotFoundException}
	 * 
	 * @param token as parameter 
	 * @return access token
	 * @throws DBException if record not found
	 */
	AccessToken getValid(String token) throws DBException;

	/**
	 * Retrieve any token available for the user, which he tries to register but due 
	 * to some reason it was unsuccessful.
	 * returns <code>null</code> if no record found
	 * @param profileId as parameter
	 * @return access token
	 * @throws DBException if record not found
	 */
	AccessToken getUserRegisteredWOTPToken(String profileId) throws DBException;
	
	/**
	 * Delete documents based on the search criteria
	 * @param query   a map as parameter
	 * @return integer value
	 * @throws DBException if record not found
	 */
	int deleteByQuery(Map<String, Object> query) throws DBException;
	
}

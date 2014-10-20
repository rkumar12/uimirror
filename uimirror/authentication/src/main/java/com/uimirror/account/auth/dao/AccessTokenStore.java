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
package com.uimirror.account.auth.dao;

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
	 * Retrieves the list of active {@linkplain AccessToken} issued to the owner
	 * returns <code>null</code> if no record found 
	 * @param ownerId
	 * @return
	 * @throws DBException
	 */
	List<AccessToken> getActivesByOwner(String ownerId) throws DBException;
	
	/**
	 * Retrieves the list of {@linkplain AccessToken} issued to the client.
	 * returns <code>null</code> if no record found
	 * 
	 * @param clientId
	 * @return
	 * @throws DBException
	 */
	List<AccessToken> getByClient(String clientId) throws DBException;
	
	/**
	 * Retrieves the list of active {@linkplain AccessToken} issued to a client.
	 * returns <code>null</code> if no record found
	 * 
	 * @param clientId
	 * @return
	 * @throws DBException
	 */
	List<AccessToken> getActivesByClient(String clientId) throws DBException;
	
	/**
	 * Update the documents on the basics of the owner
	 * @param ownerId
	 * @param update
	 * @return number of record got updated
	 * @throws DBException
	 */
	int updateByOwner(String ownerId, Map<String, Object> update) throws DBException;
	
	/**
	 * Update the documents on the basics of the client.
	 * @param clientId
	 * @param update
	 * @return number of record got updated
	 * @throws DBException
	 */
	int updateByClient(String clientId, Map<String, Object> update) throws DBException;
	
	/**
	 * Delete the document based on the owner ID
	 * @param ownerId
	 * @return number of document got deleted
	 * @throws DBException
	 */
	int deleteByOwner(String ownerId) throws DBException;
	
	/**
	 * Delete all the expired token issued for that owner
	 * @param ownerId
	 * @return number of record got deleted
	 * @throws DBException
	 */
	int deleteAllExpiredByOwner(String ownerId) throws DBException;
	
	/**
	 * Delete all the token issued to the client specified.
	 * @param clientId
	 * @return
	 * @throws DBException
	 */
	int deleteByClient(String clientId) throws DBException;
	
	/**
	 * Delete all the expired token issued to the client specified.
	 * @param clientId
	 * @return
	 * @throws DBException
	 */
	int deleteAllExpiredByClient(String clientId) throws DBException;
	
	/**
	 * Delete documents based on the search criteria
	 * @param query
	 * @return
	 * @throws DBException
	 */
	int deleteByQuery(Map<String, Object> query) throws DBException;
	
	/**
	 * Mark all the token issued to the user as expired
	 * @param ownerId
	 * @throws DBException
	 */
	void markAllExpired(String ownerId) throws DBException;
	
	/**
	 * Mark all the token issued for the client as expired
	 * @param clientId
	 * @throws DBException
	 */
	void markAllExpiredByClient(String clientId)throws DBException;
	
	/**
	 * Mark the token issued for the client as expired
	 * @param clientId
	 * @throws DBException
	 */
	int markAsExpired(String token)throws DBException;
	
}

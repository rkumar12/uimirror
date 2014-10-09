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
package com.uimirror.auth.dao;

import java.util.List;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.mongo.feature.MongoDocumentSerializer;

/**
 * The generated access token will be stored and retrieved.
 * Common DB interface which will be responsible for this.
 *  
 * @author Jay
 */
public interface AccessTokenStore {

	/**
	 * Retrieves the list of {@linkplain AccessToken} issued to the owner from
	 * various sources.
	 * returns <code>null</code> if no record found
	 * @param ownerId
	 * @return
	 * @throws DBException
	 */
	List<AccessToken> getByOwner(Object ownerId) throws DBException;

	/**
	 * Retrieves the list of active {@linkplain AccessToken} issued to the owner
	 * returns <code>null</code> if no record found 
	 * @param ownerId
	 * @return
	 * @throws DBException
	 */
	List<AccessToken> getActivesByOwner(Object ownerId) throws DBException;
	
	/**
	 * Retrieves the list of {@linkplain AccessToken} issued to the client.
	 * returns <code>null</code> if no record found
	 * 
	 * @param clientId
	 * @return
	 * @throws DBException
	 */
	List<AccessToken> getByClient(Object clientId) throws DBException;
	
	/**
	 * Retrieves the list of active {@linkplain AccessToken} issued to a client.
	 * returns <code>null</code> if no record found
	 * 
	 * @param clientId
	 * @return
	 * @throws DBException
	 */
	List<AccessToken> getActivesByClient(Object clientId) throws DBException;
	
	/**
	 * Update the documents on the basics of the owner
	 * @param ownerId
	 * @param docToUpdate
	 * @return number of record got updated
	 * @throws DBException
	 */
	int updateByOwner(Object ownerId, MongoDocumentSerializer docToUpdate) throws DBException;
	
	/**
	 * Update the documents on the basics of the client.
	 * @param clientId
	 * @param docToUpdate
	 * @return number of record got updated
	 * @throws DBException
	 */
	int updateByClient(Object clientId, MongoDocumentSerializer docToUpdate) throws DBException;
	
	/**
	 * Delete the document based on the owner ID
	 * @param ownerId
	 * @return number of document got deleted
	 * @throws DBException
	 */
	int deleteByOwner(Object ownerId) throws DBException;
	
	/**
	 * Delete all the expired token issued for that owner
	 * @param ownerId
	 * @return number of record got deleted
	 * @throws DBException
	 */
	int deleteAllExpiredByOwner(Object ownerId) throws DBException;
	
	/**
	 * Delete all the token issued to the client specified.
	 * @param clientId
	 * @return
	 * @throws DBException
	 */
	int deleteByClient(Object clientId) throws DBException;
	
	/**
	 * Delete all the expired token issued to the client specified.
	 * @param clientId
	 * @return
	 * @throws DBException
	 */
	int deleteAllExpiredByClient(Object clientId) throws DBException;
	
	/**
	 * Mark all the token issued to the user as expired
	 * @param ownerId
	 * @throws DBException
	 */
	void markAllExpired(Object ownerId) throws DBException;
	
	/**
	 * Mark all the token issued for the client as expired
	 * @param clientId
	 * @throws DBException
	 */
	void markAllExpiredByClient(Object clientId)throws DBException;
	
}

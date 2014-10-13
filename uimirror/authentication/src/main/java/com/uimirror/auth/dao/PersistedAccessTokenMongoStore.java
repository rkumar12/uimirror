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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mongodb.DBCollection;
import com.uimirror.auth.bean.DefaultAccessToken;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.mongo.feature.MongoDocumentSerializer;

/**
 * Retrieves the credential store for the user.
 * @author Jay
 */
//TODO implement this today
@Repository
public class PersistedAccessTokenMongoStore extends AbstractMongoStore<DefaultAccessToken> implements AccessTokenStore {
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	@Autowired
	public PersistedAccessTokenMongoStore(@Qualifier("tokenOuathCol") DBCollection collection){
		super(collection, DefaultAccessToken.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#getByOwner(java.lang.Object)
	 */
	@Override
	public List<AccessToken> getByOwner(Object ownerId) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#getActivesByOwner(java.lang.Object)
	 */
	@Override
	public List<AccessToken> getActivesByOwner(Object ownerId)
			throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#getByClient(java.lang.Object)
	 */
	@Override
	public List<AccessToken> getByClient(Object clientId) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#getActivesByClient(java.lang.Object)
	 */
	@Override
	public List<AccessToken> getActivesByClient(Object clientId)
			throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#updateByOwner(java.lang.Object, com.uimirror.core.mongo.feature.MongoDocumentSerializer)
	 */
	@Override
	public int updateByOwner(Object ownerId, MongoDocumentSerializer docToUpdate)
			throws DBException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#updateByClient(java.lang.Object, com.uimirror.core.mongo.feature.MongoDocumentSerializer)
	 */
	@Override
	public int updateByClient(Object clientId,
			MongoDocumentSerializer docToUpdate) throws DBException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#deleteByOwner(java.lang.Object)
	 */
	@Override
	public int deleteByOwner(Object ownerId) throws DBException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#deleteAllExpiredByOwner(java.lang.Object)
	 */
	@Override
	public int deleteAllExpiredByOwner(Object ownerId) throws DBException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#deleteByClient(java.lang.Object)
	 */
	@Override
	public int deleteByClient(Object clientId) throws DBException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#deleteAllExpiredByClient(java.lang.Object)
	 */
	@Override
	public int deleteAllExpiredByClient(Object clientId) throws DBException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#markAllExpired(java.lang.Object)
	 */
	@Override
	public void markAllExpired(Object ownerId) throws DBException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.AccessTokenStore#markAllExpiredByClient(java.lang.Object)
	 */
	@Override
	public void markAllExpiredByClient(Object clientId) throws DBException {
		// TODO Auto-generated method stub
		
	}

}

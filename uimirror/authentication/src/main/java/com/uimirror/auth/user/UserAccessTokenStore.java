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
package com.uimirror.auth.user;

import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.uimirror.auth.dao.AbstractAccessTokenStore;
import com.uimirror.auth.dao.AccessTokenStore;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.dao.DBException;

/**
 * Basic Implementation for the {@linkplain AccessTokenStore}
 * @author Jay
 */
public class UserAccessTokenStore extends AbstractAccessTokenStore{

	/**
	 * Assign/ Create collection from the given {@link Mongo}
	 * @param mongo
	 * @param dbName
	 * @param collectionName
	 */
	public UserAccessTokenStore(Mongo mongo, String dbName, String collectionName){
		super(mongo, dbName, collectionName);
	}
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	public UserAccessTokenStore(DBCollection collection){
		super(collection);
	}
	
	/**
	 * @param db
	 * @param collectionName
	 */
	public UserAccessTokenStore(DB db, String collectionName) {
		super(db, collectionName);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#getByOwner(java.lang.Object)
	 */
	@Override
	public List<AccessToken> getByOwner(Object ownerId) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#getActivesByOwner(java.lang.Object)
	 */
	@Override
	public List<AccessToken> getActivesByOwner(Object ownerId) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#getByClient(java.lang.Object)
	 */
	@Override
	public List<AccessToken> getByClient(Object clientId) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#getActivesByClient(java.lang.Object)
	 */
	@Override
	public List<AccessToken> getActivesByClient(Object clientId)
			throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

}

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
package com.uimirror.auth.user.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.uimirror.core.auth.dao.CredentialsStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.dao.MongoInitializer;
import com.uimirror.core.dao.MongoSerializer;

/**
 * Retrieves the credential store for the user.
 * 
 * @author Jay
 */
//@Repository
public class UserCredentialStore extends MongoInitializer implements CredentialsStore, MongoSerializer {
	
	private @Value("${auth.db.name}")String dbName;
	private @Value("${auth.usr.col.name}")String collectionName;
	
	/**
	 * Assign/ Create collection from the given {@link DB}
	 * @param db
	 * @param collectionName
	 */
	public UserCredentialStore(DB db, String collectionName) {
		super(db, collectionName);
	}

	/**
	 * Assign/ Create collection from the given {@link Mongo}
	 * @param mongo
	 * @param dbName
	 * @param collectionName
	 */
	public UserCredentialStore(Mongo mongo, String dbName, String collectionName){
		super(mongo, dbName, collectionName);
	}
	
	/**
	 * 
	 * @param collection
	 */
	public UserCredentialStore(DBCollection collection){
		super(collection);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.MongoSerializer#toMap(java.lang.Object)
	 */
	@Override
	public Map<String, Object> toMap(Object src) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.CredentialsStore#getCredentials(java.lang.Object)
	 */
	@Override
	public Object getCredentials(Object identifier) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

}

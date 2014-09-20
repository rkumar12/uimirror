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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.uimirror.auth.DBFileds;
import com.uimirror.core.auth.dao.CredentialsStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.dao.MongoSerializer;
import com.uimirror.core.dao.RecordNotFoundException;

/**
 * Retrieves the credential store for the user.
 * 
 * @author Jay
 */
@Repository
public class UserCredentialStore extends MongoSerializer<Object> implements CredentialsStore {
	
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
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	@Autowired
	public UserCredentialStore(@Qualifier("usrAuthCol") DBCollection collection){
		super(collection);
	}
	

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.CredentialsStore#getCredentials(java.lang.Object)
	 */
	@Override
	public Object getCredentials(Object identifier) throws DBException {
		DBObject res = null;
		try{
			res = getCollection().findOne(buildUserCredentialSerachQuery(identifier));
			if(res == null){
				throw new RecordNotFoundException();
			}
			
		}catch(MongoException e){
			
		}
		//Make sure no other thing setting as null to response
		return res.toMap();
	}
	
	/**
	 * Builds the query to search from the userid array of user credential document
	 * @param identifier
	 * @return
	 */
	private DBObject buildUserCredentialSerachQuery(Object identifier){
		return new BasicDBObject(DBFileds.UC_USER_ID, identifier.toString());
	}

}

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.uimirror.auth.client.Client;
import com.uimirror.core.dao.MongoExceptionMapper;
import com.uimirror.core.dao.MongoSerializer;
import com.uimirror.core.extra.MapException;

/**
 * This will be the client store DB implementations
 * 
 * @author Jay
 */
@Repository
public class PersistedClientStore extends MongoSerializer implements ClientStore{

	private @Value("${client.db.name}")String dbName;
	private @Value("${client.col.name}")String collectionName;

	/**
	 * Assign/ Create collection from the given {@link DB}
	 * @param db
	 * @param collectionName
	 */
	public PersistedClientStore(DB db, String collectionName) {
		super(db, collectionName);
	}

	/**
	 * Assign/ Create collection from the given {@link Mongo}
	 * @param mongo
	 * @param dbName
	 * @param collectionName
	 */
	public PersistedClientStore(Mongo mongo, String dbName, String collectionName){
		super(mongo, dbName, collectionName);
	}
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	@Autowired
	public PersistedClientStore(@Qualifier("clientBasicInfoCol") DBCollection collection){
		super(collection);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.ClientStore#findClientByApiKey(java.lang.String)
	 */
	@Override
	@MapException(use=MongoExceptionMapper.NAME)
	public Client findClientByApiKey(String apiKey) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.ClientStore#findClientById(java.lang.String)
	 */
	@Override
	public Client findClientById(String clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.ClientStore#findActieveClientByApiKey(java.lang.String)
	 */
	@Override
	public Client findActieveClientByApiKey(String apiKey) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.dao.ClientStore#findActieveClientById(java.lang.String)
	 */
	@Override
	public Client findActieveClientById(String clientId) {
		// TODO Auto-generated method stub
		return null;
	}
}

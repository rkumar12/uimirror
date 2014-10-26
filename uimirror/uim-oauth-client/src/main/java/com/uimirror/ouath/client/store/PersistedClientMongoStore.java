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
package com.uimirror.ouath.client.store;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.ClientDBFields;

/**
 * This will be the client store In Mongo DB implementations
 * 
 * @author Jay
 */
public class PersistedClientMongoStore extends AbstractMongoStore<Client> implements ClientStore{
	
	private final static String CLIENT_BASIC_SEQ = "cbs";
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	@Autowired
	public PersistedClientMongoStore(@Qualifier("clientBasicInfoCol") DBCollection collection, @Qualifier("clientBasicInfoSeqCol") DBCollection seqCollection){
		super(collection, seqCollection, CLIENT_BASIC_SEQ, Client.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.ClientStore#findClientByApiKey(java.lang.String)
	 */
	@Override
	public Client findClientByApiKey(String apiKey) throws DBException{
		return queryFirstRecord(getApiKeyQuery(apiKey));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.ClientStore#findClientById(java.lang.String)
	 */
	@Override
	public Client findClientById(String clientId) throws DBException{
		return getById(clientId);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.ClientStore#findClientById(java.lang.String, java.util.Map)
	 */
	@Override
	public Client findClientById(String clientId, String ... fields) throws DBException {
		Map<String, Object> projections = new LinkedHashMap<String, Object>();
		for(String field: fields){
			projections.put(field, 1);
		}
		return getById(clientId, projections);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.ClientStore#findActieveClientByApiKey(java.lang.String)
	 */
	@Override
	public Client findActieveClientByApiKey(String apiKey) throws DBException{
		Map<String, Object> apiKeyQuery = getApiKeyQuery(apiKey);
		apiKeyQuery.put(ClientDBFields.STATUS, AccountStatus.ACTIVE.getStatus());
		return queryFirstRecord(apiKeyQuery);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.ClientStore#findActieveClientById(java.lang.String)
	 */
	@Override
	public Client findActieveClientById(String clientId) throws DBException{
		Map<String, Object> idQuery = getIdMap(clientId);
		idQuery.put(ClientDBFields.STATUS, AccountStatus.ACTIVE.getStatus());
		return queryFirstRecord(idQuery);
	}
	
	/**
	 * Creates a query for the API Key
	 * @param apiKey
	 * @return
	 */
	private Map<String, Object> getApiKeyQuery(String apiKey){
		Assert.hasText(apiKey, "Api key Query Parameter can't be empty");
		Map<String, Object> query = new LinkedHashMap<String, Object>(3);
		query.put(ClientDBFields.API_KEY, apiKey);
		return query;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		DBObject obj = new BasicDBObject(ClientDBFields.APP_URL, 1);
		getCollection().createIndex(obj);;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.client.dao.ClientStore#findClientByAppUrl(java.lang.String)
	 */
	@Override
	public Client findClientByAppUrl(String url) throws DBException {
		Map<String, Object> query = new LinkedHashMap<String, Object>(3);
		query.put(ClientDBFields.APP_URL, url);
		Map<String, Object> fields = new LinkedHashMap<String, Object>(3);
		fields.put(ClientDBFields.ID, 1);
		return queryFirstRecord(query, fields);
	}
	
}
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
package com.uimirror.account.client.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.DBCollection;
import com.uimirror.account.client.ClientDBFields;
import com.uimirror.account.client.bean.Client;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.user.AccountStatus;

/**
 * This will be the client store In Mongo DB implementations
 * 
 * @author Jay
 */
@Repository
public class PersistedClientMongoStore extends AbstractMongoStore<Client> implements ClientStore{
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	@Autowired
	public PersistedClientMongoStore(@Qualifier("clientBasicInfoCol") DBCollection collection){
		super(collection, Client.class);
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
		// TODO Auto-generated method stub
		
	}
	
}

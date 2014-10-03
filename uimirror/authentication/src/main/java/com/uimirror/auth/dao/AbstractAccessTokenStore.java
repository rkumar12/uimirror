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

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.uimirror.core.BasicMongoOperators;
import com.uimirror.core.auth.AccessTokenFields;
import com.uimirror.core.auth.dao.AccessTokenStore;
import com.uimirror.core.dao.AbstractStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.mongo.feature.MongoDocumentSerializer;
import com.uimirror.core.util.DateTimeUtil;

/**
 * Basic and essential implementation for the {@linkplain AccessTokenStore}
 * 
 * @author Jay
 */
public abstract class AbstractAccessTokenStore extends AbstractStore implements AccessTokenStore{

	/**
	 * Assign/ Create collection from the given {@link Mongo}
	 * @param mongo
	 * @param dbName
	 * @param collectionName
	 */
	public AbstractAccessTokenStore(Mongo mongo, String dbName, String collectionName){
		super(mongo, dbName, collectionName);
	}
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	public AbstractAccessTokenStore(DBCollection collection){
		super(collection);
	}
	
	/**
	 * @param db
	 * @param collectionName
	 */
	public AbstractAccessTokenStore(DB db, String collectionName) {
		super(db, collectionName);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#updateByOwner(java.lang.Object, com.uimirror.core.mongo.feature.MongoDocumentSerializer)
	 */
	@Override
	public int updateByOwner(Object ownerId, MongoDocumentSerializer docToUpdate) throws DBException {
		return super.setSingleByQuery(getOwnerQueryMap(ownerId), docToUpdate);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#updateByClient(java.lang.Object, com.uimirror.core.mongo.feature.MongoDocumentSerializer)
	 */
	@Override
	public int updateByClient(Object clientId, MongoDocumentSerializer docToUpdate) throws DBException {
		return super.setSingleByQuery(getClientQueryMap(clientId), docToUpdate);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#deleteByOwner(java.lang.Object)
	 */
	@Override
	public int deleteByOwner(Object ownerId) throws DBException {
		return super.deleteByQuery(getOwnerQueryMap(ownerId));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#deleteAllExpiredByOwner(java.lang.Object)
	 */
	@Override
	public int deleteAllExpiredByOwner(Object ownerId) throws DBException {
		return super.deleteByQuery(getTokenExpirByOwnerQuery(ownerId));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#deleteByClient(java.lang.Object)
	 */
	@Override
	public int deleteByClient(Object clientId) throws DBException {
		return super.deleteByQuery(getClientQueryMap(clientId));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#deleteAllExpiredByClient(java.lang.Object)
	 */
	@Override
	public int deleteAllExpiredByClient(Object clientId) throws DBException {
		return super.deleteByQuery(getTokenExpirByClientQuery(clientId));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#markAllExpired(java.lang.Object)
	 */
	@Override
	public void markAllExpired(Object ownerId) throws DBException {
		super.updateByQuery(getTokenExpirByOwnerQuery(ownerId), getExpirUpdateQuery());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.AccessTokenStore#markAllExpiredByClient(java.lang.Object)
	 */
	@Override
	public void markAllExpiredByClient(Object clientId) throws DBException {
		super.updateByQuery(getTokenExpirByClientQuery(clientId), getExpirUpdateQuery());
	}

	/**
	 * Builds the query that will be used to find documents based on the client id
	 * and token expires from now
	 * @param clientId
	 * @return
	 */
	private Map<String, Object> getTokenExpirByClientQuery(Object clientId){
		Map<String, Object> query = getClientQueryMap(clientId);
		query.put(AccessTokenFields.AUTH_TKN_EXPIRES, getLessThanEpochQuery());
		return query;
	}
	
	/**
	 * Builds the query that will be used to find documents based on the owner id
	 * and token expires from now
	 * @param ownerId
	 * @return
	 */
	private Map<String, Object> getTokenExpirByOwnerQuery(Object ownerId){
		Map<String, Object> query = getOwnerQueryMap(ownerId);
		query.put(AccessTokenFields.AUTH_TKN_EXPIRES, getLessThanEpochQuery());
		return query;
	}

	/**
	 * Builds the EPOCH time less than query.
	 * @return
	 */
	private Map<String, Object> getLessThanEpochQuery(){
		Map<String, Object> lessThanQuery = new LinkedHashMap<String, Object>(3);
		lessThanQuery.put(BasicMongoOperators.LESSTHANEQUEAL, DateTimeUtil.getCurrentSystemUTCEpoch());
		return lessThanQuery;
	}
	
	private Map<String, Object> getExpirUpdateQuery(){
		Map<String, Object> setFields = new LinkedHashMap<String, Object>(3);
		setFields.put(AccessTokenFields.AUTH_TKN_EXPIRES, ZonedDateTime.now(Clock.systemUTC()));
		Map<String, Object> setQuery = new LinkedHashMap<String, Object>(3);
		setQuery.put(BasicMongoOperators.SET, setFields);
		return setQuery;
	}
	
	/**
	 * Gets the Map, that checks for {"owner":"ccc"}
	 * @param id
	 * @return
	 */
	protected Map<String, Object> getOwnerQueryMap(Object id){
		Map<String, Object> query = new LinkedHashMap<String, Object>(3);
		query.put(AccessTokenFields.AUTH_TKN_OWNER, id);
		return query;
	}
	
	/**
	 * Gets the Map, that checks for {"client":"ccc"}
	 * @param id
	 * @return
	 */
	protected Map<String, Object> getClientQueryMap(Object id){
		Map<String, Object> query = new LinkedHashMap<String, Object>(3);
		query.put(AccessTokenFields.AUTH_TKN_CLIENT, id);
		return query;
	}

}

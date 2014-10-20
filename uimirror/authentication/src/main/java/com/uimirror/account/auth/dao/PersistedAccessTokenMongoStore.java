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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mongodb.DBCollection;
import com.uimirror.account.auth.AccessTokenFields;
import com.uimirror.account.auth.DefaultAccessToken;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.mongo.BasicMongoOperators;
import com.uimirror.core.util.DateTimeUtil;

/**
 * A Basic MONGO store for the access token  
 * @author Jay
 */
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
	 * @see com.uimirror.account.auth.dao.AccessTokenStore#store(com.uimirror.core.auth.AccessToken)
	 */
	@Override
	public void store(AccessToken token) throws DBException {
		store(token);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.AccessTokenStore#get(java.lang.String)
	 */
	@Override
	public AccessToken get(String token) throws DBException {
		return getById(token);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.AccessTokenStore#getValid(java.lang.String)
	 */
	@Override
	public AccessToken getValid(String token) throws DBException {
		return queryFirstRecord(buildValidTokenQuery(token));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.AccessTokenStore#getByOwner(java.lang.String)
	 */
	@Override
	public List<AccessToken> getByOwner(String ownerId) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Builds the query for retrieval of the token by ID and expires
	 * @param token
	 * @return
	 */
	private Map<String, Object> buildValidTokenQuery(String token){
		Map<String, Object> query = getIdMap(token);
		query.put(AccessTokenFields.AUTH_TKN_EXPIRES, buildTimeValidQuery());
		return query;
	}
	
	/**
	 * Builds the Less Than Equal query with current UTC EPOCH time
	 * @return
	 */
	private Map<String, Object> buildTimeValidQuery(){
		Map<String, Object> query = new LinkedHashMap<String, Object>(3);
		query.put(BasicMongoOperators.LESSTHANEQUEAL, DateTimeUtil.getCurrentUTCTime());
		return query;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.AccessTokenStore#markAsExpired(java.lang.String)
	 */
	@Override
	public int markAsExpired(String token) throws DBException {
		Map<String, Object> setQuery = new LinkedHashMap<String, Object>(3);
		setQuery.put(BasicMongoOperators.SET, getExpiryNowMap());
		return updateById(token, setQuery);
	}
	
	/**
	 * Creates the map for the token expire fields to mark as now
	 * @return
	 */
	private Map<String, Object> getExpiryNowMap(){
		Map<String, Object> query = new LinkedHashMap<String, Object>(3);
		query.put(AccessTokenFields.AUTH_TKN_EXPIRES, DateTimeUtil.getCurrentSystemUTCEpoch());
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
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
package com.uimirror.user.store;

import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_EXPIRES;
import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_INSTRUCTIONS;
import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_OWNER;
import static com.uimirror.core.mongo.BasicMongoOperators.LESSTHANEQUEAL;

import java.util.Map;
import java.util.WeakHashMap;

import com.mongodb.DBCollection;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.dao.MongoStoreHelper;
import com.uimirror.core.util.DateTimeUtil;

/**
 * A Basic MONGO store for the newly created accounts  
 * @author Jay
 */
public class PersistedAccountTokenMongoStore extends AbstractMongoStore<DefaultAccessToken> implements AccountTokenStore {
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	public PersistedAccountTokenMongoStore(DBCollection collection){
		super(collection, DefaultAccessToken.class);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.AccessTokenStore#store(com.uimirror.core.auth.AccessToken)
	 */
	@Override
	public void store(AccessToken token) throws DBException {
		DefaultAccessToken  tkn = (DefaultAccessToken) token;
		store(tkn);
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
	
	/**
	 * Builds the query for retrieval of the token by ID and expires
	 * @param token
	 * @return
	 */
	private Map<String, Object> buildValidTokenQuery(String token){
		Map<String, Object> query = MongoStoreHelper.getIdMap(token);
		query.put(AUTH_TKN_EXPIRES, buildTimeValidQuery());
		return query;
	}
	
	/**
	 * Builds the Less Than Equal query with current UTC EPOCH time
	 * @return
	 */
	private Map<String, Object> buildTimeValidQuery(){
		Map<String, Object> query = new WeakHashMap<String, Object>(3);
		query.put(LESSTHANEQUEAL, DateTimeUtil.getCurrentSystemUTCEpoch());
		return query;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * gets the web registered verify token field
	 * @param profileId
	 * @return
	 * @throws DBException
	 */
	@Override
	public AccessToken getUserRegisteredWOTPToken(String profileId)throws DBException {
		Map<String, Object> query = new WeakHashMap<String, Object>(4);
		query.put(AUTH_TKN_OWNER, profileId);
		query.put(AUTH_TKN_INSTRUCTIONS+"."+AuthConstants.WEB_VERIFY_TOKEN, MongoStoreHelper.getExistQuery(Boolean.TRUE));
		return queryFirstRecord(query);
	}
	
}

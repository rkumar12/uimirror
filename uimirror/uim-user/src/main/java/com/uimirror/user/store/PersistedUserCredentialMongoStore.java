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

import static com.uimirror.core.mongo.BasicMongoOperators.SET;
import static com.uimirror.core.user.UserAuthDBFields.ACCOUNT_STATE;
import static com.uimirror.core.user.UserAuthDBFields.USER_ID;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.Credentials;

/**
 * Retrieves the credential store for the user.
 * @author Jay
 */
public class PersistedUserCredentialMongoStore extends AbstractMongoStore<Credentials> implements UserCredentialsStore {
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection as parameter
	 */
	public PersistedUserCredentialMongoStore(DBCollection collection){
		super(collection, Credentials.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.CredentialsStore#getCredentials(java.lang.Object)
	 */
	@Override
	public Credentials getCredentialsByUserName(String identifier) throws DBException {
		return queryFirstRecord(getUserIdQuery(identifier));
	}
	
	/**
	 * Builds the query to search from the userid array of user credential document
	 * @param identifier
	 * @return
	 */
	private Map<String, Object> getUserIdQuery(String identifier){
		Assert.hasText(identifier, "UserID Query Parameter can't be empty");
		Map<String, Object> query = new LinkedHashMap<String, Object>(3);
		query.put(USER_ID, identifier);
		return query;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.UserCredentialsStore#getCredentialsByProfileId(java.lang.String)
	 */
	@Override
	public Credentials getCredentialsByProfileId(String identifier) throws DBException {
		return getById(identifier);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.UserCredentialsStore#enableAccount(java.lang.String)
	 */
	@Override
	public void enableAccount(String profileId) throws DBException {
		Map<String, Object> set = new WeakHashMap<String, Object>(3);
		set.put(SET, getAccountStateMap(AccountState.ENABLED));
		updateById(profileId, set);
	}
	
	/**Create a map for the account state 
	 * @param state
	 * @return
	 */
	private Map<String, Object> getAccountStateMap(AccountState state){
		Map<String, Object> field = new LinkedHashMap<String, Object>(3);
		field.put(ACCOUNT_STATE, state.getState());
		return field;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		//TODO finalize during production what needs to be actual value
		DBObject obj = new BasicDBObject(USER_ID, 1);
		getCollection().createIndex(obj);
	}

}

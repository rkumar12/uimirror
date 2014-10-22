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
package com.uimirror.account.user.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.account.user.bean.DefaultUserCredentials;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.mongo.BasicMongoOperators;
import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.UserAuthDBFields;
import com.uimirror.core.user.UserCredentials;
import com.uimirror.core.user.UserDBFields;

/**
 * Retrieves the credential store for the user.
 * @author Jay
 */
@Repository
public class PersistedUserCredentialMongoStore extends AbstractMongoStore<DefaultUserCredentials> implements UserCredentialsStore {
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	@Autowired
	public PersistedUserCredentialMongoStore(@Qualifier("usrAuthCol") DBCollection collection){
		super(collection, DefaultUserCredentials.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.dao.CredentialsStore#getCredentials(java.lang.Object)
	 */
	@Override
	public UserCredentials getCredentialsByUserName(String identifier) throws DBException {
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
		query.put(UserAuthDBFields.USER_ID, identifier);
		return query;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.UserCredentialsStore#getCredentialsByProfileId(java.lang.String)
	 */
	@Override
	public UserCredentials getCredentialsByProfileId(String identifier) throws DBException {
		return getById(identifier);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.dao.UserCredentialsStore#enableAccount(java.lang.String)
	 */
	@Override
	public int enableAccount(String profileId) throws DBException {
		Map<String, Object> set = new LinkedHashMap<String, Object>(3);
		set.put(BasicMongoOperators.SET, getAccountStateMap(AccountState.ENABLED));
		return updateById(profileId, set);
	}
	
	/**Create a map for the account state 
	 * @param state
	 * @return
	 */
	private Map<String, Object> getAccountStateMap(AccountState state){
		Map<String, Object> field = new LinkedHashMap<String, Object>(3);
		field.put(UserAuthDBFields.ACCOUNT_STATE, state.getState());
		return field;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		//TODO finalize during production what needs to be actual value
		DBObject obj = new BasicDBObject(UserDBFields.USER_ID, 1);
		getCollection().createIndex(obj);
	}

}

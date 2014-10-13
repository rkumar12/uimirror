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

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.DBCollection;
import com.uimirror.auth.dao.UserCredentialsStore;
import com.uimirror.auth.user.DefaultUserCredentials;
import com.uimirror.auth.user.UserAuthDBFields;
import com.uimirror.auth.user.UserCredentials;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.dao.DBException;

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
	 * @see com.uimirror.auth.dao.UserCredentialsStore#getCredentialsByProfileId(java.lang.String)
	 */
	@Override
	public UserCredentials getCredentialsByProfileId(String identifier) throws DBException {
		return getById(identifier);
	}

}

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.user.DefaultUserAccountLogs;
import com.uimirror.core.user.UserDBFields;

/**
 * Retrieves the credential store for the user.
 * @author Jay
 */
@Repository
public class PersistedUserAccountLogMongoStore extends AbstractMongoStore<DefaultUserAccountLogs> implements UserAccountLogStore {
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	@Autowired
	public PersistedUserAccountLogMongoStore(@Qualifier("userLogsCol") DBCollection collection){
		super(collection, DefaultUserAccountLogs.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserAccountLogStore#getLogsByProfileId(java.lang.String)
	 */
	@Override
	public DefaultUserAccountLogs getLogsByProfileId(String profileId) {
		return getById(profileId);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserAccountLogStore#deleteByprofileId(java.lang.String)
	 */
	@Override
	public void deleteByprofileId(String profileId) {
		deleteById(profileId);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		//TODO finalize during production what needs to be actual value
		DBObject obj = new BasicDBObject(UserDBFields.CREATED_ON, 1);
		getCollection().createIndex(obj);
	}

}

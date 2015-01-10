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
import static com.uimirror.core.user.UserAccountLogDBFields.CREATED_ON;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.user.AccountLogs;

/**
 * Retrieves the credential store for the user.
 * @author Jay
 */
public class PersistedUserAccountLogMongoStore extends AbstractMongoStore<AccountLogs> implements UserAccountLogStore {
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection as parameter
	 */
	public PersistedUserAccountLogMongoStore(DBCollection collection){
		super(collection, AccountLogs.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserAccountLogStore#getLogsByProfileId(java.lang.String)
	 */
	@Override
	public AccountLogs getLogsByProfileId(String profileId) {
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
		DBObject obj = new BasicDBObject(CREATED_ON, 1);
		getCollection().createIndex(obj);
	}

}

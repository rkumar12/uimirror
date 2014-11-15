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

import static com.uimirror.core.user.UserDBFields.ACCOUNT_STATE;
import static com.uimirror.core.user.UserDBFields.ACCOUNT_STATUS;
import static com.uimirror.core.user.UserDBFields.DATE_OF_BIRTH;
import static com.uimirror.core.user.UserDBFields.FIRST_NAME;
import static com.uimirror.core.user.UserDBFields.LAST_NAME;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.user.BasicDetails;

/**
 * Retrieves the credential store for the user.
 * @author Jay
 */
public class PersistedUserBasicDetailsMongoStore extends AbstractMongoStore<BasicDetails> implements UserBasicDetailsStore {
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	public PersistedUserBasicDetailsMongoStore(DBCollection collection){
		super(collection, BasicDetails.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserBasicInfoStore#getUserInfoByProfileId(java.lang.String)
	 */
	@Override
	public BasicDetails getUserInfoByProfileId(String profileId) {
		return getById(profileId);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserBasicInfoStore#deleteByprofileId(java.lang.String)
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
		ensureIndexOnPresentLocation();
		ensureIndexOnPermanetLocation();
		ensureIndexOnDateOfBirth();
	}
	
	/**
	 * Creates index on first name and last name with status and state
	 */
	private void ensureIndexOnPresentLocation(){
		DBObject first_last_name = new BasicDBObject(FIRST_NAME, 1);
		first_last_name.put(LAST_NAME, 1);
		first_last_name.put(ACCOUNT_STATUS, 1);
		first_last_name.put(ACCOUNT_STATE, 1);
		getCollection().createIndex(first_last_name);
	}
	/**
	 * Creates index on first name with status and state
	 */
	private void ensureIndexOnPermanetLocation(){
		DBObject first_name = new BasicDBObject(FIRST_NAME, 1);
		first_name.put(ACCOUNT_STATUS, 1);
		first_name.put(ACCOUNT_STATE, 1);
		getCollection().createIndex(first_name);
	}
	/**
	 * Creates index on last name with status and state
	 */
	private void ensureIndexOnDateOfBirth(){
		DBObject last_name = new BasicDBObject(LAST_NAME, 1);
		last_name.put(DATE_OF_BIRTH, 1);
		last_name.put(ACCOUNT_STATE, 1);
		getCollection().createIndex(last_name);
	}

}

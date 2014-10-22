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
import com.uimirror.core.user.BasicDetails;
import com.uimirror.core.user.UserDBFields;

/**
 * Retrieves the credential store for the user.
 * @author Jay
 */
@Repository
public class PersistedUserBasicDetailsMongoStore extends AbstractMongoStore<BasicDetails> implements UserBasicDetailsStore {
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	@Autowired
	public PersistedUserBasicDetailsMongoStore(@Qualifier("userDetailsCol") DBCollection collection){
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
		DBObject first_last_name = new BasicDBObject(UserDBFields.FIRST_NAME, 1);
		first_last_name.put(UserDBFields.LAST_NAME, 1);
		first_last_name.put(UserDBFields.ACCOUNT_STATUS, 1);
		first_last_name.put(UserDBFields.ACCOUNT_STATE, 1);
		getCollection().createIndex(first_last_name);
	}
	/**
	 * Creates index on first name with status and state
	 */
	private void ensureIndexOnPermanetLocation(){
		DBObject first_name = new BasicDBObject(UserDBFields.FIRST_NAME, 1);
		first_name.put(UserDBFields.ACCOUNT_STATUS, 1);
		first_name.put(UserDBFields.ACCOUNT_STATE, 1);
		getCollection().createIndex(first_name);
	}
	/**
	 * Creates index on last name with status and state
	 */
	private void ensureIndexOnDateOfBirth(){
		DBObject last_name = new BasicDBObject(UserDBFields.LAST_NAME, 1);
		last_name.put(UserDBFields.DATE_OF_BIRTH, 1);
		last_name.put(UserDBFields.ACCOUNT_STATE, 1);
		getCollection().createIndex(last_name);
	}

}

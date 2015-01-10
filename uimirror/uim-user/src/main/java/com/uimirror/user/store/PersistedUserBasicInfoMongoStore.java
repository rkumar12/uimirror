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

import java.util.Map;
import java.util.WeakHashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.core.user.BasicInfo;
import com.uimirror.core.user.UserDBFields;

/**
 * Retrieves the credential store for the user.
 * @author Jay
 */
public class PersistedUserBasicInfoMongoStore extends AbstractMongoStore<BasicInfo> implements UserBasicInfoStore {
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection as parameter
	 */
	public PersistedUserBasicInfoMongoStore(DBCollection collection){
		super(collection, BasicInfo.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserBasicInfoStore#getUserInfoByProfileId(java.lang.String)
	 */
	@Override
	public BasicInfo getUserInfoByProfileId(String profileId) {
		return getById(profileId);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserBasicInfoStore#getUserInfoByEmail(java.lang.String)
	 */
	@Override
	public BasicInfo getUserInfoByEmail(String email) {
		return queryFirstRecord(emailMap(email));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserBasicInfoStore#deleteByprofileId(java.lang.String)
	 */
	@Override
	public void deleteByprofileId(String profileId) {
		deleteById(profileId);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserBasicInfoStore#activateByprofileId(java.lang.String)
	 */
	@Override
	public void activateByprofileId(String profileId) {
		updateById(profileId, statusQuery(AccountStatus.ACTIVE));
	}
	
	/**
	 * It will for the query for the account status
	 * @return
	 */
	private Map<String, Object> statusQuery(AccountStatus accountStatus){
		Map<String, Object> update = new WeakHashMap<String, Object>(3);
		update.put(UserDBFields.ACCOUNT_STATUS, accountStatus);
		return update;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserBasicInfoStore#deactivateByprofileId(java.lang.String)
	 */
	@Override
	public void blockByprofileId(String profileId) {
		 updateById(profileId, statusQuery(AccountStatus.BLOCKED));
		
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserBasicInfoStore#enableByprofileId(java.lang.String)
	 */
	@Override
	public void enableByprofileId(String profileId) {
		updateById(profileId, stateQuery(AccountState.ENABLED));
		
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserBasicInfoStore#disableByprofileId(java.lang.String)
	 */
	@Override
	public void disableByprofileId(String profileId) {
		updateById(profileId, stateQuery(AccountState.DISABLED));
	}
	
	/**
	 * It will for the query for the account state
	 * @return
	 */
	private Map<String, Object> stateQuery(AccountState accountState){
		Map<String, Object> update = new WeakHashMap<String, Object>(3);
		update.put(UserDBFields.ACCOUNT_STATE, accountState);
		return update;
	}
	
	/**
	 * Creates the email map
	 * @param email
	 * @return
	 */
	private Map<String, Object> emailMap(String email){
		Map<String, Object> map = new WeakHashMap<String, Object>(3);
		map.put(UserDBFields.EMAIL, email);
		return map;
	}


	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		ensureIndexOnFirstAndLastNameWithStatusAndState();
		ensureIndexOnFirstNameWithStateAndStatus();
		ensureIndexOnLastNameWithStateAndStatus();
		ensureIndexOnEmailWithStateAndStatus();
	}
	
	/**
	 * Creates index on first name and last name with status and state
	 */
	private void ensureIndexOnFirstAndLastNameWithStatusAndState(){
		DBObject first_last_name = new BasicDBObject(UserDBFields.FIRST_NAME, 1);
		first_last_name.put(UserDBFields.LAST_NAME, 1);
		first_last_name.put(UserDBFields.ACCOUNT_STATUS, 1);
		first_last_name.put(UserDBFields.ACCOUNT_STATE, 1);
		getCollection().createIndex(first_last_name);
	}
	/**
	 * Creates index on first name with status and state
	 */
	private void ensureIndexOnFirstNameWithStateAndStatus(){
		DBObject first_name = new BasicDBObject(UserDBFields.FIRST_NAME, 1);
		first_name.put(UserDBFields.ACCOUNT_STATUS, 1);
		first_name.put(UserDBFields.ACCOUNT_STATE, 1);
		getCollection().createIndex(first_name);
	}
	/**
	 * Creates index on last name with status and state
	 */
	private void ensureIndexOnLastNameWithStateAndStatus(){
		DBObject last_name = new BasicDBObject(UserDBFields.LAST_NAME, 1);
		last_name.put(UserDBFields.ACCOUNT_STATUS, 1);
		last_name.put(UserDBFields.ACCOUNT_STATE, 1);
		getCollection().createIndex(last_name);
	}
	/**
	 * Creates index on email with status and state
	 */
	private void ensureIndexOnEmailWithStateAndStatus(){
		DBObject email = new BasicDBObject(UserDBFields.EMAIL, 1);
		email.put(UserDBFields.ACCOUNT_STATUS, 1);
		email.put(UserDBFields.ACCOUNT_STATE, 1);
		getCollection().createIndex(email);
	}
	

}

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
package com.uimirror.core.user.store;

import static com.uimirror.core.mongo.BasicMongoOperators.LESSTHANEQUEAL;
import static com.uimirror.core.mongo.BasicMongoOperators.SET;
import static com.uimirror.core.mongo.feature.BasicDBFields.ID;
import static com.uimirror.core.user.UserAccountLogDBFields.CREATED_ON;
import static com.uimirror.core.user.UserDBFields.ACCOUNT_STATE;
import static com.uimirror.core.user.UserDBFields.EMAIL;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.core.AccountState;
import com.uimirror.core.exceptions.db.DBException;
import com.uimirror.core.store.AbstractMongoStore;
import com.uimirror.core.store.MongoStoreHelper;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.util.DateTimeUtil;

/**
 * A temp store for the user information.
 * 
 * @author Jay
 */
public class PersistedDefaultUserMongoStore extends AbstractMongoStore<DefaultUser> implements DefaultUserStore {
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection as parameter
	 * @param seqCollection as parameter
	 */
	public PersistedDefaultUserMongoStore(DBCollection collection, DBCollection seqCollection){
		super(collection, seqCollection, USER_BASIC_INFO_SEQ, DefaultUser.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserBasicInfoStore#getUserInfoByProfileId(java.lang.String)
	 */
	@Override
	public DefaultUser getByProfileId(String profileId) {
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
	 * @see com.uimirror.account.user.dao.DefaultUserStore#getUserByEmail(java.lang.String)
	 */
	@Override
	public DefaultUser getByEmail(String email) {
		return queryFirstRecord(getEmailQuery(email));
	}
	
	/**
	 * Get A Query for the email  query.
	 * @param email as parameter
	 * @return a map
	 */
	private Map<String, Object> getEmailQuery(String email){
		Map<String, Object> map = new WeakHashMap<String, Object>(3);
		map.put(EMAIL, email);
		return map;
	}


	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		ensureIndexOnEmail();
	}
	
	/**
	 * Creates index on email
	 */
	private void ensureIndexOnEmail(){
		DBObject email = new BasicDBObject(EMAIL, 1);
		getCollection().createIndex(email);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.user.store.DefaultUserStore#enableAccount(java.lang.String)
	 */
	@Override
	public DefaultUser enableAccount(String profileId) throws DBException {
		Map<String, Object> query =MongoStoreHelper.getIdMap(profileId);
		Map<String, Object> update = new WeakHashMap<String, Object>(3);
		update.put(ACCOUNT_STATE, AccountState.ENABLED.getState());
		Map<String, Object> set = new WeakHashMap<String, Object>(3);
		set.put(SET, update);
		return findAndModify(query, set);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.user.store.DefaultUserStore#getProfileIdByQuery(java.util.Map)
	 */
	@Override
	public List<DefaultUser> getUnVerifiedAccountBefore(int minutes) throws DBException {
		Map<String, Object> lessThanquery = new WeakHashMap<String, Object>(3);
		lessThanquery.put(LESSTHANEQUEAL, DateTimeUtil.minusToCurrentUTCTimeConvertToEpoch(minutes));
		Map<String, Object> query = new WeakHashMap<String, Object>();
		query.put(CREATED_ON, lessThanquery);
		Map<String, Object> fields = new WeakHashMap<String, Object>(3);
		fields.put(ID, 1);
		return getByQuery(query, fields);
	}

}

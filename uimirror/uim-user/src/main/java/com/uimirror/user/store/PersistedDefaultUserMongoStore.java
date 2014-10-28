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

import java.util.LinkedHashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.user.UserDBFields;

/**
 * Retrieves the credential store for the user.
 * @author Jay
 */
public class PersistedDefaultUserMongoStore extends AbstractMongoStore<DefaultUser> implements DefaultUserStore {
	
	private final static String USER_BASIC_INFO_SEQ = "ubis";
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	public PersistedDefaultUserMongoStore(DBCollection collection, DBCollection seqCollection){
		super(collection, seqCollection,USER_BASIC_INFO_SEQ,DefaultUser.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.user.dao.UserBasicInfoStore#getUserInfoByProfileId(java.lang.String)
	 */
	@Override
	public DefaultUser getUserByProfileId(String profileId) {
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
	public DefaultUser getUserByEmail(String email) {
		return queryFirstRecord(getEMailQuery(email));
	}
	
	private Map<String, Object> getEMailQuery(String email){
		Map<String, Object> map = new LinkedHashMap<String, Object>(3);
		map.put(UserDBFields.EMAIL, email);
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
		DBObject email = new BasicDBObject(UserDBFields.EMAIL, 1);
		getCollection().createIndex(email);
	}

}

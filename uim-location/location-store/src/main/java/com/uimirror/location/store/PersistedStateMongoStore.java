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
package com.uimirror.location.store;

import java.util.Map;
import java.util.WeakHashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.location.CountryDBFields;
import com.uimirror.location.State;
import com.uimirror.location.StateDBFields;

/**
 * A MONGO Persisted implementation for the {@link State}
 * @author Jay
 */
public class PersistedStateMongoStore extends AbstractMongoStore<State> implements StateStore{

	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	public PersistedStateMongoStore(DBCollection collection, DBCollection seqCollection){
		super(collection, seqCollection, STATE_SEQ, State.class);
	}
	
	@Override
	public State getById(String state_id) {
		return super.getById(state_id);
	}

	@Override
	public void deleteById(String state_id) {
		super.deleteById(state_id);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.location.store.CountryStore#getByName(java.lang.String)
	 */
	@Override
	public State getByName(String name) {
		Map<String, Object> query = new WeakHashMap<String, Object>(3);
		query.put(StateDBFields.NAME, name);
		return queryFirstRecord(query);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.location.store.CountryStore#getByShortName(java.lang.String)
	 */
	@Override
	public State getByShortName(String sh_name) {
		Map<String, Object> query = new WeakHashMap<String, Object>(3);
		query.put(StateDBFields.SHORT_NAME, sh_name);
		return queryFirstRecord(query);
	}
	
	@Override
	protected void ensureIndex() {
		indexOnName();
		indexOnShortName();
	}
	
	/**
	 * Creates index on name
	 */
	private void indexOnName(){
		DBObject name = new BasicDBObject(CountryDBFields.NAME, 1);
		getCollection().createIndex(name);
	}

	/**
	 * Creates index on  short name
	 */
	private void indexOnShortName(){
		DBObject name = new BasicDBObject(CountryDBFields.SHORT_NAME, 1);
		getCollection().createIndex(name);
	}
	
}

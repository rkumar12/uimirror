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

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.location.Country;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.LocationDBFields;

/**
 * A MONGO Persisted implementation for the {@link Country}
 * @author Jay
 */
public class PersistedLocationMongoStore extends AbstractMongoStore<DefaultLocation> implements LocationStore{

	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	public PersistedLocationMongoStore(DBCollection collection, DBCollection seqCollection){
		super(collection, seqCollection, LOCATION_SEQ, DefaultLocation.class);
	}
	
	@Override
	public DefaultLocation getByLocationId(String loc_id) {
		return getById(loc_id);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		indexOnName();
	}
	
	/**
	 * Creates index on name
	 */
	private void indexOnName(){
		DBObject name = new BasicDBObject(LocationDBFields.NAME, 1);
		getCollection().createIndex(name);
	}
	
}

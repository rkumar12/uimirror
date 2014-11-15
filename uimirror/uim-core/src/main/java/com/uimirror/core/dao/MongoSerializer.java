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
package com.uimirror.core.dao;

import java.util.Map;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.uimirror.core.util.BeanToMap;

/**
 * Mongo Serialization helper
 * 
 * @author Jay
 */
public abstract class MongoSerializer extends MongoInitializer{

	/**
	 * Assign/ Create collection from the given {@link DB}
	 * @param db inside of which data base document will be stored
	 * @param collectionName collection inside the DB
	 */
	public MongoSerializer(DB db, String collectionName) {
		super(db, collectionName);
	}

	/**
	 * Assign/ Create collection from the given {@link Mongo}
	 * @param mongo mongo connection 
	 * @param dbName data base name
	 * @param collectionName collection name
	 */
	public MongoSerializer(Mongo mongo, String dbName, String collectionName){
		super(mongo, dbName, collectionName);
	}
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection where documents will e stored
	 */
	public MongoSerializer(DBCollection collection){
		super(collection);
	}
	/**
	 * <p>Defines contract how a object class while saving will be serialized</p>
	 * This gives a default implementation of object getting converted to {@link Map}
	 * @param src source from which state will be serailized
	 * @return {@link Map} of the serailized details
	 */
	public Map<String, Object> toMap(Object src){
		return BeanToMap.toMap(src);
	}
}

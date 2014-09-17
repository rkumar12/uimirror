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

import org.springframework.util.StringUtils;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

/**
 * Common Mongo Collection Initializer
 * 
 * @author Jay
 */
public abstract class MongoInitializer {

	private final DBCollection collection;
	
	public MongoInitializer(Mongo mongo, String dbName, String collectionName) {
		checkValidity(mongo, dbName, collectionName);
		this.collection = mongo.getDB(dbName).getCollection(collectionName);
	}
	
	public MongoInitializer(DB db, String collectionName) {
		checkValidity(db, collectionName);
		this.collection = db.getCollection(collectionName);
	}
	
	public MongoInitializer(DBCollection collection) {
		checkValidity(collection);
		this.collection = collection;
	}

	public DBCollection getCollection() {
		return collection;
	}
	/**
	 * <p>This checks for the validity of the parameter passed</p>
	 * @param obj
	 * @param args
	 */
	private void checkValidity(Object obj, String ... args){
		if(obj == null){
			throw new IllegalStateException("Connection can't be established, check the parameters");
		}
		for(String arg : args){
			if(!StringUtils.hasText(arg)){
				throw new IllegalStateException("Connection can't be established, check the parameters");
			}
		}
	}

}

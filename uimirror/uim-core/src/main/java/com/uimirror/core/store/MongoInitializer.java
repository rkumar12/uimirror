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
package com.uimirror.core.store;

import org.springframework.util.StringUtils;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

/**
 * Common Mongo Collection Initializer
 * 
 * @author Jay
 */
public class MongoInitializer {

	protected final DBCollection collection;
	protected final DBCollection seqCollection;
	
	public MongoInitializer(Mongo mongo, String dbName, String collectionName, String seqCollectionName) {
		checkValidity(mongo, dbName, collectionName);
		this.collection = mongo.getDB(dbName).getCollection(collectionName);
		this.seqCollection = mongo.getDB(dbName).getCollection(seqCollectionName);
	}
	
	public MongoInitializer(Mongo mongo, String dbName, String collectionName) {
		checkValidity(mongo, dbName, collectionName);
		this.collection = mongo.getDB(dbName).getCollection(collectionName);
		this.seqCollection = null;
	}
	
	public MongoInitializer(DB db, String collectionName, String seqCollectionName) {
		checkValidity(db, collectionName);
		this.collection = db.getCollection(collectionName);
		this.seqCollection = db.getCollection(seqCollectionName);
	}

	public MongoInitializer(DB db, String collectionName) {
		checkValidity(db, collectionName);
		this.collection = db.getCollection(collectionName);
		this.seqCollection = null;
	}
	
	/**
	 * Initialize the store based on the collection and seq collection
	 *  
	 * @param collection where document will be persisted
	 * @param seqCollection collection where sequence will be stored
	 */
	public MongoInitializer(DBCollection collection, DBCollection seqCollection) {
		checkValidity(collection);
		this.collection = collection;
		this.seqCollection = seqCollection;
	}
	
	/**
	 * Initialize the store based on the collection
	 * 
	 * @param collection where document will be persisted
	 */
	public MongoInitializer(DBCollection collection) {
		checkValidity(collection);
		this.collection = collection;
		this.seqCollection = null;
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

	/**
	 * Gets the Seq collection for this store
	 * @return a collection instance
	 */
	public DBCollection getSeqCollection() {
		return seqCollection;
	}
	
	/**
	 * Gets the collection for this store
	 * @return a collection instance
	 */
	public DBCollection getCollection() {
		return collection;
	}

}

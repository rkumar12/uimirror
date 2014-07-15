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
package com.uimirror.challenge.dao.conf;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * <p>Factory bean to get the collection namespaces.</p>
 * @author Jayaram
 */
public class DBCollectionFactory {

	private final DB db;
	
	public DBCollectionFactory(DB db) {
		super();
		this.db = db;
	}
	
	/**
	 * <p>Returns the collection specified in the collection construct
	 * @return
	 */
	public DBCollection getCollection(String collectionName){
		return this.db.getCollection(collectionName);
	}
}

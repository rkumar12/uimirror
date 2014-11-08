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
package com.uimirror.core.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * <p>Factory bean to get the collection namespaces.</p>
 * @author Jayaram
 */
public final class DBCollectionUtil {

	/**
	 * <p>Returns the collection specified in the collection construct
	 * @param db from where collection will be retrieved
	 * @param collectionName name that will be retrieved
	 * @return {@link DBCollection} from the given {@link DB} and collectionName
	 */
	public static DBCollection getCollection(final DB db, final String collectionName){
		if(db == null){
			throw new IllegalArgumentException("No Database to get a collection from");
		}
		if(collectionName == null || collectionName.trim().isEmpty()){
			throw new IllegalArgumentException("Collection Name can't be empty");
		}
		return db.getCollection(collectionName);
	}
}

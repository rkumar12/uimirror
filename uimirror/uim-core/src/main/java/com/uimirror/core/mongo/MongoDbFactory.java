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
import com.mongodb.Mongo;

/**
 * ***********************************************************************
 * This class is developed by UIMirror Team.
 * Details of class goes like : This the factory class for the mongo db 
 * connection per db.
 * 
 * @author Jayaram
 * @version $
 * @see 
 * @createdOn 15-Mar-2014 9:09:48 PM
 * @modifiedby Jayaram
 * @modifiedon 15-Mar-2014 9:09:48 PM
 * ***********************************************************************
 */
public final class MongoDbFactory {
	
	/**
	 * <p>Gets or create the db from the 
	 * @return
	 */
	public static DB getDB(Mongo mongo, String dbName){
		if(dbName == null || dbName.trim().isEmpty()){
			throw new IllegalArgumentException("Can't get DB from Mongo connection, as dbName can't be empty.");
		}
		if(mongo == null){
			throw new IllegalArgumentException("No Mongo connection to retrive a data base from");
		}
		return mongo.getDB(dbName);
	}

}

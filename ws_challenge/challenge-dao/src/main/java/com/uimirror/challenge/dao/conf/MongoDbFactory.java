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

import org.springframework.util.StringUtils;

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
public class MongoDbFactory {
	
	private final Mongo mongo;

	public MongoDbFactory(Mongo mongo) {
		super();
		this.mongo = mongo;
	}
	
	/**
	 * <p>Gets or create the db from the 
	 * @return
	 */
	public DB getDB(String dbName){
		if(StringUtils.isEmpty(dbName)){
			throw new IllegalArgumentException("Can't get DB from Mongo connection, as dbName can't be empty.");
		}
		return this.mongo.getDB(dbName);
	}
	
	/**
	 * <p>Close all the open connection with mongo server.
	 */
	public void close(){
		this.mongo.close();
	}

}

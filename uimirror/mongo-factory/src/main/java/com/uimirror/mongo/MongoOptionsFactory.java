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
package com.uimirror.mongo;

import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;

/**
 * <p>This will have all the configurations releated to the mongo Options</p>
 * @author Jayaram
 */
public final class MongoOptionsFactory {

	private MongoOptionsFactory() {
		super();
	}

	/**
	 * <p>Builds the options for the mongo connection.
	 * @return
	 */
	public static MongoClientOptions getMongoOptions(int maxPoolSize, int connectTimeout, ReadPreference readPreference, WriteConcern writeConcern){
		return MongoClientOptions.builder()
                .connectionsPerHost(maxPoolSize)
                .connectTimeout(connectTimeout)
                .readPreference(readPreference)
                .writeConcern(writeConcern)
                .build();
	}
	
}

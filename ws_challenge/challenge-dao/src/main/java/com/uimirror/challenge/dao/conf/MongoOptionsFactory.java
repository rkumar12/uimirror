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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;

/**
 * <p>This will have all the configurations releated to the mongo Options</p>
 * @author Jayaram
 */
@Service
public class MongoOptionsFactory {
	
	@Value(("${uimirror.mongo.maxpoolsize:20}"))
	private int maxPoolSize;
	@Value(("${uimirror.mongo.connecttimeout:2000}"))
	private int connectTimeout;
	@Value(("${T(com.mongodb.ReadPreference).secondaryPreferred()}"))
	private ReadPreference readPreference;
	@Value(("${T(com.mongodb.WriteConcern).ACKNOWLEDGED}"))
	private WriteConcern writeConcern;

	public MongoOptionsFactory(int maxPoolSize, int connectTimeout) {
		super();
		this.maxPoolSize = maxPoolSize;
		this.connectTimeout = connectTimeout;
	}

	public MongoOptionsFactory() {
		super();
	}

	/**
	 * <p>Builds the options for the mongo connection.
	 * @return
	 */
	public MongoClientOptions getMongoOptions(){
		return MongoClientOptions.builder()
                .connectionsPerHost(maxPoolSize)
                .connectTimeout(this.connectTimeout)
                .readPreference(readPreference)
                .writeConcern(writeConcern)
                .build();
	}

	
	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public ReadPreference getReadPreference() {
		return readPreference;
	}

	public void setReadPreference(ReadPreference readPreference) {
		this.readPreference = readPreference;
	}

	public WriteConcern getWriteConcern() {
		return writeConcern;
	}

	public void setWriteConcern(WriteConcern writeConcern) {
		this.writeConcern = writeConcern;
	}
}

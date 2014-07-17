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

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;

/**
 * @author Jayaram
 *
 */
public final class ConnectionFactory {

	private String host;
	private int maxPoolSize = 20;
	private int connectTimeout = 2000;
	private ReadPreference readPreference = ReadPreference.secondaryPreferred();
	private WriteConcern writeConcern = WriteConcern.ACKNOWLEDGED;
	private Mongo mongo;
	
	/**
	 * <p>Using Mongo options, host and port it gets a connection</p>
	 * @return
	 * @throws UnknownHostException
	 */
	public Mongo getMongoClient() throws UnknownHostException{
		this.mongo = new MongoClient(host, MongoOptionsFactory.getMongoOptions(this.maxPoolSize, this.connectTimeout, this.readPreference, this.writeConcern)); 
		return mongo;
	}
	
	public void destroy(){
		if(this.mongo != null)
			this.mongo.close();
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the maxPoolSize
	 */
	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	/**
	 * @param maxPoolSize the maxPoolSize to set
	 */
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	/**
	 * @return the connectTimeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @return the readPreference
	 */
	public ReadPreference getReadPreference() {
		return readPreference;
	}

	/**
	 * @param readPreference the readPreference to set
	 */
	public void setReadPreference(ReadPreference readPreference) {
		this.readPreference = readPreference;
	}

	/**
	 * @return the writeConcern
	 */
	public WriteConcern getWriteConcern() {
		return writeConcern;
	}

	/**
	 * @param writeConcern the writeConcern to set
	 */
	public void setWriteConcern(WriteConcern writeConcern) {
		this.writeConcern = writeConcern;
	}
	
}

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
package com.uimirror.ws.api.security.conf;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.uimirror.mongo.ConnectionFactory;
import com.uimirror.mongo.DBCollectionUtil;
import com.uimirror.mongo.MongoDbFactory;

/**
 * <p>Configures and register the beans 
 * @author Jayaram
 */
@Configuration
public class SecurityDataStoreIntitializer {
	
	@Value("${mongo.host:127.0.0.1}")
	protected String host;
	@Value("${mongo.db.uim.ouath:uim_ouath}")
	protected String uimOuathDB;
	@Value("${mongo.cl.uim.ouath.client.details:uim_client_details}")
	protected String uimClientDetails;
	
	/**
	 * <p>Creates the Mongo Options</p>
	 * <p>Using Mongo options, host and port it gets a connection</p>
	 * @return
	 * @throws UnknownHostException 
	 */
	//@Bean(destroyMethod="destroy")
	@Bean
	public Mongo createConnection() throws UnknownHostException{
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(host);
		return cf.getMongoClient();
	}
	
	/**
	 * <p>Create a DB factory from the connection</p>
	 * <p>Get a specific DB from the db factory bean</p>
	 * @return
	 * @throws UnknownHostException
	 */
	@Bean
	public DB getDB() throws UnknownHostException{
		return MongoDbFactory.getDB(createConnection(), this.uimOuathDB);
	}
	
	/**
	 * <p>A DB can have multiple collections, so create a collection factory</p>
	 * <p>From the Collection Factory obtain the specific collection i.e uim_client_details</p>
	 * @return
	 * @throws UnknownHostException
	 */
	@Bean(name="uimClientDetails")
	public DBCollection getClientDetailsCollection() throws UnknownHostException{
		return DBCollectionUtil.getCollection(getDB(), this.uimClientDetails);
	}

}
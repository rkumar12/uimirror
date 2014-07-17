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

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

/**
 * <p>This contains the Mongo DB and its collection bean details.</p>
 * 
 * @author Jayaram
 *
 */
@Configuration
public class DaoBeanIntitializer {

	@Value("${mongo.host:127.0.0.1}")
	protected String host;
	@Value("${mongo.db.challenge:uim_challenge}")
	protected String challengeDb;
	@Value("${mongo.cl.challenge:challenge}")
	protected String challengeCollection;
	//Inject main options factory like timeout etc
	@Autowired
	private MongoOptionsFactory mongoOptionsFactory;
	
	/**
	 * <p>Creates the Mongo Options</p>
	 * @return
	 */
	@Bean
	public MongoClientOptions createClientOptions(){
		return mongoOptionsFactory.getMongoOptions();
	}
	/**
	 * <p>Using Mongo options, host and port it gets a connection</p>
	 * @return
	 * @throws UnknownHostException
	 */
	@Bean
	public MongoClient createMongoClient() throws UnknownHostException{
		return new MongoClient(host, createClientOptions());
	}
	
	/**
	 * <p>Create a DB factory from the connection</p>
	 * @return
	 * @throws UnknownHostException
	 */
	@Bean(destroyMethod="close")
	public MongoDbFactory createDbFactory() throws UnknownHostException{
		return new MongoDbFactory(createMongoClient());
	}
	
	/**
	 * <p>Get a specific DB from the db factory bean</p>
	 * @return
	 * @throws UnknownHostException
	 */
	@Bean
	public DB createUimChallengeDb() throws UnknownHostException{
		return createDbFactory().getDB(this.challengeDb);
	}
	
	/**
	 * <p>A DB can have multiple collections, so create a collection factory</p>
	 * @return
	 * @throws UnknownHostException
	 */
	@Bean
	public DBCollectionFactory createCollectionFactory() throws UnknownHostException{
		return new DBCollectionFactory(createUimChallengeDb());
	}
	
	/**
	 * <p>From the Collection Factory obtain the specific collection i.e challenge</p>
	 * @return
	 * @throws UnknownHostException
	 */
	@Bean(name="chCollection")
	public DBCollection createChallengeCollection() throws UnknownHostException{
		return createCollectionFactory().getCollection(this.challengeCollection);
	}
	
}

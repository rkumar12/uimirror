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

import org.springframework.util.Assert;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.uimirror.mongo.ConnectionFactory;
import com.uimirror.mongo.DBCollectionUtil;
import com.uimirror.mongo.MongoDbFactory;

/**
 * <p>Configures the Security access token Data Store</p> 
 * @author Jayaram
 */
public class SecurityDataStoreIntitializer implements SecurityAccessTokenDataStore{
	
	private String host;
	private String uimOuathDBName;
	private String uimAccessTokenColName;
	private Mongo mongo;
	private DB uimOuathDb;
	private DBCollection uimAccessTokenStore;
	
	/**
	 * <p>This creates a new connection from the provided connection parameters</p>
	 * @param host
	 * @param uimOuathDBName
	 * @param uimAccessTokenColName
	 * @throws UnknownHostException
	 */
	public SecurityDataStoreIntitializer(String host, String uimOuathDBName, String uimAccessTokenColName) throws UnknownHostException {
		Assert.hasText(host, "Host Can't be empty");
		Assert.hasText(uimOuathDBName, "Ouath DB name can't be empty");
		Assert.hasText(uimAccessTokenColName, "Access Token Collection name can't be empty");
		this.host = host;
		this.uimOuathDBName = uimOuathDBName;
		this.uimAccessTokenColName = uimAccessTokenColName;
		createConnection();
		createDB();
		createAccesstokenCollection();
	}
	
	/**
	 * <p>This generates the collection from the provided mongo instance</p>
	 * @param mongo
	 * @param uimOuathDBName
	 * @param uimAccessTokenColName
	 * @throws UnknownHostException
	 */
	public SecurityDataStoreIntitializer(Mongo mongo, String uimOuathDBName, String uimAccessTokenColName) throws UnknownHostException {
		Assert.hasText(uimOuathDBName, "Ouath DB name can't be empty");
		Assert.hasText(uimAccessTokenColName, "Access Token Collection name can't be empty");
		Assert.notNull(mongo, "An Ouath Mongo instance can't be null");
		this.mongo = mongo;
		createDB();
		createAccesstokenCollection();
	}
	
	/**
	 * <p>This creates collection from the provided DB</p>
	 * @param uimOuathDb
	 * @param uimAccessTokenColName
	 * @throws UnknownHostException
	 */
	public SecurityDataStoreIntitializer(DB uimOuathDb, String uimAccessTokenColName) throws UnknownHostException {
		Assert.hasText(uimAccessTokenColName, "Access Token Collection name can't be empty");
		Assert.notNull(this.uimOuathDb, "An Ouath Mongo DB instance can't be null");
		this.uimOuathDb = uimOuathDb;
		createAccesstokenCollection();
	}
	
	
	/**
	 * <p>This initialize the connection already provided</p>
	 * @param uimAccessTokenStore
	 * @throws UnknownHostException
	 */
	public SecurityDataStoreIntitializer(DBCollection uimAccessTokenStore) throws UnknownHostException {
		Assert.notNull(uimAccessTokenStore, "Access Token Collection can't be empty.");
		this.uimAccessTokenStore = uimAccessTokenStore;
	}

	/**
	 * <p>Creates the Mongo Options</p>
	 * <p>Using Mongo options, host and port it gets a connection</p>
	 * @throws UnknownHostException 
	 */
	private void createConnection() throws UnknownHostException{
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(this.host);
		this.mongo = cf.getMongoClient();
	}
	
	/**
	 * <p>Create a DB factory from the connection</p>
	 * <p>Create a specific DB from the db factory bean</p>
	 */
	private void createDB(){
		Assert.notNull(this.mongo, "An Ouath Mongo instance can't be null");
		this.uimOuathDb = MongoDbFactory.getDB(this.mongo, this.uimOuathDBName); 
	}
	
	/**
	 * <p>A DB can have multiple collections, so create a collection factory</p>
	 * <p>From the Collection Factory obtain the specific collection i.e uim_client_details</p>
	 */
	private void createAccesstokenCollection(){
		Assert.notNull(this.uimOuathDb, "An Ouath Mongo DB instance can't be null");
		this.uimAccessTokenStore = DBCollectionUtil.getCollection(this.uimOuathDb, this.uimAccessTokenColName);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.conf.SecurityAccessTokenDataStore#getTokenStoreCollection()
	 */
	@Override
	public DBCollection getTokenStoreCollection() {
		return this.uimAccessTokenStore;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.conf.SecurityAccessTokenDataStore#destroy()
	 */
	@Override
	public void destroy() {
		if(this.mongo != null){
			this.mongo.close();
		}
	}

}

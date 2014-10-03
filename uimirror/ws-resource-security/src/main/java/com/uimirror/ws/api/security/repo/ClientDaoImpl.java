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
package com.uimirror.ws.api.security.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.uimirror.mongo.DBCollectionUtil;
import com.uimirror.mongo.MongoDbFactory;
import com.uimirror.ws.api.security.bean.base.Client;
import com.uimirror.ws.api.security.common.SecurityFieldConstants;

/**
 * @author Jay
 */
public class ClientDaoImpl implements ClientDao {

	protected static final Logger LOG = LoggerFactory.getLogger(ClientDaoImpl.class);
	
	private final DBCollection clientCollection;
	private final Mongo mongo;
	private final DB db;
	private String dbName = OUATH_DB;
	private String collectionName = CLIENT_COLLECTION;
	
	/**
	 *<p>This will initialize the collection.</p>
	 *@param collection {@link DBCollection} instance
	 *@throws IllegalArgumentException if collection name is empty 
	 */
	public ClientDaoImpl(DBCollection collection) throws IllegalArgumentException{
		Assert.notNull(collection, "Collection Name can't be empty");
		this.clientCollection = collection;
		this.mongo = null;
		this.db = null;
		this.clientCollection.setObjectClass(Client.class);
	}
	
	/**
	 * <p>This will initialize collection instance from {@link Mongo} instance</p>
	 * @param mongo
	 * @throws IllegalArgumentException
	 */
	public ClientDaoImpl(Mongo mongo) throws IllegalArgumentException{
		Assert.notNull(mongo, "Mongo Instance can't be empty");
		this.mongo = mongo;
		this.db = MongoDbFactory.getDB(this.mongo, this.dbName);
		this.clientCollection = DBCollectionUtil.getCollection(this.db, this.collectionName);
		this.clientCollection.setObjectClass(Client.class);
	}
	
	/**
	 * <p>This will initialize collection from the {@link DB} instance</p>
	 * @param db
	 * @throws IllegalArgumentException
	 */
	public ClientDaoImpl(DB db) throws IllegalArgumentException{
		Assert.notNull(db, "DB Instance can't be empty");
		this.db = db;
		this.clientCollection = DBCollectionUtil.getCollection(this.db, this.collectionName);
		this.mongo = null;
		this.clientCollection.setObjectClass(Client.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.ClientDao#insert(com.uimirror.ws.api.security.bean.base.Client)
	 */
	@Override
	public void insert(Client client) throws IllegalArgumentException, MongoException {
		LOG.info("[START]- Saving a client instance");
		Assert.notNull(client, "Client can't be null");
		this.clientCollection.insert(client);
		LOG.info("[END]- Saving a client instance");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.ClientDao#findById(java.lang.String)
	 */
	@Override
	public Client findById(String id) throws IllegalArgumentException, MongoException {
		LOG.info("[START]- Searching a client by its ID");
		Assert.hasText(id, "Client Id is invalid");
		DBObject query = new BasicDBObject(4);
		query.put(SecurityFieldConstants._ID, id);
		Client client = (Client)this.clientCollection.findOne(query);
		LOG.info("[END]- Searching a client by its ID");
		return client;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.ClientDao#findBy(java.util.Map)
	 */
	@Override
	public List<Client> findBy(Map<String, Object> query) throws IllegalArgumentException, MongoException {
		LOG.info("[START]- Searching zero or more client by some creteria");
		Assert.notNull(query, "Search Creteria Can't be invalid");
		DBObject q = new BasicDBObject(query);
		DBCursor result = this.clientCollection.find(q);
		//Retrieve max of 20 at a time
		result.batchSize(20);
		List<Client> clients = new ArrayList<Client>(20);
		result.forEach((client) -> clients.add((Client)client));
		result.close();
		LOG.info("[END]- Searching zero or more client by some creteria");
		return clients;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.ClientDao#findAll()
	 */
	@Override
	public List<Client> findAll() throws MongoException {
		LOG.info("[START]- Searching all available client");
		DBCursor result = this.clientCollection.find();
		//Retrieve max of 20 at a time
		result.batchSize(20);
		List<Client> clients = new ArrayList<Client>(20);
		result.forEach((client) -> clients.add((Client)client));
		result.close();
		LOG.info("[END]- Searching all available client");
		return clients;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.ClientDao#findAllActive()
	 */
	@Override
	public List<Client> findAllActive() throws MongoException {
		LOG.info("[START]- Searching all available active client");
		DBObject query = new BasicDBObject(SecurityFieldConstants._CLIENT_IS_ACTIEVE, SecurityFieldConstants._ST_NUM_1);
		DBCursor result = this.clientCollection.find(query);
		//Retrieve max of 20 at a time
		result.batchSize(20);
		List<Client> clients = new ArrayList<Client>(20);
		result.forEach((client) -> clients.add((Client)client));
		LOG.info("[END]- Searching all available active client");
		result.close();
		return clients;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.ClientDao#findAllInactive()
	 */
	@Override
	public List<Client> findAllInactive() throws MongoException {
		LOG.info("[START]- Searching all available in-active client");
		DBObject query = new BasicDBObject(SecurityFieldConstants._CLIENT_IS_ACTIEVE, SecurityFieldConstants._ST_NUM_0);
		DBCursor result = this.clientCollection.find(query);
		//Retrieve max of 20 at a time
		result.batchSize(20);
		List<Client> clients = new ArrayList<Client>(20);
		result.forEach((client) -> clients.add((Client)client));
		result.close();
		LOG.info("[END]- Searching all available in-active client");
		return clients;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.ClientDao#updateById(java.lang.String, java.util.Map)
	 */
	@Override
	public void updateById(String id, Map<String, Object> updates) throws IllegalArgumentException, MongoException {
		LOG.info("[START]- Updateing Client by ID");
		Assert.hasText(id, "Client Id can't be invalid");
		Assert.notNull(updates, "Client details to be updated can't be invalid");
		DBObject query = new BasicDBObject(SecurityFieldConstants._ID, id);
		this.clientCollection.updateMulti(query, new BasicDBObject(updates));
		LOG.info("[END]- Updating Client by ID");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.ClientDao#updateBy(java.util.Map, java.util.Map)
	 */
	@Override
	public void updateBy(Map<String, Object> query, Map<String, Object> updates) throws IllegalArgumentException, MongoException {
		LOG.info("[START]- Updateing Client by client search query");
		Assert.notNull(updates, "Client details to be updated can't be invalid");
		Assert.notNull(query, "Client Search creteria can't be invalid");
		this.clientCollection.updateMulti(new BasicDBObject(query), new BasicDBObject(updates));
		LOG.info("[END]- Updating Client by client search query");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.ClientDao#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(String id) throws IllegalArgumentException, MongoException {
		LOG.info("[START]- Deleting client documets by client id");
		Assert.hasText(id, "Client Id can't be invalid");
		this.clientCollection.remove(new BasicDBObject(SecurityFieldConstants._ID, id));
		LOG.info("[END]- Deleting client documets by client id");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.ClientDao#deleteAll()
	 */
	@Override
	public void deleteAll() {
		LOG.info("[START]- Deleting all client Documents");
		this.clientCollection.remove(new BasicDBObject(1));
		LOG.info("[END]- Deleting all client Documents");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.ClientDao#deleteBy(java.util.Map)
	 */
	@Override
	public void deleteBy(Map<String, Object> query) throws IllegalArgumentException, MongoException {
		LOG.info("[START]- Deleting client documets by client serach creteria");
		Assert.notNull(query, "Client Search query to delete can't be invalid");
		this.clientCollection.remove(new BasicDBObject(query));
		LOG.info("[END]- Deleting client documets by client serach creteria");
	}

}

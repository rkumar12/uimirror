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
package com.uimirror.ws.api.audit.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.mongodb.QueryBuilder;
import com.uimirror.core.mongo.DBCollectionUtil;
import com.uimirror.core.mongo.MongoDbFactory;
import com.uimirror.ws.api.audit.Audit;
import com.uimirror.ws.api.audit.common.AuditFieldConstants;

/**
 * @author Jay
 */
public class AuditRepoImpl implements AuditRepo {
	
	protected static final Logger LOG = LoggerFactory.getLogger(AuditRepoImpl.class);
	
	private final DBCollection clientAuditStore;
	private final Mongo mongo;
	private final DB db;
	private String dbName = AUDIT_DB;
	private String collectionName = CLIENT_COLLECTION;

	//Initialize the Collection Only
	public AuditRepoImpl(DBCollection clientAuditStore) {
		Assert.notNull(clientAuditStore, "Client AUdit Collection Can't be Empty");
		this.clientAuditStore = clientAuditStore;
		this.clientAuditStore.setObjectClass(Audit.class);
		this.mongo = null;
		this.db = null;
	}

	//Initialize the mongo connection then extract the collection out of that
	public AuditRepoImpl(Mongo mongo) {
		Assert.notNull(mongo, "A Data Base connection is required");
		this.mongo = mongo;
		this.db = MongoDbFactory.getDB(this.mongo, this.dbName);
		this.clientAuditStore = DBCollectionUtil.getCollection(this.db, this.collectionName);
		this.clientAuditStore.setObjectClass(Audit.class);
	}

	//Initialize via the DB instance
	public AuditRepoImpl(DB db) {
		Assert.notNull(db, "A Database is Required");
		this.db = db;
		this.clientAuditStore = DBCollectionUtil.getCollection(this.db, this.collectionName);
		this.clientAuditStore.setObjectClass(Audit.class);
		this.mongo = null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#insert(com.uimirror.ws.api.audit.Audit)
	 */
	@Override
	public void insert(Audit audit) throws IllegalArgumentException, MongoException {
		Assert.notNull(audit, "Null Object can't be saved");
		LOG.debug("[START]-Creating a Audit");
		this.clientAuditStore.save(audit);
		LOG.debug("[END]-Creating a Audit");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#findById(java.lang.String)
	 */
	@Override
	public Audit findById(String id) throws IllegalArgumentException, MongoException {
		Assert.hasText(id, "A Audit Id is required to perform a search");
		LOG.debug("[START]- Finding Audit by ID");
		DBObject query = new BasicDBObject(4);
		query.put(AuditFieldConstants._ID, id);
		Audit audit = (Audit) this.clientAuditStore.findOne(query);
		LOG.debug("[END]- Finding Audit by ID");
		return audit;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#findAll()
	 */
	@Override
	public List<Audit> findAll() throws MongoException {
		LOG.debug("[START]- Finding All the Audit Details");
		DBCursor cursor = this.clientAuditStore.find();
		LOG.debug("[END]- Finding All the Audit Details");
		return getFromcursor(cursor);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#findByAfterDate(java.time.LocalDateTime)
	 */
	@Override
	public List<Audit> findByAfterDate(LocalDateTime time) throws IllegalArgumentException, MongoException {
		Assert.notNull(time, "Time To to search can't be Empty");
		LOG.debug("[START]- Finding Audit by time");
		DBObject query = new BasicDBObject(4);
		query.put(AuditFieldConstants._RQ_TIME, new BasicDBObject(AuditFieldConstants._GTE, time));
		DBCursor cursor =  this.clientAuditStore.find(query);
		LOG.debug("[END]- Finding Audit by ID");
		return getFromcursor(cursor);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#findByDateRange(java.time.LocalDateTime, java.time.LocalDateTime)
	 */
	@Override
	public List<Audit> findByDateRange(LocalDateTime fromTime, LocalDateTime toTime) throws IllegalArgumentException, MongoException {
		Assert.notNull(fromTime, "From Time To to search can't be Empty");
		Assert.notNull(toTime, "To Time To to search can't be Empty");
		LOG.debug("[START]- Finding Audit in time range");
		QueryBuilder builder = QueryBuilder.start(AuditFieldConstants._RQ_TIME).greaterThanEquals(fromTime).and(AuditFieldConstants._RQ_TIME).lessThanEquals(toTime);
		DBCursor cursor =  this.clientAuditStore.find(builder.get());
		LOG.debug("[END]- Finding Audit in Time range");
		return getFromcursor(cursor);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#findByClientId(java.lang.String)
	 */
	@Override
	public List<Audit> findByClientId(String clientId)
			throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#findByClientIdAndAfterDate(java.lang.String, java.time.LocalDateTime)
	 */
	@Override
	public List<Audit> findByClientIdAndAfterDate(String clientId,
			LocalDateTime time) throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#findByClientIdAndTimeRange(java.lang.String, java.time.LocalDate, java.time.LocalDate)
	 */
	@Override
	public List<Audit> findByClientIdAndTimeRange(String clientId,
			LocalDate fromTime, LocalDate toTime)
			throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#countByAfterDate(java.time.LocalDateTime)
	 */
	@Override
	public int countByAfterDate(LocalDateTime time)
			throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#countByDateRange(java.time.LocalDateTime, java.time.LocalDateTime)
	 */
	@Override
	public int countByDateRange(LocalDateTime fromTime, LocalDateTime toTime)
			throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#countByClientId(java.lang.String)
	 */
	@Override
	public int countByClientId(String clientId)
			throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#countByClientIdAndAfterdate(java.lang.String, java.time.LocalDate)
	 */
	@Override
	public int countByClientIdAndAfterdate(String clientId, LocalDate time)
			throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#countByClientIdAndDateRange(java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime)
	 */
	@Override
	public int countByClientIdAndDateRange(String clientId,
			LocalDateTime fromTime, LocalDateTime toTime)
			throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#countSuccessResponseByClientId(java.lang.String)
	 */
	@Override
	public int countSuccessResponseByClientId(String clientId)
			throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#countSuccessResponseByClientDateRange(java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime)
	 */
	@Override
	public int countSuccessResponseByClientDateRange(String clientId,
			LocalDateTime fromTime, LocalDateTime toTime)
			throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#countSuccessResponseByClientIdAndAfterDate(java.lang.String, java.time.LocalDateTime)
	 */
	@Override
	public int countSuccessResponseByClientIdAndAfterDate(String clientId,
			LocalDateTime time) throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#countAll()
	 */
	@Override
	public int countAll() throws MongoException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(String id) throws IllegalArgumentException,
			MongoException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#deleteAll()
	 */
	@Override
	public void deleteAll() throws MongoException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.repo.AuditRepo#updateStatusById(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateStatusById(String id, String status)
			throws IllegalArgumentException, MongoException {
		// TODO Auto-generated method stub

	}
	
	private List<Audit> getFromcursor(DBCursor cursor){
		return reteriveFromCursor(cursor, 20);
	}
	
	/**
	 * <p>Retrieves from the cursor</p>
	 * @param cursor
	 * @param batchSize
	 * @return
	 */
	private List<Audit> reteriveFromCursor(DBCursor cursor, int batchSize){
		batchSize = batchSize > 0 ? batchSize : 20;
		//Retrieve max of 20 at a time by default
		cursor.batchSize(batchSize);
		List<Audit> lst = new ArrayList<Audit>(batchSize);
		cursor.forEach((audit) -> lst.add((Audit)audit));
		return lst;
	}

}

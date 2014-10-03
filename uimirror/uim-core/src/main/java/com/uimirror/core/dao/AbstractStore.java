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
package com.uimirror.core.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import com.uimirror.core.BasicDBFields;
import com.uimirror.core.BasicMongoOperators;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.mongo.feature.MongoDocumentSerializer;

/**
 * Basic and essential implementation for the {@linkplain BasicStore}
 * Make sure in case of query giving the proper {@linkplain Map} with key as one of 
 * the operators defined in {@linkplain BasicMongoOperators}
 * Make sure in case of update giving the proper {@linkplain Map} with key as one of 
 * the operators defined in {@linkplain BasicMongoOperators} leaving the top most document
 * as by default its taking {@linkplain BasicMongoOperators#SET} only for {@linkplain BasicStore#setSingleById(Object, MongoDocumentSerializer)}
 * else in case for the updateByQuery pass the updated {@linkplain Map<String, Object} as fully update filed
 * with proper operators from the {@linkplain BasicMongoOperators}
 * 
 * @author Jay
 */
public class AbstractStore extends MongoInitializer implements BasicStore{

	protected static final Logger LOG = LoggerFactory.getLogger(AbstractStore.class);
	
	/**
	 * Assign/ Create collection from the given {@link Mongo}
	 * @param mongo
	 * @param dbName
	 * @param collectionName
	 */
	public AbstractStore(Mongo mongo, String dbName, String collectionName){
		super(mongo, dbName, collectionName);
	}
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	public AbstractStore(DBCollection collection){
		super(collection);
	}
	
	/**
	 * @param db
	 * @param collectionName
	 */
	public AbstractStore(DB db, String collectionName) {
		super(db, collectionName);
	}


	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#store(com.uimirror.core.mongo.feature.MongoDocumentSerializer)
	 */
	@Override
	@MapException(use=MongoExceptionMapper.NAME)
	public void store(MongoDocumentSerializer doc) throws DBException {
		LOG.debug("[START]- Storing the object");
		BasicDBObject obj = new BasicDBObject(doc.toMap());
		getCollection().save(obj);
		LOG.debug("[END]- Storing the object");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#deleteById(java.lang.Object)
	 */
	@Override
	@MapException(use=MongoExceptionMapper.NAME)
	public void deleteById(Object id) throws DBException {
		LOG.debug("[SINGLE]- Deleting the object");
		delete(getIdQuery(id));
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#deleteByQuery(java.util.Map)
	 */
	@Override
	@MapException(use=MongoExceptionMapper.NAME)
	public int deleteByQuery(Map<String, Object> query) throws DBException {
		LOG.debug("[SINGLE]- Deleting the objects based on the query");
		return getNumberOfDocumentAffected(delete(new BasicDBObject(query)));
	}
	
	/**
	 * Delete the document based on the object query specified,
	 * If empty, then whole documents in the collection.
	 * 
	 * @param obj
	 * @return
	 */
	protected WriteResult delete(DBObject obj){
		return getCollection().remove(obj);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#getById(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@MapException(use=MongoExceptionMapper.NAME)
	public Map<String,Object> getById(Object id) throws DBException {
		LOG.debug("[START]- Getting an object based on the ID");
		DBObject result = getCollection().findOne(getIdQuery(id));
		if(result == null){
			throw new RecordNotFoundException();
		}
		LOG.debug("[END]- Getting an object based on the ID");
		return result.toMap();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#updateById(java.lang.Object, com.uimirror.core.mongo.feature.MongoDocumentSerializer)
	 */
	@Override
	public int setSingleById(Object id, MongoDocumentSerializer toUpdate) throws DBException {
		LOG.debug("[SINGLE]- Updateing the document by ID");
		return updateByQuery(getIdMap(id), getSetSingleMap(toUpdate.toMap()));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#updateByQuery(java.util.Map, com.uimirror.core.mongo.feature.MongoDocumentSerializer)
	 */
	@Override
	public int setSingleByQuery(Map<String, Object> query, MongoDocumentSerializer toUpdate) throws DBException {
		return updateByQuery(query, getSetSingleMap(toUpdate.toMap()));
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#updateByQuery(java.util.Map, java.util.Map)
	 */
	@Override
	@MapException(use=MongoExceptionMapper.NAME)
	public int updateByQuery(Map<String, Object> query, Map<String, Object> toUpdate) throws DBException {
		return getNumberOfDocumentAffected(update(query, toUpdate));
	}
	
	/**
	 * Updates to the document
	 * @param q
	 * @param u
	 * @return
	 */
	protected WriteResult update(Map<String, Object> q, Map<String, Object> u){
		return getCollection().update(new BasicDBObject(q), new BasicDBObject(u));
	}
	
	/**
	 * Concerts the {@linkplain DBCursor} into list of {@linkplain Map}
	 * @param cursor
	 * @param fetchSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getFromCursor(DBCursor cursor, int fetchSize){
		fetchSize = fetchSize <= 0 ? 20 : fetchSize;
		int thirtyPercentage = fetchSize*((30/100)*100);
		//Retrieve max of 20 at a time
		cursor.batchSize(fetchSize);
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>(fetchSize+(fetchSize+thirtyPercentage));
		cursor.forEach((result) -> results.add(result.toMap()));
		return results;
	}
	
	/**
	 * This creates an ID Query
	 * @param id
	 * @return
	 */
	protected DBObject getIdQuery(Object id){
		return new BasicDBObject(BasicDBFields.ID, id);
	}
	
	/**
	 * This creates an ID Query Map
	 * @param id
	 * @return
	 */
	protected Map<String, Object> getIdMap(Object id){
		Map<String, Object> map = new LinkedHashMap<String, Object>(3);
		map.put(BasicDBFields.ID, id);
		return map;
	}
	
	/**
	 * Gets the number of document that got affected because of this {@linkplain WriteResult}
	 * @param result
	 * @return
	 */
	protected int getNumberOfDocumentAffected(WriteResult result){
		return result.getN();
	}
	
	/**
	 * This adds the Set operator to the Map Specified
	 * @param obj
	 * @return
	 */
	protected Map<String, Object> getSetSingleMap(Map<String, Object> obj){
		Map<String, Object> s = new LinkedHashMap<String, Object>(3);
		s.put(BasicMongoOperators.SET, obj);
		return s;
	}

}

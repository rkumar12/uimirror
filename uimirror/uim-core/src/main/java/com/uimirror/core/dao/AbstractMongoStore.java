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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import com.uimirror.core.BasicDBFields;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.mongo.BasicMongoOperators;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.mongo.feature.MongoDocumentSerializer;

/**
 * Basic and essential implementation for the {@linkplain BasicStore}
 * Make sure in case of query giving the proper {@linkplain Map} with key as one of 
 * the operators defined in {@linkplain BasicMongoOperators}
 * Make sure in case of update giving the proper {@linkplain Map} with key as one of 
 * the operators defined in {@linkplain BasicMongoOperators} leaving the top most document
 * as by default its taking {@linkplain BasicMongoOperators#SET} only for {@linkplain BasicStore#updateById(Object, MongoDocumentSerializer)}
 * else in case for the updateByQuery pass the updated {@linkplain Map<String, Object} as fully update filed
 * with proper operators from the {@linkplain BasicMongoOperators}
 * 
 * @author Jay
 */
public abstract class AbstractMongoStore<T extends BeanBasedDocument> extends MongoInitializer implements BasicStore<T>{

	protected static final Logger LOG = LoggerFactory.getLogger(AbstractMongoStore.class);
	private T t;
	/**
	 * Assign/ Create collection from the given {@link Mongo}
	 * @param mongo
	 * @param dbName
	 * @param collectionName
	 */
	public AbstractMongoStore(Mongo mongo, String dbName, String collectionName , Class<? extends BeanBasedDocument> claz){
		super(mongo, dbName, collectionName);
		setTargetClass(claz);
		
	}
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	public AbstractMongoStore(DBCollection collection, Class<? extends BeanBasedDocument> claz){
		super(collection);
		setTargetClass(claz);
	}
	
	/**
	 * @param db
	 * @param collectionName
	 */
	public AbstractMongoStore(DB db, String collectionName, Class<? extends BeanBasedDocument> claz) {
		super(db, collectionName);
		setTargetClass(claz);
	}
	
	/**
	 * Initialize the type of serialization of the class
	 * @param claz
	 */
	@SuppressWarnings("unchecked")
	private void setTargetClass(Class<? extends BeanBasedDocument> claz){
		try {
			t = (T) claz.getConstructor().newInstance(new Object[] { });
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException("Provided class can't be cast to desired document");
		}
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#store(com.uimirror.core.mongo.feature.MongoDocumentSerializer)
	 */
	@Override
	@MapException(use=MongoExceptionMapper.NAME)
	public void store(T doc) throws DBException {
		LOG.debug("[START]- Storing the object");
		Assert.notNull(doc, "Object To store can't be empty");
		Map<String, Object> document = doc.toMap();
		if(CollectionUtils.isEmpty(document))
			throw new IllegalArgumentException("Object To store can't be empty");
		getCollection().save(convertToDBObject(document));
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
		return getNumberOfDocumentAffected(delete(convertToDBObject(query)));
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
	public T getById(Object id) throws DBException {
		LOG.debug("[START]- Getting an object based on the ID");
		DBObject result = getCollection().findOne(getIdQuery(id));
		if(result == null){
			throw new RecordNotFoundException();
		}
		LOG.debug("[END]- Getting an object based on the ID");
		return (T) t.initFromMap(result.toMap());
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#getByQuery(java.lang.Object)
	 */
	@MapException(use=MongoExceptionMapper.NAME)
	@Override
	public List<T> getByQuery(Map<String, Object> query) throws DBException {
		LOG.debug("[START]- Getting an object based on the Query specified");
		DBCursor cursor = getCollection().find(convertToDBObject(query));
		LOG.debug("[END]- Getting an object based on the Query specified");
		return getFromCursor(cursor, 50);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#updateById(java.lang.Object, com.uimirror.core.mongo.feature.MongoDocumentSerializer)
	 */
	@Override
	public int updateById(Object id, Map<String, Object> toUpdate) throws DBException {
		LOG.debug("[SINGLE]- Updateing the document by ID");
		return updateByQuery(getIdMap(id), toUpdate);
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
		return getCollection().update(convertToDBObject(q), convertToDBObject(u));
	}
	
	/**
	 * Concerts the {@linkplain DBCursor} into list of {@linkplain T}
	 * @param cursor
	 * @param fetchSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getFromCursor(DBCursor cursor, int fetchSize){
		fetchSize = fetchSize <= 0 ? 20 : fetchSize;
		int thirtyPercentage = fetchSize*((30/100)*100);
		//Retrieve max of 20 at a time
		cursor.batchSize(fetchSize);
		List<T> results = new ArrayList<T>(fetchSize+(fetchSize+thirtyPercentage));
		cursor.forEach((result) -> results.add((T)result.toMap()));
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
//	protected Map<String, Object> getSetSingleMap(Map<String, Object> obj){
//		Map<String, Object> s = new LinkedHashMap<String, Object>(3);
//		s.put(BasicMongoOperators.SET, obj);
//		return s;
//	}
	
	/**
	 * Converts the provided map into {@link DBObject}
	 * @param obj
	 * @return
	 */
	protected DBObject convertToDBObject(Map<String, Object> obj){
		return new BasicDBObject(obj);
	}
	
	/**
	 * Query for the first record from the document.
	 * @param query
	 * @return
	 * @throws RecordNotFoundException
	 */
	protected T queryFirstRecord(Map<String, Object> query) throws RecordNotFoundException{
		List<T> results = getByQuery(query);
		if(CollectionUtils.isEmpty(results)){
			throw new RecordNotFoundException("No Record Found");
		}
		return results.get(0);
	}

}

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
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.mongo.BasicMongoOperators;
import com.uimirror.core.mongo.feature.BasicDBFields;
import com.uimirror.core.mongo.feature.BeanBasedDocument;

/**
 * Basic and essential implementation for the {@linkplain BasicStore}
 * Make sure in case of query giving the proper {@linkplain Map} with key as one of 
 * the operators defined in {@linkplain BasicMongoOperators}
 * Make sure in case of update giving the proper {@linkplain Map} with key as one of 
 * the operators defined in {@linkplain BasicMongoOperators} leaving the top most document
 * as by default its taking  only for {@linkplain BasicStore#updateById(Object, Map)}
 * else in case for the updateByQuery pass the updated as fully update filed
 * with proper operators from the {@linkplain BasicMongoOperators}
 * 
 * @author Jay
 */
public abstract class AbstractMongoStore<T extends BeanBasedDocument<T>> extends MongoInitializer implements BasicStore<T>{

	protected static final Logger LOG = LoggerFactory.getLogger(AbstractMongoStore.class);
	private T t;
	private String seqName;
	/**
	 * Assign/ Create collection from the given {@link Mongo}
	 * @param mongo
	 * @param dbName
	 * @param collectionName
	 * @param claz
	 */
	public AbstractMongoStore(Mongo mongo, String dbName, String collectionName , Class<? extends BeanBasedDocument<T>> claz){
		super(mongo, dbName, collectionName);
		setTargetClass(claz);
		
	}
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 * @param claz
	 */
	public AbstractMongoStore(DBCollection collection, Class<? extends BeanBasedDocument<T>> claz){
		super(collection);
		setTargetClass(claz);
	}
	
	/**
	 * @param db
	 * @param collectionName
	 * @param claz
	 */
	public AbstractMongoStore(DB db, String collectionName, Class<? extends BeanBasedDocument<T>> claz) {
		super(db, collectionName);
		setTargetClass(claz);
	}
	
	public AbstractMongoStore(DB db, String collectionName,
			String seqCollectionName, String seqName, Class<? extends BeanBasedDocument<T>> claz) {
		super(db, collectionName, seqCollectionName);
		setTargetClass(claz);
		this.seqName = seqName;
	}

	public AbstractMongoStore(DBCollection collection,
			DBCollection seqCollection, String seqName, Class<? extends BeanBasedDocument<T>> claz) {
		super(collection, seqCollection);
		setTargetClass(claz);
		this.seqName = seqName;
	}

	/**
	 * Initialize the type of serialization of the class
	 * @param claz
	 */
	@SuppressWarnings("unchecked")
	private void setTargetClass(Class<? extends BeanBasedDocument<T>> claz){
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
	public T store(T doc) throws DBException {
		LOG.debug("[START]- Storing the object");
		Assert.notNull(doc, "Object To store can't be empty");
		//Serialize the states
		Map<String, Object> document = doc.toMap();
		if(CollectionUtils.isEmpty(document))
			throw new IllegalArgumentException("Object To store can't be empty");
		
		//If ID null and Sequence Name present then put the sequence name as well 
		if(!StringUtils.hasText(doc.getId()) && getSeqName() != null)
			document.put(BasicDBFields.ID, getNextSequence());
		
		getCollection().save(convertToDBObject(document));
		
		LOG.debug("[END]- Storing the object");
		LOG.debug("[START]- Create Index");
		ensureIndex();
		LOG.debug("[END]- Create Index");
		return StringUtils.hasText(doc.getId()) ? doc : t.initFromMap(document);
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
	 * @return {@link WriteResult}
	 */
	protected WriteResult delete(DBObject obj){
		return getCollection().remove(obj);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#getById(java.lang.Object)
	 */
	@Override
	public T getById(Object id) throws DBException {
		return getById(id, null);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#getByQuery(java.lang.Object)
	 */
	@Override
	public List<T> getByQuery(Map<String, Object> query) throws DBException {
		return getByQuery(query, null);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#getById(java.lang.Object, java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@MapException(use=MongoExceptionMapper.NAME)
	public T getById(Object id, Map<String, Object> fields) throws DBException {
		LOG.debug("[START]- Getting an object based on the ID");
		DBObject result = getCollection().findOne(getIdQuery(id), convertToDBObject(fields));
		if(result == null){
			throw new RecordNotFoundException();
		}
		LOG.debug("[END]- Getting an object based on the ID");
		return (T) t.initFromMap(result.toMap());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#getByQuery(java.util.Map, java.util.Map)
	 */
	@Override
	@MapException(use=MongoExceptionMapper.NAME)
	public List<T> getByQuery(Map<String, Object> query, Map<String, Object> fields) throws DBException {
		LOG.debug("[START]- Getting an object based on the Query specified");
		DBCursor cursor = getCollection().find(convertToDBObject(query), convertToDBObject(fields));
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
	public int updateByQuery(Map<String, Object> query, Map<String, Object> toUpdate) throws DBException {
		return getNumberOfDocumentAffected(update(query, toUpdate, Boolean.FALSE, Boolean.FALSE));
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#updateByIdInsertWhenNoMatchWithMulti(java.lang.Object, java.util.Map)
	 */
	@Override
	public int updateByIdInsertWhenNoMatchWithMulti(Object id, Map<String, Object> toUpdate) throws DBException {
		return getNumberOfDocumentAffected(update(getIdMap(id), toUpdate, Boolean.TRUE, Boolean.TRUE));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#updateByIdInsertWhenNoMatchWithOutMulti(java.lang.Object, java.util.Map)
	 */
	@Override
	public int updateByIdInsertWhenNoMatchWithOutMulti(Object id, Map<String, Object> toUpdate) throws DBException {
		return getNumberOfDocumentAffected(update(getIdMap(id), toUpdate, Boolean.TRUE, Boolean.FALSE));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#updateByQueryInsertWhenNoMatchWithMulti(java.util.Map, java.util.Map)
	 */
	@Override
	public int updateByQueryInsertWhenNoMatchWithMulti(Map<String, Object> query, Map<String, Object> toUpdate) throws DBException {
		return getNumberOfDocumentAffected(update(query, toUpdate, Boolean.TRUE, Boolean.TRUE));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#updateByQueryInsertWhenNoMatchWithOutMulti(java.util.Map, java.util.Map)
	 */
	@Override
	public int updateByQueryInsertWhenNoMatchWithOutMulti(Map<String, Object> query, Map<String, Object> toUpdate) throws DBException {
		return getNumberOfDocumentAffected(update(query, toUpdate, Boolean.TRUE, Boolean.FALSE));
	}
	
	/**
	 * Updates to the document
	 * @param q query
	 * @param u update statment
	 * @param nomatchInsert if no match then create a document
	 * @param multi if multi needs to be inserted
	 * @return no of results write to DB
	 */
	@MapException(use=MongoExceptionMapper.NAME)
	protected WriteResult update(Map<String, Object> q, Map<String, Object> u, boolean nomatchInsert, boolean multi){
		return getCollection().update(convertToDBObject(q), convertToDBObject(u), nomatchInsert, multi);
	}
	
	/**
	 * Concerts the {@linkplain DBCursor} into list of {@linkplain T}
	 * @param cursor document cursor
	 * @param fetchSize size to which it will fetched at a time
	 * @return {@link List} of Object
	 */
	@SuppressWarnings("unchecked")
	public List<T> getFromCursor(DBCursor cursor, int fetchSize){
		fetchSize = fetchSize <= 0 ? 20 : fetchSize;
		int thirtyPercentage = fetchSize*((30/100)*100);
		//Retrieve max of 20 at a time
		cursor.batchSize(fetchSize);
		List<T> results = new ArrayList<T>(fetchSize+(fetchSize+thirtyPercentage));
		cursor.forEach((result) -> {
			results.add((T)t.initFromMap(result.toMap()));
		});
		if(CollectionUtils.isEmpty(results)){
			throw new RecordNotFoundException("No Record Found");
		}
		return results;
		//return null;
	}
	
	/**
	 * This creates an ID Query
	 * @param id for which document will be qured
	 * @return formated query
	 */
	protected DBObject getIdQuery(Object id){
		return new BasicDBObject(BasicDBFields.ID, id);
	}
	
	/**
	 * This creates an ID Query Map
	 * @param id object id
	 * @return {@link Map}
	 */
	protected Map<String, Object> getIdMap(Object id){
		Map<String, Object> map = new LinkedHashMap<String, Object>(3);
		map.put(BasicDBFields.ID, id);
		return map;
	}
	
	/**
	 * Gets the number of document that got affected because of this {@linkplain WriteResult}
	 * @param result write concerns
	 * @return number of record
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
	 * @param obj object to be serialized
	 * @return {@link DBObject}
	 */
	protected DBObject convertToDBObject(Map<String, Object> obj){
		DBObject dbObject = null;
		if(CollectionUtils.isEmpty(obj))
			dbObject = new BasicDBObject();
		else
			dbObject = new BasicDBObject(obj);
		return dbObject;
	}
	
	/**
	 * Query for the first record from the document.
	 * @param query query 
	 * @return found object
	 * @throws RecordNotFoundException if no record
	 */
	protected T queryFirstRecord(Map<String, Object> query) throws RecordNotFoundException{
		List<T> results = getByQuery(query);
		return results.get(0);
	}
	
	/**
	 * Query for the first record from the document.
	 * @param query query
	 * @param fields fileds
	 * @return state of the object
	 * @throws RecordNotFoundException exception once object not found
	 */
	protected T queryFirstRecord(Map<String, Object> query, Map<String, Object> fields) throws RecordNotFoundException{
		List<T> results = getByQuery(query, fields);
		return results.get(0);
	}
	
	/**
	 * Should have give the implementation to make sure, document has the enough index
	 */
	protected abstract void ensureIndex();
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#getNextSequence(java.lang.String)
	 */
	public String getNextSequence() {
		if(getSeqName() == null)
			return null;
		// this object represents your "query", its analogous to a WHERE clause in SQL
	    DBObject query = new BasicDBObject(1);
	    query.put(BasicDBFields.ID, seqName); // where _id = the input sequence name
	 
	    // this object represents the "update" or the SET blah=blah in SQL
	    DBObject change = new BasicDBObject("seq", 1);
	    DBObject update = new BasicDBObject(BasicMongoOperators.INCREAMENT, change); // the $inc here is a mongodb command for increment
	 
	    // Atomically updates the sequence field and returns the value for you
	    DBObject res = getSeqCollection().findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
	    return res.get("seq").toString();
	}
	
	private String getSeqName(){
		return seqName;
	}
	
	/**
	 * Gets the exists map query
	 * @param flag exist field 
	 * @return {@link Map} of the query
	 */
	public Map<String, Object> getExistQuery( boolean flag){
		Map<String, Object> query = new LinkedHashMap<String, Object>(5);
		query.put(BasicMongoOperators.EXISTS, flag);
		return query;
	}

}

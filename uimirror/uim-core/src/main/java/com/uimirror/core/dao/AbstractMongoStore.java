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
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.mongo.BasicMongoOperators;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.mongo.feature.BasicDBFields;
import com.uimirror.core.mongo.feature.MongoDocumentSerializer;

/**
 * Basic and essential implementation for the {@linkplain BasicStore}
 * 
 * Which abstracts the definition for basic CRUD operation with MONGO store
 * 
 * @author Jay
 */
public abstract class AbstractMongoStore<T extends MongoDocumentSerializer<T>> implements BasicStore<T>{

	protected static final Logger LOG = LoggerFactory.getLogger(AbstractMongoStore.class);
	private T t;
	private final String seqName;
	private final MongoInitializer initializer;

	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection where doucment will be stored
	 * @param claz destination class which will participate in seralization and deserailization
	 */
	public AbstractMongoStore(DBCollection collection, Class<? extends AbstractBeanBasedDocument<T>> claz){
		this.seqName = null;
		this.initializer = new MongoInitializer(collection) ;
		setTargetClass(claz);
	}

	public AbstractMongoStore(DBCollection collection, DBCollection seqCollection, String seqName, Class<? extends AbstractBeanBasedDocument<T>> claz) {
		this.seqName = seqName;
		this.initializer = new MongoInitializer(collection, seqCollection) ;
		setTargetClass(claz);
	}

	/**
	 * Initialize the type of serialization of the class
	 * @param claz class for which store is getting initialized
	 */
	@SuppressWarnings("unchecked")
	private void setTargetClass(Class<? extends MongoDocumentSerializer<T>> claz){
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
		//Serailize
		Map<String, Object> document = seralize(doc);
		getCollection().save(MongoStoreHelper.convertToDBObject(document));
		LOG.debug("[END]- Storing the object");
		LOG.debug("[START]- Create Index");
		ensureIndex();
		LOG.debug("[END]- Create Index");
		return StringUtils.hasText(doc.getId()) ? doc : doc.updateId((String)document.get(BasicDBFields.ID));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#deleteById(java.lang.Object)
	 */
	@Override
	@MapException(use=MongoExceptionMapper.NAME)
	public void deleteById(Object id) throws DBException {
		LOG.debug("[SINGLE]- Deleting the object");
		delete(MongoStoreHelper.getIdQuery(id));
	}
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#deleteByQuery(java.util.Map)
	 */
	@Override
	@MapException(use=MongoExceptionMapper.NAME)
	public int deleteByQuery(Map<String, Object> query) throws DBException {
		LOG.debug("[SINGLE]- Deleting the objects based on the query");
		return getNumberOfDocumentAffected(delete(MongoStoreHelper.convertToDBObject(query)));
	}
	
	/**
	 * Delete the document based on the object query specified,
	 * If empty, then whole documents in the collection.
	 * 
	 * @param obj which will be deleted, basically query string
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
		DBObject result = getCollection().findOne(MongoStoreHelper.getIdQuery(id), MongoStoreHelper.convertToDBObject(fields));
		if(result == null){
			throw new RecordNotFoundException();
		}
		LOG.debug("[END]- Getting an object based on the ID");
		return (T) t.readFromMap(result.toMap());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#getByQuery(java.util.Map, java.util.Map)
	 */
	@Override
	@MapException(use=MongoExceptionMapper.NAME)
	public List<T> getByQuery(Map<String, Object> query, Map<String, Object> fields) throws DBException {
		LOG.debug("[START]- Getting an object based on the Query specified");
		DBCursor cursor = getCollection().find(MongoStoreHelper.convertToDBObject(query), MongoStoreHelper.convertToDBObject(fields));
		LOG.debug("[END]- Getting an object based on the Query specified");
		return getFromCursor(cursor, 50);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#updateById(java.lang.Object, com.uimirror.core.mongo.feature.MongoDocumentSerializer)
	 */
	@Override
	public int updateById(Object id, Map<String, Object> toUpdate) throws DBException {
		LOG.debug("[SINGLE]- Updateing the document by ID");
		return updateByQuery(MongoStoreHelper.getIdMap(id), toUpdate);
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
		return getNumberOfDocumentAffected(update(MongoStoreHelper.getIdMap(id), toUpdate, Boolean.TRUE, Boolean.TRUE));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#updateByIdInsertWhenNoMatchWithOutMulti(java.lang.Object, java.util.Map)
	 */
	@Override
	public int updateByIdInsertWhenNoMatchWithOutMulti(Object id, Map<String, Object> toUpdate) throws DBException {
		return getNumberOfDocumentAffected(update(MongoStoreHelper.getIdMap(id), toUpdate, Boolean.TRUE, Boolean.FALSE));
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
		return getCollection().update(MongoStoreHelper.convertToDBObject(q), MongoStoreHelper.convertToDBObject(u), nomatchInsert, multi);
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
			results.add((T)t.readFromMap(result.toMap()));
		});
		if(CollectionUtils.isEmpty(results)){
			throw new RecordNotFoundException("No Record Found");
		}
		return results;
		//return null;
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
	
	/**
	 * Returns the sequence name being used
	 * @return sequence name
	 */
	protected String getSeqName(){
		return seqName;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#getCollection()
	 */
	public DBCollection getCollection(){
		return initializer.getCollection();
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.BasicStore#getSeqCollection()
	 */
	public DBCollection getSeqCollection(){
		return initializer.getSeqCollection();
	}
	
	/**
	 * This will serialize the document into {@link Map} and populate id , if not present
	 * @param doc which will be persisted
	 * @return map of the document
	 */
	private Map<String, Object> seralize(T doc) {
		//Serialize the states
		Map<String, Object> document = doc.writeToMap();
		if(CollectionUtils.isEmpty(document))
			throw new IllegalArgumentException("Object To store can't be empty");
				
		//If ID null and Sequence Name present then put the sequence name as well 
		if(!StringUtils.hasText(doc.getId())){
			if(getSeqName() != null)
				document.put(BasicDBFields.ID, getNextSequence());
			else
				throw new IllegalStateException("document to persist is couropted");
		}
		return document;
	}

}

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

import java.util.List;
import java.util.Map;

import com.mongodb.DBCollection;
import com.uimirror.core.mongo.feature.MongoDocumentSerializer;

/**
 * This is a basic MONGO store contract definition for the basic CRUD operations,
 * 
 * @author Jay
 */
public interface BasicStore<T extends MongoDocumentSerializer<T>> {
	
	/**
	 * Gets the Sequence for the document
	 * @return next sequence
	 */
	String getNextSequence();

	/**
	 * Stores the document by calling 
	 * {@linkplain MongoDocumentSerializer#writeToMap()}
	 * @param doc document to be persisted
	 * @return T bean object
	 * @throws DBException in case of any exception
	 */
	T store(T doc) throws DBException;
	
	/**
	 * Defines the contract of the delete by ID
	 * @param id object which will be deleted
	 * @throws DBException in case of any exception
	 */
	void deleteById(Object id) throws DBException;
	
	/**
	 * Delete the documents based on the query specified.
	 * If you want to remove all the dcoument pass a empty object
	 * @param query query on which basics it will be deleted
	 * @return number of the record deleted
	 * @throws DBException exception in case of delete
	 */
	int deleteByQuery(Map<String, Object> query) throws DBException;
	
	/**
	 * Get the document by ID
	 * @param id Document ID
	 * @return the object found by the id
	 * @throws DBException in case of any exception
	 */
	T getById(Object id) throws DBException;
	
	/**
	 * Get the document by ID
	 * @param id Document ID
	 * @param fields to get projected
	 * @return the object found by the id
	 * @throws DBException in case of any exception
	 */
	T getById(Object id, Map<String, Object> fields) throws DBException;
	
	/**
	 * Get the document by the query specified
	 * If the query is null, it will get all the documents
	 * @param query fields to match the coditions
	 * @return {@link List} of the object found by the id
	 * @throws DBException in case of any exception
	 */
	List<T> getByQuery(Map<String, Object> query) throws DBException;
	
	/**
	 * Get the document by the query specified
	 * If the query is null, it will get all the documents
	 * @param query fields to select
	 * @param fields projection to be retrieved
	 * @return {@link List} of the object found by the id
	 * @throws DBException in case of any exception
	 */
	List<T> getByQuery(Map<String, Object> query, Map<String, Object> fields) throws DBException;
	
	/**
	 * Updates the document on the available specific id
	 * The update map should have the proper $SET commands
	 * @param id document ID
	 * @param toUpdate fields to be updated
	 * @return number of record got updated
	 * @throws DBException in case of any exception
	 */
	int updateById(Object id, Map<String, Object> toUpdate) throws DBException;
	
	/**
	 * Updates the document on the available specific id
	 * The update map should have the proper $SET commands
	 * This will insert when no match found with multi option as true.
	 * 
	 * @param id document ID
	 * @param toUpdate fields to be get updated
	 * @return number of record got updated
	 * @throws DBException  in case of any exception
	 */
	int updateByIdInsertWhenNoMatchWithMulti(Object id, Map<String, Object> toUpdate) throws DBException;
	/**
	 * Updates the document on the available specific id
	 * The update map should have the proper $SET commands
	 * This will insert when no match found with multi option as false.
	 * 
	 * @param id document ID
	 * @param toUpdate fields to be updated
	 * @return number of record got updated
	 * @throws DBException  in case of any exception
	 */
	int updateByIdInsertWhenNoMatchWithOutMulti(Object id, Map<String, Object> toUpdate) throws DBException;
	
	/**
	 * Update the document based on the query specified.
	 * The update map should have the proper $SET commands
	 * @param query to find the documents
	 * @param toUpdate fields to be updated
	 * @return number of record got affected
	 * @throws DBException  in case of any exception
	 */
	int updateByQuery(Map<String, Object> query, Map<String, Object> toUpdate) throws DBException;
	
	/**
	 * Update the document based on the query specified.
	 * The update map should have the proper $SET commands
	 * This will insert when no match found with multi option as true.
	 * @param query based on which document will be retrieved
	 * @param toUpdate update fields
	 * @return number of row got updated
	 * @throws DBException in case of any exception
	 */
	int updateByQueryInsertWhenNoMatchWithMulti(Map<String, Object> query, Map<String, Object> toUpdate) throws DBException;
	/**
	 * Update the document based on the query specified.
	 * The update map should have the proper $SET commands
	 * This will insert when no match found with multi option as false.
	 * @param query to be updated
	 * @param toUpdate values to be updated
	 * @return number of record updated
	 * @throws DBException in case of any exception
	 */
	int updateByQueryInsertWhenNoMatchWithOutMulti(Map<String, Object> query, Map<String, Object> toUpdate) throws DBException;
	
	/**
	 * This find and modify the document and return the new updated document.
	 * in case no records found or updated, returns null
	 * @param query based on which document needs to be retrieved
	 * @param fields for projection
	 * @param toUpdate value that needs to be updated
	 * @return new updated document
	 * @throws DBException
	 */
	T findAndModify(Map<String, Object> query, Map<String, Object> fields, Map<String, Object> toUpdate) throws DBException;
	
	/**
	 * This find and modify the document and return the new updated document.
	 * This doesn't have projection fields so use it for minimal field operation.
	 * in case no records found or updated, returns null
	 * @param query based on which document needs to be retrieved
	 * @param toUpdate value that needs to be updated
	 * @return new updated document
	 * @throws DBException
	 */
	T findAndModify(Map<String, Object> query, Map<String, Object> toUpdate) throws DBException;
	
	/**Gets the collection being used for this store
	 * @return where document will be persisted.
	 */
	DBCollection getCollection();
	
	/**
	 * gets the sequence collection being used for this store
	 * @return where sequence numbers will be persisted.
	 */
	DBCollection getSeqCollection();
	
}

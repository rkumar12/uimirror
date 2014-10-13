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

import com.uimirror.core.mongo.BasicMongoOperators;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.mongo.feature.MongoDocumentSerializer;

/**
 * This is a basic store definition,
 * every store should have to give this definition for the
 * storing, getting by id & delete by id
 * Make sure in case of query giving the proper {@linkplain Map} with key as one of 
 * the operators defined in {@linkplain BasicMongoOperators}
 * Make sure in case of update giving the proper {@linkplain Map} with key as one of 
 * the operators defined in {@linkplain BasicMongoOperators} leaving the top most document
 * as by default its taking {@linkplain BasicMongoOperators#SET}
 * 
 * @author Jay
 */
public interface BasicStore<T extends BeanBasedDocument> {

	/**
	 * Stores the document by calling 
	 * {@linkplain MongoDocumentSerializer#toMap()}
	 * @param doc
	 */
	void store(T doc) throws DBException;
	
	/**
	 * Defines the contract of the delete by ID
	 * @param id
	 */
	void deleteById(Object id) throws DBException;
	
	/**
	 * Delete the documents based on the query specified.
	 * If you want to remove all the dcoument pass a empty object
	 * @param query
	 * @return
	 * @throws DBException
	 */
	int deleteByQuery(Map<String, Object> query) throws DBException;
	
	/**
	 * Get the document by ID
	 * @param id
	 * @return
	 */
	T getById(Object id) throws DBException;
	
	/**
	 * Get the document by the query specified
	 * If the query is null, it will get all the documents
	 * @param query
	 * @return
	 */
	List<T> getByQuery(Map<String, Object> query) throws DBException;
	
	/**
	 * Updates the document on the available specific id
	 * The update map should have the proper $SET commands
	 * @param id
	 * @param toUpdate
	 * @return number of record got updated
	 */
	int updateById(Object id, Map<String, Object> toUpdate) throws DBException;
	
	/**
	 * Update the document based on the query specified.
	 * The update map should have the proper $SET commands
	 * @param query
	 * @param toUpdate
	 * @return
	 * @throws DBException
	 */
	int updateByQuery(Map<String, Object> query, Map<String, Object> toUpdate) throws DBException;
	
}

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
package com.uimirror.core.store;

import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.util.CollectionUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.uimirror.core.mongo.BasicMongoOperators;
import com.uimirror.core.mongo.feature.BasicDBFields;

/**
 * A query helper for the basic queries
 * @author Jay
 */
public class MongoStoreHelper {

	/**
	 * Gets the exists map query
	 * @param flag exist field 
	 * @return {@link Map} of the query
	 */
	public static Map<String, Object> getExistQuery( boolean flag){
		Map<String, Object> query = new WeakHashMap<String, Object>(5);
		query.put(BasicMongoOperators.EXISTS, flag);
		return query;
	}
	
	/**
	 * Converts the provided map into {@link DBObject}
	 * @param obj object to be serialized
	 * @return {@link DBObject}
	 */
	public static DBObject convertToDBObject(Map<String, Object> obj){
		DBObject dbObject = null;
		if(CollectionUtils.isEmpty(obj))
			dbObject = new BasicDBObject();
		else
			dbObject = new BasicDBObject(obj);
		return dbObject;
	}
	
	
	/**
	 * This creates an ID Query
	 * @param id for which document will be qured
	 * @return formated query
	 */
	public static DBObject getIdQuery(Object id){
		return new BasicDBObject(BasicDBFields.ID, id);
	}
	
	/**
	 * This creates an ID Query Map
	 * @param id object id
	 * @return {@link Map}
	 */
	public static Map<String, Object> getIdMap(Object id){
		Map<String, Object> map = new WeakHashMap<String, Object>(3);
		map.put(BasicDBFields.ID, id);
		return map;
	}

}

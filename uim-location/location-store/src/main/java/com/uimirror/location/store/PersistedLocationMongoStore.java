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
package com.uimirror.location.store;

import java.util.Map;
import java.util.WeakHashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uimirror.core.GeoGsonType;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.mongo.BasicMongoOperators;
import com.uimirror.core.mongo.feature.LocationDBField;
import com.uimirror.location.Country;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.LocationDBFields;

/**
 * A MONGO Persisted implementation for the {@link Country}
 * @author Jay
 */
public class PersistedLocationMongoStore extends AbstractMongoStore<DefaultLocation> implements LocationStore{

	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	public PersistedLocationMongoStore(DBCollection collection, DBCollection seqCollection){
		super(collection, seqCollection, LOCATION_SEQ, DefaultLocation.class);
	}
	
	@Override
	public DefaultLocation getByLocationId(String loc_id) {
		return getById(loc_id);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.location.store.LocationStore#getByCord(double, double)
	 */
	@Override
	public DefaultLocation getByCord(double longitude, double latitude) {
		Map<String, Object> query = new WeakHashMap<String, Object>();
		query.put(LocationDBField.LOCATION, getLocQueryNearZeroMeter(longitude, latitude));
		return queryFirstRecord(query);
	}
	
	/**
	 * This will get the location near by zero meter of given longitude and latitude.
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	private Map<String, Object> getLocQueryNearZeroMeter(double longitude, double latitude){
		Map<String, Object> nearQuery = new WeakHashMap<String, Object>(5);
		Map<String, Object> geometeryMap = getGeometryQuery(longitude, latitude);
		geometeryMap.put(BasicMongoOperators.MIN_DISTANCE, 0);
		geometeryMap.put(BasicMongoOperators.MAX_DISTANCE, 0);
		nearQuery.put(BasicMongoOperators.NEAR, geometeryMap);
		return nearQuery;
	}
	
	/**
	 * This will get the location point.
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	private Map<String, Object> getGeometryQuery(double longitude, double latitude){
		Map<String, Object> geometeryMap = new WeakHashMap<String, Object>(5); 
		Map<String, Object> query = getCordQueryMap(longitude, latitude);
		query.put(LocationDBField.TYPE, GeoGsonType.POINT);
		geometeryMap.put(BasicMongoOperators.GEOMETERY, query);
		return geometeryMap;
	}
	
	/**
	 * Gets the Cordinate Map
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	private Map<String, Object> getCordQueryMap(double longitude, double latitude){
		Map<String, Object> query = new WeakHashMap<String, Object>(3);
		query.put(LocationDBField.COORDINATES, new double[]{longitude, latitude});
		return query;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		indexOnName();
		indexOnCord();
	}
	
	/**
	 * Creates index on name
	 */
	private void indexOnName(){
		DBObject name = new BasicDBObject(LocationDBFields.NAME, 1);
		getCollection().createIndex(name);
	}
	
	private void indexOnCord(){
		DBObject name = new BasicDBObject(LocationDBField.LOCATION, BasicMongoOperators.GEO_2SPEHERE_INDEX);
		getCollection().createIndex(name);
	}
	
}

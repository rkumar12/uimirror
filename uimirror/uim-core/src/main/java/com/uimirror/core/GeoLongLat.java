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
package com.uimirror.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.uimirror.core.mongo.feature.LocationDBField;
import com.uimirror.core.rest.extra.IllegalArgumentException;

/**
 * Stores the Longitude and latitude for the location coordinate.
 * It stors the location name as well
 * below is the format:
 * loc : { type: "Point", coordinates: [ -73.97, 40.77 ] },
      name: "Central Park",
 * 
 * @author Jay
 */
public class GeoLongLat{

	private String location;
	private double longitude;
	private double latitude;
	private String type;

	/**
	 * Converts the location details in to a serialize  map that will be stored in the DB
	 * @return
	 */
	public Map<String, Object> toGeoCordMap(){
		Map<String, Object> map = new LinkedHashMap<String, Object>(6);
		map.put(LocationDBField.TYPE, getType());
		double[] cord = new double[2];
		cord[0] = longitude;
		cord[1] = latitude;
		map.put(LocationDBField.COORDINATES, cord);
		Map<String, Object> locMap = new LinkedHashMap<String, Object>(6);
		locMap.put(LocationDBField.LOCATION, map);
		locMap.put(LocationDBField.LOCATION_NAME, location);
		return locMap;
	}

	/**
	 * Converts the serialized map to the bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static GeoLongLat initFromGeoCordMap(Map<String, Object> map){
		Map<String, Object> locMap = (Map<String, Object>)map.get(LocationDBField.LOCATION);
		String locaName = (String) map.get(LocationDBField.LOCATION_NAME);
		if(locMap == null || CollectionUtils.isEmpty(locMap))
			throw new IllegalArgumentException("Can't deserailze the location");
		
		double[] cord = null;
		if(locMap.get(LocationDBField.COORDINATES) != null){
			cord = (double[]) map.get(LocationDBField.COORDINATES);
		}
		if(cord.length < 2)
			throw new IllegalStateException("Location Cordinates are invalid");
		String type = (String)map.get(LocationDBField.TYPE);
		return new GeoLongLatBuilder(locaName).updateLongitude(cord[0]).updateLatitude(cord[1]).updatePointTypee(type).build();
	}

	/**
	 * Return distance between this location and that location
	 * measured in statute miles
	 * @param that
	 * @return
	 */
	public double distanceTo(GeoLongLat that) {
		double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
		double lat1 = Math.toRadians(this.latitude);
		double lon1 = Math.toRadians(this.longitude);
		double lat2 = Math.toRadians(that.latitude);
		double lon2 = Math.toRadians(that.longitude);

		// great circle distance in radians, using law of cosines formula
		double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

		// each degree on a great circle of Earth is 60 nautical miles
		double nauticalMiles = 60 * Math.toDegrees(angle);
		double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
		return statuteMiles;
	}


	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public String getLocation() {
		return location;
	}
	public String getType() {
		return type;
	}
	
	public static class GeoLongLatBuilder implements Builder<GeoLongLat>{

		private String location;
		private double longitude;
		private double latitude;
		private String type = GeoGsonType.POINT;
		
		public GeoLongLatBuilder(String location){
			this.location = location;
		}
		
		public GeoLongLatBuilder updateLongitude(double longitude){
			this.longitude = longitude;
			return this;
		}
		
		public GeoLongLatBuilder updateLatitude(double latitude){
			this.latitude = latitude;
			return this;
		}
		public GeoLongLatBuilder updatePointTypee(String type){
			Assert.hasText(type, "Location Cordinate Type should present.");
			this.type = type;
			return this;
		}
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public GeoLongLat build() {
			return new GeoLongLat(this);
		}
		
	}
	
	/**
	 * Build state from a builder
	 * @param builder
	 */
	private GeoLongLat(GeoLongLatBuilder builder){
		this.location = builder.location;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.type = builder.type;
	}

}

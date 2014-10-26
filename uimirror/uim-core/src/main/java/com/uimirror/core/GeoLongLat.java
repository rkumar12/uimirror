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

import com.uimirror.core.mongo.feature.LocationDBField;

/**
 * Stores the Longitude and latitude for the location coordinate.
 * 
 * @author Jay
 */
public class GeoLongLat {

	private String location;
	private double longitude;
	private double latitude;
	private String type;

	/**
	 * Create and initialize a point with given name and (latitude, longitude)
	 * specified in degrees
	 * 
	 * @param longitude
	 * @param latitude
	 */
	public GeoLongLat(String loc, double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.location = loc;
		this.type = GeoGsonType.POINT;
	}
	/**
	 * Create and initialize a point with given name and (latitude, longitude)
	 * specified in degrees
	 * 
	 * @param longitude
	 * @param latitude
	 * @param type
	 */
	public GeoLongLat(String loc, double longitude, double latitude, String type) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.location = loc;
		this.type = type;
	}
	
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
		return map;
	}

	/**
	 * Converts the serialized map to the bean
	 * @return
	 */
	public static GeoLongLat initFromGeoCordMap(Map<String, Object> map){
		double[] cord = null;
		if(map.get(LocationDBField.COORDINATES) != null){
			cord = (double[]) map.get(LocationDBField.COORDINATES);
		}
		if(cord.length < 2)
			throw new IllegalStateException("Location Cordinates are invalid");
		String type = (String)map.get(LocationDBField.TYPE);
		return new GeoLongLat(null, cord[0], cord[1], type);
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

}

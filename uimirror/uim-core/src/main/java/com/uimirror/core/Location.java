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


/**
 * Gets the DOB of the user
 * @author Jay
 */
public class Location {

	private String name;
	private double longitude;
	private double latitude;
	
	/**
	 * Create and initialize a point with given name and 
	 * (latitude, longitude) specified in degrees
	 * 
	 * @param name
	 * @param longitude
	 * @param latitude
	 */
	public Location(String name, double longitude, double latitude) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public double distanceTo(Location that){
		return 0;
	}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }
//  
//  /*******************************************************************************
//   * Copyright (c) 2014 Uimirror.
//   * All rights reserved. This program and the accompanying materials
//   * are made available under the terms of the Uimirror license
//   * which accompanies this distribution, and is available at
//   * http://www.uimirror.com/legal
//   *
//   * Contributors:
//   * Uimirror Team
//   *******************************************************************************/
//  package com.uimirror.core;
//
//  import java.util.LinkedHashMap;
//  import java.util.Map;
//
//  import org.springframework.beans.factory.annotation.Autowired;
//  import org.springframework.util.CollectionUtils;
//
//
//  /**
//   * Gets the DOB of the user
//   * @author Jay
//   */
//  public class Location {
//
//  	private String name;
//  	private double longitude;
//  	private double latitude;
//  	
//  	/**
//  	 * Create and initialize a point with given name and 
//  	 * (latitude, longitude) specified in degrees
//  	 * 
//  	 * @param name
//  	 * @param longitude
//  	 * @param latitude
//  	 */
//  	public Location(String name, double longitude, double latitude) {
//  		super();
//  		this.name = name;
//  		this.longitude = longitude;
//  		this.latitude = latitude;
//  	}
//  	
//  	 // return distance between this location and that location
//      // measured in statute miles
//      public double distanceTo(Location that) {
//          double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
//          double lat1 = Math.toRadians(this.latitude);
//          double lon1 = Math.toRadians(this.longitude);
//          double lat2 = Math.toRadians(that.latitude);
//          double lon2 = Math.toRadians(that.longitude);
//
//          // great circle distance in radians, using law of cosines formula
//          double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
//                                 + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
//
//          // each degree on a great circle of Earth is 60 nautical miles
//          double nauticalMiles = 60 * Math.toDegrees(angle);
//          double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
//          return statuteMiles;
//      }
//      
//      /**
//  	 * Get the map for dob
//  	 * @return
//  	 */
//  	public Map<String, Object> toMap(){
//  		Map<String, Object> map = new LinkedHashMap<String, Object>(8);
//  		//TODO conisder this task first
////  		map.put(UserDBFields.DATE_OF_BIRTH_DATE, getDate());
////  		map.put(UserDBFields.DATE_OF_BIRTH_MONTH, getMonth());
////  		map.put(UserDBFields.DATE_OF_BIRTH_YEAR, getYear());
//  		return map;
//  	}
//
//  	/**
//  	 * Get the map for dob
//  	 * @return
//  	 */
//  	public static Location initFromMap(Map<String, Object> map){
//  		if(CollectionUtils.isEmpty(map))
//  			return null;
//  		return null;
//  	}
//      
//      // return string representation of this point
//      @Autowired
//      public String toString() {
//          return name + " (" + latitude + ", " + longitude + ")";
//      }
//
//  	public String getName() {
//  		return name;
//  	}
//
//  	public double getLongitude() {
//  		return longitude;
//  	}
//
//  	public double getLatitude() {
//  		return latitude;
//  	}
//
//  }

	
	
	
	

}

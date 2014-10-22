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
	
	
	
	

}

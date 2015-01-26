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

import com.uimirror.core.mongo.feature.BasicDBFields;

/**
 * @author Jay
 */
public class LocationDBField extends BasicDBFields{
	//Prevent creating instance
	private LocationDBField(){
		//NOP
	}

	public static final String TYPE = "type";
	public static final String LATITUDE = "lat";
	public static final String LONGITUDE = "lng";
	public static final String COORDINATES = "coordinates";
	public static final String LOCATION = "loc";
	public static final String LOCATION_NAME = "loc_name";
}

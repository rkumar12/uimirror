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
 * Stores the different type of the GeoJson types supported currently
 * @author Jay
 */
public class GeoGsonType {
	
	//Prevent from instantiate
	private GeoGsonType(){
		//NOP
	}
	public static final String POINT = "Point";
	public static final String LINESTRING = "LineString";
	public static final String POLYGON = "Polygon";
	public static final String MULTIPOINT = "MultiPoint";
	public static final String MULTILINESTRING = "MultiLineString";
	public static final String MULTIPOLYGON = "MultiPolygon";
	public static final String GEOMETRYCOLLECTION = "GeometryCollection";

}

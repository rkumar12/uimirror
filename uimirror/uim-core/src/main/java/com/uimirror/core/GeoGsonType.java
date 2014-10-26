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
public interface GeoGsonType {
	String POINT = "Point";
	String LINESTRING = "LineString";
	String POLYGON = "Polygon";
	String MULTIPOINT = "MultiPoint";
	String MULTILINESTRING = "MultiLineString";
	String MULTIPOLYGON = "MultiPolygon";
	String GEOMETRYCOLLECTION = "GeometryCollection";

}

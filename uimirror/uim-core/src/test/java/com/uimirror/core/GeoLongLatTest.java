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

import static com.uimirror.core.LocationDBField.COORDINATES;
import static com.uimirror.core.LocationDBField.LOCATION;
import static com.uimirror.core.LocationDBField.LOCATION_NAME;
import static com.uimirror.core.LocationDBField.TYPE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jay
 */
public class GeoLongLatTest {

	private GeoLongLat geo;
	private Map<String, Object> seralized;
	
	@Before
	public void setUp(){
		geo = new GeoLongLat.GeoLongLatBuilder("Marathahali").updateLatitude(1.0d).updateLongitude(1.1d).updatePointTypee(GeoGsonType.POINT).build();
		Map<String, Object> map = new WeakHashMap<String, Object>(6);
		map.put(TYPE, GeoGsonType.POINT);
		List<Double> cords = new ArrayList<Double>(4);
		cords.add(1.1d);
		cords.add(1.0d);
		map.put(COORDINATES, cords);
		seralized = new WeakHashMap<String, Object>(6);
		seralized.put(LOCATION, map);
		seralized.put(LOCATION_NAME, "Marathahali");
	}
	
	@Test
	public void testHashCode() {
		EqualsVerifier.forClass(GeoLongLat.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}
	
	@Test
	public void seralizeTest(){
		assertThat(geo.toGeoCordMap()).isEqualTo(seralized);
	}
	
	@Test
	public void deSeralizeTest(){
		assertThat(GeoLongLat.initFromGeoCordMap(seralized)).isEqualTo(geo);
	}



}

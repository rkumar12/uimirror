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

import com.uimirror.location.DefaultLocation;

/**
 * A store for persiting the location like name, type, and country id
 * @author Jay
 */
public interface LocationStore {

	String LOCATION_SEQ = "loc_seq";
	
	/**
	 * Persist the location.
	 * @param loc
	 * @return
	 */
	DefaultLocation store(DefaultLocation loc);
	
	/**
	 * Gets the location based on the location ID.
	 * @param loc_id
	 * @return
	 */
	DefaultLocation getByLocationId(String loc_id);
	
	/**
	 * Gets the location by longitude and latitude
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	DefaultLocation getByCord(double longitude, double latitude);

}

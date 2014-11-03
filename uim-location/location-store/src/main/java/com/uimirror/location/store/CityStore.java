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

import com.uimirror.location.City;
import com.uimirror.location.State;

/**
 * A store implementation for the {@link City} details into the DB.
 * @author Jay
 */
public interface CityStore {
	
	String CITY_SEQ = "loc_city_seq";

	/**
	 * Store the {@link State} and returns the new populated {@link City} with ID.
	 * @param City
	 * @return
	 */
	City store(City city); 
	
	/**
	 * Gets the {@link City} Details by city id.
	 * @param city_id
	 * @return
	 */
	City getById(String city_id);
	
	/**
	 * Gets the {@link City} Details by city name.
	 * @param name
	 * @return
	 */
	City getByName(String name);
	
	/**
	 * Gets the State Details by city short name.
	 * @param short name
	 * @return
	 */
	City getByShortName(String sh_name);
	
	/**
	 * Delete the {@link City} by the City ID.
	 * @param city_id
	 */
	void deleteById(String city_id);

}

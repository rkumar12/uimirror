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

import com.uimirror.location.Country;

/**
 * A store implementation for the country details into the DB.
 * @author Jay
 */
public interface CountryStore {
	
	String COUNTRY_SEQ = "loc_seq";

	/**
	 * Store the Country and returns the new populated country with ID.
	 * @param country
	 * @return
	 */
	Country store(Country country); 
	
	/**
	 * Gets the Country Details by country id.
	 * @param country_id
	 * @return
	 */
	Country getById(String country_id);

	/**
	 * Gets the Country Details by country name.
	 * @param name
	 * @return
	 */
	Country getByName(String name);
	
	/**
	 * Gets the Country Details by country short name.
	 * @param short name
	 * @return
	 */
	Country getByShortName(String sh_name);
	
	/**
	 * Delete the {@link Country} by the country ID.
	 * @param country_id
	 */
	void deleteById(String country_id);

}

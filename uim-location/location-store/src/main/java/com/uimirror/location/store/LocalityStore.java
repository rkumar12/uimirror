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

import com.uimirror.location.Locality;

/**
 * A store implementation for the {@link Locality} details into the DB.
 * @author Jay
 */
public interface LocalityStore {
	
	String LOCALITY_SEQ = "loc_locality_seq";

	/**
	 * Store the {@link Locality} and returns the new populated {@link Locality} with ID.
	 * @param locality
	 * @return
	 */
	Locality store(Locality locality); 
	
	/**
	 * Gets the {@link Locality} Details by locality id.
	 * @param locality_id
	 * @return
	 */
	Locality getById(String locality_id);
	
	/**
	 * Gets the {@link Locality} Details by locality name.
	 * @param name
	 * @return
	 */
	Locality getByName(String name);
	
	/**
	 * Gets the Locality Details by locality short name.
	 * @param short name
	 * @return
	 */
	Locality getByShortName(String sh_name);
	
	/**
	 * Delete the {@link Locality} by the Locality ID.
	 * @param locality_id
	 */
	void deleteById(String locality_id);

}

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

import com.uimirror.location.State;

/**
 * A store implementation for the {@link State} details into the DB.
 * @author Jay
 */
public interface StateStore {
	
	String STATE_SEQ = "loc_state_seq";

	/**
	 * Store the {@link State} and returns the new populated {@link State} with ID.
	 * @param State
	 * @return
	 */
	State store(State state); 
	
	/**
	 * Gets the {@link State} Details by state id.
	 * @param state_id
	 * @return
	 */
	State getById(String state_id);
	
	/**
	 * Gets the {@link State} Details by state name.
	 * @param name
	 * @return
	 */
	State getByName(String name);
	
	/**
	 * Gets the State Details by state short name.
	 * @param short name
	 * @return
	 */
	State getByShortName(String sh_name);
	
	/**
	 * Delete the {@link State} by the state ID.
	 * @param state_id
	 */
	void deleteById(String state_id);

}

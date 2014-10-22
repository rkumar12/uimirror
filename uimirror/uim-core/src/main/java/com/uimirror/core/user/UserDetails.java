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
package com.uimirror.core.user;

import java.util.Map;

import com.uimirror.core.DOB;
import com.uimirror.core.Location;

/**
 * Interface to define user details methods.
 * @author damart1
 */
public interface UserDetails {

	/**
	 * Gets the profile ID
	 * @return
	 */
	String getProfileId();

	/**
	 * Gets the Address Of the User
	 * @return
	 */
	Location getPresentAddress();

	/**
	 * Gets the Address Of the User
	 * @return
	 */
	Location getPermanetAddress();

	/**
	 * Get Date of Birth of the user
	 * @return
	 */
	DOB getDateOfBirth();
	
	/**
	 * Get the details for the user profile.
	 * @return
	 */
	Object getDetails();
	
	/**
	 * @param raw
	 * @return
	 */
	UserDetails initFromMap(Map<String, Object> raw);
	
	/**
	 * @return
	 */
	Map<String, Object> toMap();

}

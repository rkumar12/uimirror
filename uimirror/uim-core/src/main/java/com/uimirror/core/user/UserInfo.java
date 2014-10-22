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

import com.uimirror.core.bean.Gender;

/**
 * Interface to define user information methods.
 * 
 * @author damart1
 *
 */
public interface UserInfo {

	/**
	 * Gives the profile ID of the user
	 * @return
	 */
	String getProfileId();
	
	/**
	 * Gets the first name of the user
	 * @return
	 */
	String getFirstName();
	
	/** 
	 * gets the last name
	 * @return
	 */
	String getLastName();
	
	/**
	 * Gets the combination of the first name and last name seperated by space
	 * @return
	 */
	String getName();
	
	/**
	 * Gets the Email id of the user
	 * @return
	 */
	String getEmail();
	
	/**
	 * Gets the Gender of the user
	 * @return
	 */
	Gender getGender();
	
	/**
	 * Gets the User Account Status
	 * @return
	 */
	AccountStatus getAccountStatus();
	
	/**
	 * Gets the User account state
	 * @return
	 */
	AccountState getAccountState();
	
	/**
	 * @param raw
	 * @return
	 */
	UserInfo initFromMap(Map<String, Object> raw);
	
	/**
	 * @return
	 */
	Map<String, Object> toMap();
	
}

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
package com.uimirror.core.util;

import com.uimirror.core.Constants;

/**
 * An Utility class that helps to get and manupulate the date time objects
 * @author Jay
 */
public class StringRegexUtil {
	

	/**
	 * Matches the given name with internatiolnization support
	 * @param name
	 * @return
	 */
	public static boolean isValidName(String name){
		return Constants.NAME_PATTERN.matcher(name).matches();
	}
	
	/**
	 * Matches the password policy 
	 * @param password
	 * @return
	 */
	public static boolean isPasswordFollowingThePolicy(String password){
		return Constants.PASSWORD_POLICY_PATTERN.matcher(password).matches();
	}
	
	
}

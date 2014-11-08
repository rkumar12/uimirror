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
 * A utility class for the {@link Boolean}
 * @author Jay
 */
public class BooleanUtil {

	/**
	 * Checks the provided argument is either y/yes/true, then returns true
	 * else false
	 * @param obj which will be parsed
	 * @return the boolean value represented by the obj
	 */
	public static final boolean parseBoolean(String obj){
		boolean keepMeLogin = Boolean.parseBoolean(obj);
		keepMeLogin = (keepMeLogin == Boolean.FALSE && 
				("y".equalsIgnoreCase(obj) || "yes".equalsIgnoreCase(obj))) ?  Boolean.TRUE : Boolean.FALSE;
		return keepMeLogin;
	}

}

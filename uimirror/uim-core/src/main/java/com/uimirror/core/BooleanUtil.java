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
	
	//Prevent creating instance
	private BooleanUtil(){
		//NOP
	}
	public static final String YES = "Yes";
	public static final String Y = "Y";
	

	/**
	 * Checks the provided argument is either y/yes/true, then returns <code>true</code>
	 * else <code>false</code>
	 * 
	 * @param obj which will be parsed
	 * @return the boolean value represented by the obj
	 */
	public static final boolean parseBoolean(String obj){
		boolean keepMeLogin = Boolean.parseBoolean(obj);
		keepMeLogin = (keepMeLogin == Boolean.FALSE && 
				(Y.equalsIgnoreCase(obj) || YES.equalsIgnoreCase(obj))) ?  Boolean.TRUE : Boolean.FALSE;
		return keepMeLogin;
	}

}

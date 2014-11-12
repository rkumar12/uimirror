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
package com.uimirror.core.auth;

/**
 * Defines the constant that are for security related
 * @author Jay
 */
public class SecurityConstants {
	
	//Prevent creating objects
	private SecurityConstants(){
		//NOP
	}

	/**
	 * <p>String identifier for the Ouath2 authorization header.
	 */
	public static final String BEARER = "Bearer";
}

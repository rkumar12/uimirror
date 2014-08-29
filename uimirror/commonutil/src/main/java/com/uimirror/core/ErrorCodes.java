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
 * <p>This has all the constant codes of a web code</p>
 * @author Jay
 */
public interface ErrorCodes {

	//2xx series
	
	/**
	 * No Content
	 */
	public static final int _204 = 204;
	
	//4xx series

	/**
	 * Not Found
	 */
	public static final int _404 =  404;
	
	/**
	 * Authentication Timeout
	 */
	public static final int _419 = 419;
	
	//5xx series
	
	/**
	 * Internal Server Error
	 */
	public static final int _500 = 500;
}

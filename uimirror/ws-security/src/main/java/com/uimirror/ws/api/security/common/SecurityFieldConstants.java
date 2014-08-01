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
package com.uimirror.ws.api.security.common;

/**
 * <p>This contains the data base field name as key</p>
 * @author Jayaram
 *
 */
public interface SecurityFieldConstants {

	//MONGO Commands
	public static final String _SET = "$set";
	//Common Fields
	public static final String _ID = "_id";
	public static final String _TIME_ZONE = "tz";
	public static final String _CURRENCY = "cur";
	public static final String _LOCALE = "loacle";
	public static final String _IP = "ip";
	
	//Client Embed Accessed Document Field
	public static final String _ACCESSED_ON = "on";
	public static final String _REQUESTED_FOR = "rqfor";
	public static final String _REQUESTED_STATUS = "status";
	
	//Client Short Details Field
	public static final String _API_KEY = "apikey";
	public static final String _CLIENT_SECRET = "secret";
	public static final String _CLIENT_REDIRECT_URL = "redirecturi";
	public static final String _CLIENT_LICENSE = "license";
	public static final String _CLIENT_IS_ACTIEVE = "status";
	public static final String _CLIENT_IS_AUTO_APPROVE = "approve";
	public static final String _CLIENT_ADDITIONAL_INFO = "extra";
	
	//Common Attributes
	public static final String _ST_NUM_0 = "0";
	public static final String _ST_NUM_1 = "1";
	public static final int NUM_1 = 1;
	public static final String EMPTY = "";
}

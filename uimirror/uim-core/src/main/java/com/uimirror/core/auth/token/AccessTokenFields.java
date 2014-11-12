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
package com.uimirror.core.auth.token;

import com.uimirror.core.mongo.feature.BasicDBFields;

/**
 * Basic DB fileds for the access token
 * @author Jay
 */
public class AccessTokenFields extends BasicDBFields{
	
	//Prevent creating instance
	private AccessTokenFields(){
		//NOP
	}
	
	//Auth Token Fields
	public static final String ENCRYPT_STARTEGY = "algo";
	public static final String TYPE = "type";
	public static final String SCOPE = "scope";
	public static final String AUTH_TKN_OWNER = "owner";
	public static final String AUTH_TKN_CLIENT = "client";
	public static final String AUTH_TKN_EXPIRES = "expire";
	public static final String AUTH_TKN_NOTES = "note";
	public static final String AUTH_TKN_INSTRUCTIONS = "instructions";
	public static final String AUTH_TKN_MESSAGES = "msg";
	//Token will be transfered from _ID, both are logical similar, only token will be in encrypted way
	public static final String TOKEN = "token";
	//Refresh Token Interval
	public static final String REFRESH_TOKEN_INTERVAL = "refresh";

}

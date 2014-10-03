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

import com.uimirror.core.BasicDBFields;

/**
 * Basic DB fileds for the access token
 * @author Jay
 */
public interface AccessTokenFields extends BasicDBFields{
	
	//Auth Token Fields
	String ENCRYPT_STARTEGY = "algo";
	String TYPE = "type";
	String SCOPE = "scope";
	String AUTH_TKN_OWNER = "owner";
	String AUTH_TKN_CLIENT = "client";
	String AUTH_TKN_EXPIRES = "expire";
	String AUTH_TKN_NOTES = "note";
	String AUTH_TKN_INSTRUCTIONS = "instructions";
	String TOKEN = "token";

}

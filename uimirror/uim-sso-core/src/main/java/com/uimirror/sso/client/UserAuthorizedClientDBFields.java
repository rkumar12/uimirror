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
package com.uimirror.sso.client;

import com.uimirror.core.mongo.feature.BasicDBFields;

/**
 * Contains all the DB fields required for user authorized clients
 * @author Jay
 */
public class UserAuthorizedClientDBFields extends BasicDBFields{
	//Prevent instance
	private UserAuthorizedClientDBFields(){
		//NOP
	}
	
	//User authorized clients mapped fields
	public static final String CLIENTS = "clients";
	public static final String CLIENT_ID = "client_id";
	public static final String SCOPE = "scope";
	
	public static final String CLIENT_ARRAY_MATCH_DOC = CLIENTS+".$";
}

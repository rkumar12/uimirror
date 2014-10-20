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
package com.uimirror.account.auth.user;

import com.uimirror.core.BasicDBFields;

/**
 * Contains all the DB fields required for user authorized clients
 * @author Jay
 */
public interface UserAuthorizedClientDBFields extends BasicDBFields{
	
	//User authorized clients mapped fields
	String CLIENTS = "clients";
	String CLIENT_ID = "client_id";
	String SCOPE = "scope";
	
	String CLIENT_ARRAY_MATCH_DOC = CLIENTS+".$";
}

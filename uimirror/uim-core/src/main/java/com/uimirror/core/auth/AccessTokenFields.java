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
	
	String ENCRYPT_STARTEGY = "parapharse";
	String EXPIRE_ON = "expireon";
	String TYPE = "type";
	String SCOPE = "scope";
	String IDENTIFIER = "id";

}

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
package com.uimirror.auth;

/**
 * Contains all the DB fields required for application
 * @author Jay
 */
public interface DBFileds {

	String ID = "_id";
	
	//User Credentials
	String UC_USER_ID = "uid";
	String PASSWORD = "pwd";
	String UC_ACCOUNT_STATE = "state";
	String UC_ACCOUNT_STATUS = "status";
	String UC_ENCRYPTION_PWD = "salt";
	String UC_ACCOUNT_INSTRUCTION = "sti";
}

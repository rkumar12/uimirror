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
package com.uimirror.account.user;

import com.uimirror.core.BasicDBFields;

/**
 * Contains all the DB fields required for application
 * @author Jay
 */
public interface UserAuthDBFields extends BasicDBFields{
	
	//User Credentials
	String USER_ID = "uid";
	String PASSWORD = "pwd";
	String SCREEN_PASSWORD = "screen_pwd";
	String ACCOUNT_STATE = "state";
	String ACCOUNT_STATUS = "status";
	String ENCRYPTION_PWD = "salt";
	String ACCOUNT_INSTRUCTION = "sti";
	//User Account Instructions for 2FA
	String ACC_INS_2FA = "2fa";
}

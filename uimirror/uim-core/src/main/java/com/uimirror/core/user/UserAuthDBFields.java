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
package com.uimirror.core.user;

import com.uimirror.core.mongo.feature.BasicDBFields;

/**
 * Contains all the DB fields required for application
 * @author Jay
 */
public class UserAuthDBFields extends BasicDBFields{
	
	//Prevent Instance
	private UserAuthDBFields(){
		//NOP
	}
	//User Credentials
	public static final String USER_ID = "uid";
	public static final String PASSWORD = "pwd";
	public static final String SCREEN_PASSWORD = "screen_pwd";
	public static final String ACCOUNT_STATE = "state";
	public static final String ACCOUNT_STATUS = "status";
	public static final String ENCRYPTION_PWD = "salt";
	public static final String ACCOUNT_INSTRUCTION = "sti";
	//User Account Instructions for 2FA
	public static final String ACC_INS_2FA = "2fa";
}

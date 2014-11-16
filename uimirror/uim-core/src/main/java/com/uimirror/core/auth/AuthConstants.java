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


/**
 * Common interface that defines the contract that implementing class 
 * should extract the credential details and populate the {@link Authentication}
 * 
 * Input to the method could be anything, which has a valid credentials details. 
 * 
 * @author Jay
 */
public class AuthConstants{
	
	//Prevent creating object
	private AuthConstants(){
		//NOP
	}

	public static final String USER_ID = "uid";
	//This is also known as the parapharse that will be used for decrypting
	public static final String TOKEN_ENCRYPTION_STARTEGY = "pp";
	public static final String PASSWORD = "pwd";
	public static final String OTP = "otp";
	public static final String WEB_VERIFY_TOKEN = "wotp";
	public static final String KEEP_ME_LOGIN = "keepmelogin";
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String SCOPE = "scope";
	public static final String APP = "app";
	public static final String AUTHORIZATION_TOKEN = "Authorization";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String APPROVAL = "approval";
	
	//Default refresh period is 30 Mins
	public static final int DEFAULT_EXPIRY_INTERVAL = 30;
	public static final int DEFAULT_EMAIL_LINK_EXPIRY_INTERVAL = 24*60;
	public static final int KEEP_ME_LOGED_IN_EXPIRY_INTERVAL = 24*60;
	
	//For Client specific screen
	public static final String CLIENT_ID = "client_id";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String CLIENT_SECRET_CODE = "code";
	public static final String LIMIT = "limit";
	public static final String SESSION_ID = "session_id";
	
	//Auth refresh instructions
	public static final String INST_AUTH_EXPIRY_INTERVAL = "expiry_inst";
	
	//Instructions Next step
	public static final String INST_NEXT_STEP = "next";
	public static final String INST_NEXT_USER_AUTH = "user_login";
	public static final String INST_NEXT_USER_OTP = "user_otp";
	public static final String INST_NEXT_CLIENT_AUTHORIZATION = "client_authorization";
	public static final String INST_NEXT_ACCESS_TOKEN = "access";
	public static final String INST_RESTORE_REQUIRED = "restore";
	public static final String INST_NEXT_ACCOUNT_VERIFY = "verify_acount";
	
}

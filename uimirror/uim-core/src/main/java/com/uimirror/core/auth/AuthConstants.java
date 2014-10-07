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

import com.uimirror.core.Parameters;
import com.uimirror.core.auth.bean.Authentication;

/**
 * Common interface that defines the contract that implementing class 
 * should extract the credential details and populate the {@link Authentication}
 * 
 * Input to the method could be anything, which has a valid credentials details. 
 * 
 * @author Jay
 */
public interface AuthConstants extends Parameters{

	String USER_ID = "uid";
	//This is also known as the parapharse that will be used for decrypting
	String TOKEN_ENCRYPTION_STARTEGY = "pp";
	String PASSWORD = "pwd";
	String OTP = "otp";
	String KEEP_ME_LOGIN = "keepmelogin";
	String REDIRECT_URI = "redirect_uri";
	String SCOPE = "scope";
	String APP = "app";
	String AUTHORIZATION_TOKEN = "Authorization";
	String ACCESS_TOKEN = "access_token";
	//Default refresh period is 10 Mins
	String DEFAULT_REFRESH_INTERVAL = Integer.toString(10*60);
	
	//For Client specific screen
	String CLIENT_ID = "client_id";
	String CLIENT_SECRET = "client_secret";
	String CLIENT_SECRET_CODE = "code";
	String LIMIT = "limit";
	String SESSION_ID = "session_id";
	
}

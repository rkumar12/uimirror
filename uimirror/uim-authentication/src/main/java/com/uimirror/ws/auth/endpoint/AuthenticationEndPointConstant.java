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
package com.uimirror.ws.auth.endpoint;

/**
 * Contains the default property for the authentication endpoints
 * @author Jay
 */
public class AuthenticationEndPointConstant {
	//Prevent instance
	private AuthenticationEndPointConstant(){
		//NOP
	}
	public static final String AUTH = "/auth";
	public static final String OUATH2_HOME = AUTH+"/oauth2";
	public static final String OUATH_2_SECRET_CODE_PATH = "/secret";
	public static final String TWO_FACTO_PATH = "/OTP";
	public static final String LOGIN_PATH = "/login";
	public static final String LOGOUT_PATH = "/logout";
	public static final String GRANT_ACCESS_TO_CLIENT_PATH = AUTH+"/clint/permit";
	public static final String OUATH_2_TOEKEN_PATH = "/token";
	public static final String OUATH_2_TOEKEN_VALIDATE_REFRESH_PATH = "/refresh";
	public static final String ACCESS_TOKEN_VALIDATION_PATH = "/validate";
	public static final String ACCESS_HOME_PATH = "/access";

	public static final String UNLOCK_PATH = "/unlock/screen";
	public static final String LOGIN_SESSION_PATH = AUTH+"/session";
	public static final String LOGIN_SESSION_INVALID_PATH = "/invalid";
	public static final String LOGIN_SESSION_INVALID_ALL_PATH = LOGIN_SESSION_INVALID_PATH+"/all";
	public static final String AUTH_MAIL_HOME = AUTH+"/otp";
	public static final String RESEND_MAIL_PATH = "/send/mail";
	

}

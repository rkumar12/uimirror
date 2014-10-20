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
package com.uimirror.account.auth.endpoint;

/**
 * Contains the default property for the authentication endpoints
 * @author Jay
 */
public interface AuthenticationEndPointConstant {
	String AUTH = "/auth";
	String OUATH2_HOME = AUTH+"/oauth2";
	String OUATH_2_SECRET_CODE_PATH = "/secret";
	String TWO_FACTO_PATH = "/OTP";
	String LOGIN_PATH = "/login";
	String GRANT_ACCESS_TO_CLIENT_PATH = AUTH+"/clint/permit";
	String OUATH_2_TOEKEN_PATH = "/token";

	String UNLOCK_PATH = "/unlock/screen";
	String OUATH_2_TOEKEN_VALIDATE_REFRESH_PATH = "/token/validate/refresh";
	String ACCESS_HOME_PATH = "/access";
	String ACCESS_TOKEN_VALIDATION_PATH = "/validate";
	String LOGIN_SESSION_PATH = AUTH+"/session";
	String LOGIN_SESSION_INVALID_PATH = "/invalid";
	String LOGIN_SESSION_INVALID_ALL_PATH = LOGIN_SESSION_INVALID_PATH+"/all";
	String AUTH_MAIL_HOME = AUTH+"/otp";
	String RESEND_MAIL_PATH = "/send/mail";
	

}

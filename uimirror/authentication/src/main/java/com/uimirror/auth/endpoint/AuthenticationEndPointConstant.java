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
package com.uimirror.auth.endpoint;

/**
 * Contains the default property for the authentication endpoints
 * @author Jay
 */
public interface AuthenticationEndPointConstant {

	String HOME = "/";
	String OUATH2_HOME = "/oauth2";
	String OUATH_2_AUTH_PATH = "/auth";
	String UNLOCK_PATH = "/unlock/screen";
	String TWO_FACTO_PATH = "/otp";
	String OUATH_2_TOEKEN_PATH = "/token";
	String OUATH_2_TOEKEN_VALIDATE_REFRESH_PATH = "/token/validate/refresh";
	String ACCESS_HOME_PATH = "/access";
	String ACCESS_TOKEN_VALIDATION_PATH = "/validate";
	String GRANT_ACCESS_TO_CLIENT_PATH = OUATH_2_TOEKEN_PATH+"/"+ACCESS_HOME_PATH;
	String CLIENT_IS_REMEMBERED_PATH = "/client/remember";
	String LOGIN_PATH = "/login";
	String LOGIN_SESSION_PATH = LOGIN_PATH+"/session";
	String LOGIN_SESSION_INVALID_PATH = "/invalid";
	String LOGIN_SESSION_INVALID_ALL_PATH = LOGIN_SESSION_INVALID_PATH+"/all";
	String RESEND_MAIL_PATH = "/send/mail";
	

}

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

	String UNLOCK_PATH = "unlock";
	
	String TWO_FACTO_PATH = "2fa";
	
	String OUATH_2_AUTH_PATH = "auth";//TODO move this to MVC really
	String OUATH_2_TOEKEN_PATH = "token";
	
	String ACCESS_HOME_PATH = "/access";
	String ACCESS_TOKEN_VALIDATION_PATH = "/validate";

}

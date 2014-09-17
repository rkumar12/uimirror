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

import com.uimirror.core.auth.Authentication;

/**
 * Common interface that defines the contract that implementing class 
 * should extract the credential details and populate the {@link Authentication}
 * 
 * Input to the method could be anything, which has a valid credentials details. 
 * 
 * @author Jay
 */
public interface AuthParamExtractor<T> {

	String CREDENTIAL_TYPE = "ctype";
	String DEVICE_TYPE = "device";
	String USER_ID = "uid";
	String PASSWORD = "pwd";
	String KEEP_ME_LOGIN = "keepmelogin";
	String ACCESS_TOKEN = "token";
	String REFRESH_TOKEN_INTERVAL = "rftkninvl";
	//Default refresh period is 10 Mins
	String DEFAULT_REFRESH_INTERVAL = Integer.toString(10*60);
	
	/**
	 * <p>This extract the authentication details such as user name, password
	 * and other information related to validate the request</p>
	 * 
	 * @param param
	 * @return
	 */
	Authentication extractAuthParam(T param);
}

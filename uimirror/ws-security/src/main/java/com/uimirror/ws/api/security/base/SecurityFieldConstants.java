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
package com.uimirror.ws.api.security.base;

/**
 * <p>This contains the data base field name as key</p>
 * @author Jayaram
 *
 */
public interface SecurityFieldConstants {

	//MONGO Commands
	public static final String _SET = "$set";
	//Common Fields
	public static final String _ID = "_id";
	public static final String _TIME_ZONE = "tz";
	public static final String _CURRENCY = "cur";
	public static final String _LOCALE = "loacle";
	public static final String _IP = "ip";
	
	//Client Details Field
	public static final String _CLIENT_SECRET = "secret";
	public static final String _CLIENT_RESOURCE_IDS = "resource_ids";
	public static final String _CLIENT_SCOPE = "scope";
	public static final String _CLIENT_AUTHORIZED_GRANT_TYPE = "authorized_grant_types";
	public static final String _CLIENT_WEB_REDIRECT_URI = "redirect_uri";
	public static final String _CLIENT_AUTHORITIES = "authorities";
	public static final String _CLIENT_ACCESS_TOKEN_VALIDITY = "access_token_validity";
	public static final String _CLIENT_REFRESH_TOKEN_VALIDITY = "refresh_token_validity";
	public static final String _CLIENT_ADDITIONAL_INFORMATION = "additional_information";
	
	//DUPLICATE Remove the below fields latter
	public static final String _CLIENT_NAME = "name";
	public static final String _CLIENT_APP_URL = "appurl";
	public static final String _CLIENT_CREATED_ON = "on";
	
	//Client Embed Accessed Document Field
	public static final String _ACCESSED_ON = "on";
	public static final String _REQUESTED_FOR = "rqfor";
	public static final String _REQUESTED_STATUS = "status";
	
	//Client Short Details Field
	public static final String _API_KEY = "apikey";
	public static final String _CLIENT_ROLES = "role";
	public static final String _CLIENT_IS_ACTIEVE = "status";
	
}

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
package com.uimirror.core.client;

import com.uimirror.core.mongo.feature.BasicDBFields;

/**
 * Contains all the basic db fields that will be used by the client
 * @author Jay
 */
public class ClientDBFields extends BasicDBFields{
	
	//Prevent instance
	private ClientDBFields(){
		//NOP
	}
	public static final String NAME = "name";
	public static final String SECRET = "secret";
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String APP_URL = "app_uri";
	public static final String STATUS = "status";
	public static final String API_KEY = "api_key";
	public static final String REGISTERED_ON = "registered_on";
	public static final String REGISTERED_BY = "registered_by";
	public static final String DETAILS = "details";
	public static final String IMAGE = "image";
	

}

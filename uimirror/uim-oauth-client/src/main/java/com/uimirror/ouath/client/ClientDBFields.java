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
package com.uimirror.ouath.client;

import com.uimirror.core.mongo.feature.BasicDBFields;

/**
 * Contains all the basic db fields that will be used by the client
 * @author Jay
 */
public interface ClientDBFields extends BasicDBFields{
	
	String NAME = "name";
	String SECRET = "secret";
	String REDIRECT_URI = "redirect_uri";
	String APP_URL = "app_uri";
	String STATUS = "status";
	String API_KEY = "api_key";
	String REGISTERED_ON = "registered_on";
	String REGISTERED_BY = "registered_by";
	String DETAILS = "details";

}

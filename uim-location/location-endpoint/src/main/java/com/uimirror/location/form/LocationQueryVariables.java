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
package com.uimirror.location.form;

import com.uimirror.core.Parameters;

/**
 * This has all the variable that user are in-deed to pass to make query for the location.
 *  
 * @author Jay
 */
public interface LocationQueryVariables extends Parameters{
	//By Name
	String QUERY = "q";
	String LIMIT = "limit";
	String EXACT = "exact";
	
	//By Long, Lat
	String LONGITUDE = "lon";
	String LATITUDE = "lat";
	
	//By Location LOCATION_ID
	String LOCATION_ID = "id";
}

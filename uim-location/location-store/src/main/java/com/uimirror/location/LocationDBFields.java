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
package com.uimirror.location;

import com.uimirror.core.mongo.feature.BasicDBFields;

/**
 * Holds the Location Store Fields, which will be major document structure
 * <p>Basically has the name, id, type and country its linking to</p>
 * @author Jay
 */
public interface LocationDBFields extends BasicDBFields{
	 
	String NAME = "name";
	String COUNTRY_ID = "c_id";
	String LOCATION_TYPE = "type";
	String LOCATION_ACCURACY_TYPE = "accuracy";
}

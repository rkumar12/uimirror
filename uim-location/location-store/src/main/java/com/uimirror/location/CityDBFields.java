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


public interface CityDBFields extends BasicDBFields{
	 
	String SHORT_NAME = "sh_name";
	String NAME = "name";
}

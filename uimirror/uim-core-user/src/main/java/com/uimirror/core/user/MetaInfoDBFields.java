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
package com.uimirror.core.user;

import com.uimirror.core.mongo.feature.BasicDBFields;

public class MetaInfoDBFields extends BasicDBFields{
	 
	//Prevent Instance
	private MetaInfoDBFields(){
		//NOP
	}
	public static final String LOCALE = "locale";
	public static final String CURRENCY = "cur";
	public static final String TIMEZONE = "tz";

}

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

/**
 * Contains all the DB fields required for application
 * @author Jay
 */
public class UserAccountLogDBFields extends BasicDBFields{
	
	//Prevent instance
	private UserAccountLogDBFields(){
		//NOP
	}
	
	//User Account logs such as created on etc
	public static final String CREATED_ON = "created_on";
	public static final String MODIFIED_ON = "modified_on";
	public static final String DETAILS = "details";
}

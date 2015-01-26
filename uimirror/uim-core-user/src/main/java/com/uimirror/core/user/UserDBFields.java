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


public class UserDBFields extends BasicDBFields{
	
	//Prevent instance
	private UserDBFields(){
		//NOP
	}
	 
	 public static final String FIRST_NAME="first_name";
	 public static final String LAST_NAME="last_name";
	 public static final String NAME="name";
	 public static final String EMAIL="email";
	 public static final String GENDER="gender";
	 public static final String DATE_OF_BIRTH="dob";
	 public static final String SNAP_LOC="snap_loc";
	 public static final String COVER_SNAP_LOC="cover_snap_loc";
	 
	 public static final String PRESENT_ADDRESS="present_add";
	 public static final String PERMANET_ADDRESS="permanet_add";
	 public static final String ACCOUNT_STATUS="status";
	 public static final String ACCOUNT_STATE="state";
	 public static final String INFO = "info";
	 public static final String META_INFO = "meta";
	 
	 public static final String BLOOD_GROUP = "blood_group";
	 public static final String RELATION_SHIP_STATUS = "relation_ship_status";
	 public static final String CURRENCY = "currency";
	 public static final String LOCALE = "locale";
	 public static final String TIME_ZONE = "TIME_ZONE";

}

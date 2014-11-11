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


public interface UserDBFields extends UserAuthDBFields, UserAccountLogDBFields, DOBDBFields, AddressDBFields, MetaInfoDBFields{
	 
	 String FIRST_NAME="first_name";
	 String LAST_NAME="last_name";
	 String NAME="name";
	 String EMAIL="email";
	 String GENDER="gender";
	 String DATE_OF_BIRTH="dob";
	 String PRESENT_ADDRESS="present_add";
	 String PERMANET_ADDRESS="permanet_add";
	 String ACCOUNT_STATUS="account_status";
	 String ACCOUNT_STATE="account_state";
	 String INFO = "info";

}

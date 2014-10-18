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

import java.util.Map;

import com.uimirror.core.bean.Gender;

/**
 * @author Jay
 */
//TODO add java doc
public interface UserDetails {

	String getProfileId();
	
	String getFirstName();
	
	String getLastName();
	
	String getEmail();
	
	String getDateOfBirth();
	
	Gender getGender();
	
	AccountStatus getAccountStatus();
	
	AccountState getAccountState();
	
	Map<String, Object> getDetails();
}

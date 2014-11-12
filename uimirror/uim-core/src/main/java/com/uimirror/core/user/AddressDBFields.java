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


public class AddressDBFields extends BasicDBFields{
	
	//Prevent instance 
	private AddressDBFields(){
		 //NOP
	}
	
	public static final String PRESENT_ADDRESS = "prsent_address";
	public static final String PERMANET_ADDRESS = "permanet_address";
	

}

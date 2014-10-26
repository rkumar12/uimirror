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
package com.uimirror.core;


/**
 * will hold the yes no constants
 * 
 * @author Jay
 */
public enum YesNoType {

	YES("Y"),
	NO("N"),
	MAYBE("M");
	
	private final String decission;
	
	private YesNoType(String type) {
		this.decission = type;
	}

	public String getDecission() {
		return decission;
	}
	
	@Override
    public String toString() {
    	return this.getDecission();
    }
	
	public static YesNoType getEnum(String type) {
    	if(type == null)
    		throw new IllegalArgumentException("Decission Can't be empty");
    	for(YesNoType v : values())
    		if(type.equalsIgnoreCase(v.getDecission())) return v;
    	return null;
    }
}

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
package com.uimirror.core.auth;


/**
 * Holds the
 * @author Jay
 */
public enum DeviceType {

	MOBILE("M"),
	BROWSER("B");
	
	private final String code;
	
	private DeviceType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	@Override
    public String toString() {
    	return this.getCode();
    }
	
    public static DeviceType getEnum(String code) {
    	if(code == null)
    		throw new IllegalArgumentException("Device type Can't be empty");
    	for(DeviceType v : values())
    		if(code.equalsIgnoreCase(v.getCode())) return v;
    	throw new IllegalArgumentException("No Device type Found");
    }
}

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
package com.uimirror.ws.api.security.ouath;

import com.uimirror.util.Constants;

/**
 * <p>This has license for the user services</p>
 * @author Jay
 */
public enum License {

	BASIC("1"),
	EXTRASMALLUSAGE("2"),
	SMALLUSAGE("3"),
	MIDIUMUSAGE("4"),
	EXTRAMIDIUMUSAGE("5"),
	LARGE("6"),
	EXTRALARGE("7");
	
	private final String license;
    private final String descriotion;
 
    License(String license) {
    	this.license = license;
    	this.descriotion = Constants.EMPTY;
    }
    
    License(String license, String description) {
    	this.license = license;
    	this.descriotion = description;
    }

    public String getLicense() {
    	return this.license;
    }
    
    public String getDescription() {
    	return this.descriotion;
    }

    @Override
    public String toString() {
    	return this.getLicense();
    } 

    public static License getEnum(String license) {
    	if(license == null)
    		throw new IllegalArgumentException("License Can't be empty");
    	for(License v : values())
    		if(license.equalsIgnoreCase(v.getLicense())) return v;
    	throw new IllegalArgumentException("No License Found");
    }
	
}

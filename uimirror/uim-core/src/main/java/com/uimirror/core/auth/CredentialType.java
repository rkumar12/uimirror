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

import com.uimirror.util.Constants;

/**
 * <p>This will hold the principal type suggesting type of login details</p>
 * <p>A credential can be user credential, or a client such as other application
 * who is trying to authenticate</p>
 * @author Jay
 */
public enum CredentialType {

	COOKIE("C"),
	LOGINFORM("F"),
	SCREENLOCK("S"),
	APIKEY("API");
	
	private final String principalType;
    private final String description;
 
    CredentialType(String principalType) {
    	this.principalType = principalType;
    	this.description = Constants.EMPTY;
    }
    
    CredentialType(String principalType, String description) {
    	this.principalType = principalType;
    	this.description = description;
    }
    
    public String getPrincipalType() {
		return principalType;
	}

    public String getDescription() {
		return description;
	}

	@Override
    public String toString() {
    	return this.getPrincipalType();
    } 

    public static CredentialType getEnum(String role) {
    	if(role == null)
    		throw new IllegalArgumentException("Prinicipal type Can't be empty");
    	for(CredentialType v : values())
    		if(role.equalsIgnoreCase(v.getPrincipalType())) return v;
    	throw new IllegalArgumentException("No Principal type Found");
    }
	
}

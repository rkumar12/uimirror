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

import com.uimirror.core.Constants;

/**
 * <p>This will hold the principal type suggesting type of login details</p>
 * <p>Access token will be issued to client as well user, so here to identify which 
 * token was for him this enum is mandatory</p>
 * 
 * @author Jay
 */
public enum AccessTokenType {

	USER("U"),
	CLIENT("C");
	
	private final String accessTokenType;
    private final String description;
 
    AccessTokenType(String principalType) {
    	this.accessTokenType = principalType;
    	this.description = Constants.EMPTY;
    }
    
    AccessTokenType(String principalType, String description) {
    	this.accessTokenType = principalType;
    	this.description = description;
    }
    
    public String getAccessTokenType() {
		return accessTokenType;
	}

    public String getDescription() {
		return description;
	}

	@Override
    public String toString() {
    	return this.getAccessTokenType();
    } 

    public static AccessTokenType getEnum(String role) {
    	if(role == null)
    		throw new IllegalArgumentException("Access Token type Can't be empty");
    	for(AccessTokenType v : values())
    		if(role.equalsIgnoreCase(v.getAccessTokenType())) return v;
    	throw new IllegalArgumentException("No AccessToken type Found");
    }
	
}

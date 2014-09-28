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
package com.uimirror.core.auth.bean;

import com.uimirror.core.Constants;

/**
 * <p>This will hold the principal type suggesting type of Token</p>
 * <p>Access token will be issued to client as well user, so here to identify which 
 * type token is like its a secret key or access key</p>
 * 
 * @author Jay
 */
public enum TokenType {

	ACCESS("A"),
	SECRET("S"),
	TEMPORAL("T");
	
	private final String tokenType;
    private final String description;
 
    TokenType(String type) {
    	this.tokenType = type;
    	this.description = Constants.EMPTY;
    }
    
    TokenType(String type, String description) {
    	this.tokenType = type;
    	this.description = description;
    }

	@Override
    public String toString() {
    	return this.getTokenType();
    } 

    public static TokenType getEnum(String role) {
    	if(role == null)
    		throw new IllegalArgumentException("Access Token type Can't be empty");
    	for(TokenType v : values())
    		if(role.equalsIgnoreCase(v.getTokenType())) return v;
    	throw new IllegalArgumentException("No AccessToken type Found");
    }

	public String getTokenType() {
		return tokenType;
	}

	public String getDescription() {
		return description;
	}
	
}

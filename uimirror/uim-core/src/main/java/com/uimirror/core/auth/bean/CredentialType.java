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
 * <p>This will hold the principal type suggesting type of login details</p>
 * <p>A credential can be user credential, or a client such as other application
 * who is trying to authenticate</p>
 * @author Jay
 */
public enum CredentialType {

	ACCESSKEY("C", "Cookie"),
	LOGINFORM("F", "Login Form"),
	SCREENLOCK("S", "Screen Lock"),
	APIKEY("API", "API key"),
	CLIENTSECRECTKEY("CLISECRET", "Client Secret Key"),
	_2FA("2FA", "Two factor authentication form");
	
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

    public static CredentialType getEnum(String type) {
    	if(type == null)
    		throw new IllegalArgumentException("Prinicipal type Can't be empty");
    	for(CredentialType v : values())
    		if(type.equalsIgnoreCase(v.getPrincipalType())) return v;
    	throw new IllegalArgumentException("No Principal type Found");
    }
    
    public static CredentialType getEnumByDesc(String description) {
    	if(description == null)
    		throw new IllegalArgumentException("Prinicipal type Can't be empty");
    	for(CredentialType v : values())
    		if(description.equalsIgnoreCase(v.getDescription())) return v;
    	throw new IllegalArgumentException("No Principal type Found");
    }
}

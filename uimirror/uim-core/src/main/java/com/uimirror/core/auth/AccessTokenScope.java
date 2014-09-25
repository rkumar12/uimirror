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
 * Identifies possible scope for the token.
 * {@link AccessTokenScope#READ} only read operation from application
 * {@link AccessTokenScope#WRITE} only write to the application
 * {@link AccessTokenScope#READWRITE} can read and write to the application
 * {@link AccessTokenScope#LIMITED} can do limited operation for read write 
 * to his/her account only 
 * @author Jay
 */
public enum AccessTokenScope {

	READ("R"),
	WRITE("W"),
	READWRITE("RW"),
	LIMITED("L");
	
	private final String scope;
	
	private AccessTokenScope(String scope) {
		this.scope = scope;
	}
	
	public String getScope() {
		return scope;
	}

	@Override
    public String toString() {
    	return this.getScope();
    } 
	
	public static AccessTokenScope getEnum(String status) {
    	if(status == null)
    		throw new IllegalArgumentException("Access Token Scope can't be empty");
    	for(AccessTokenScope v : values())
    		if(status.equalsIgnoreCase(v.getScope())) return v;
    	return null;
    }
	
}

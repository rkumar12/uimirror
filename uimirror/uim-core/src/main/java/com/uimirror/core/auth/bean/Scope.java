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

/**
 * Identifies possible scope for the token.
 * {@link Scope#READ} only read operation from application
 * {@link Scope#WRITE} only write to the application
 * {@link Scope#READWRITE} can read and write to the application
 * {@link Scope#LIMITED} can do limited operation for read write 
 * to his/her account only 
 * @author Jay
 */
public enum Scope {

	READ("R"),
	WRITE("W"),
	READWRITE("RW"),
	LIMITED("L");
	
	private final String scope;
	
	private Scope(String scope) {
		this.scope = scope;
	}
	
	public String getScope() {
		return scope;
	}

	@Override
    public String toString() {
    	return this.getScope();
    } 
	
	public static Scope getEnum(String scope) {
    	if(scope == null)
    		throw new IllegalArgumentException("Access Token Scope can't be empty");
    	for(Scope v : values())
    		if(scope.equalsIgnoreCase(v.getScope())) return v;
    	return null;
    }
	
}

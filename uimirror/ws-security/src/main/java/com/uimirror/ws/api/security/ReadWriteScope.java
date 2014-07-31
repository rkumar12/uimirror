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
package com.uimirror.ws.api.security;

/**
 * <p>It holds all the possible read write role available for the API</p>
 * @author Jay
 */
public enum ReadWriteScope {

	READ("R"),
	WRITE("W"),
	READWRITE("RW");
	
	private final String scope;
	
	ReadWriteScope(String scope){
		this.scope = scope;
	}
	
	public String getScope(){
		return this.scope;
	}
	
	@Override
    public String toString() {
    	return this.getScope();
    }
	
	public static ReadWriteScope getEnum(String scope){
		if(scope == null)
			throw new IllegalArgumentException("Read and write Scope can't be empty.");
		for(ReadWriteScope v : values())
    		if(scope.equalsIgnoreCase(v.getScope())) return v;
    	throw new IllegalArgumentException("No Scope Found");
	}

}

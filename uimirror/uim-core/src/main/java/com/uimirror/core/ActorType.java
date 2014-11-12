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
 * will hold the type of actor it has such as
 * {@linkplain ActorType#USER} and {@linkplain ActorType#CLIENT}
 * 
 * @author Jay
 */
@Deprecated
public enum ActorType {

	USER("U"),
	CLIENT("C");
	
	private final String type;
	
	private ActorType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	@Override
    public String toString() {
    	return this.getType();
    }
	
	public static ActorType getEnum(String type) {
    	if(type == null)
    		throw new IllegalArgumentException("Actor Type type Can't be empty");
    	for(ActorType v : values())
    		if(type.equalsIgnoreCase(v.getType())) return v;
    	return null;
    }
}

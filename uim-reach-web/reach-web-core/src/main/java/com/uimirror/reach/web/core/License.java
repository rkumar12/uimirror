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
package com.uimirror.reach.web.core;


/**
 * Contains the {@link License} types such as {@link #DEFAULT} which will be in the basic usage tier per application.
 * {@link #PER_USAGE} will be charged, as per the usage per application.
 * 
 * @author Jay
 */
//TODO move to uim-core
public enum License {
	
	DEFAULT(0),
	PER_USAGE(1);

	private final int code;
	
	private License(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	@Override
    public String toString() {
    	return Integer.toString(this.getCode());
    } 
	
	public static License getEnum(int code) {
    	for(License v : values())
    		if(code == v.getCode()) return v;
    	return null;
    }

}

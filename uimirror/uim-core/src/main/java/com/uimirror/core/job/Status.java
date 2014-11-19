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
package com.uimirror.core.job;

/**
 * <p>status enum which will be corresponding to the request call</p>
 * <p>One request can be complete or failed</p>
 * @author Jayaram
 */
public enum Status {

	COMPLETE("C"),
	STRATED("S"),
	FAILED("F");
	
	private final String status;
	
	private Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
	@Override
    public String toString() {
		return this.getStatus();
	}
	
	public static Status getEnum(String status){
		if(status == null)
			throw new IllegalArgumentException("Request Status can't be empty");
		for(Status v: values())
			if(status.equalsIgnoreCase(v.getStatus())) return v;
		return null;
	}
	
}

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
package com.uimirror.ws.api.audit;

/**
 * <p>status enum which will be coresponding to the reqest call</p>
 * <p>One request will be {@code Status#PROGRESS} when initial request enters</p>
 * <p>While sending the response a request can be marked as {@code Status#COMPLETE} 
 * or {@code Status#INCOMPLETE} or {@code Status#FAILED}</p>
 * @author Jayaram
 */
public enum Status {

	PROGRESS("P"),
	INCOMPLETE("IC"),
	COMPLETE("C"),
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
		throw new IllegalArgumentException("No Status Found");
	}
	
}

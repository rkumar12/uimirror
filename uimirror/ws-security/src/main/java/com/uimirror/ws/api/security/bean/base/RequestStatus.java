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
package com.uimirror.ws.api.security.bean.base;

/**
 * <p>This will contain the request served status.</p>
 * <ul>
 * 	<li><code>{@link RequestStatus#REQUESTED}</code> Specifies, when Web service received a call</li>
 * <li><code>{@link RequestStatus#INAVLID}</code> Specifies, when Web service received a call but it was invalid because of the data or service he is looking for</li>
 * <li><code>{@link RequestStatus#COMPLETED}</code> Specifies, when Web service received a call and served the content without any error</li>
 * <li><code>{@link RequestStatus#INCOMPLETE}</code> Specifies, when Web service received a call but due to some reason its incomplete</li>
 * </ul> 
 * @author Jayaram
 */
public enum RequestStatus {

	REQUESTED("1"), 
	INAVLID("2"), 
	COMPLETED("3"),
	INCOMPLETE("4");
	
	private String status;
	
	private RequestStatus(String status) {
		this.status = status;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	@Override
    public String toString() {
    	return this.getStatus();
    } 

    public static RequestStatus getEnum(String status) {
    	if(status == null)
    		throw new IllegalArgumentException("Status Can't be empty");
    	for(RequestStatus v : values())
    		if(status.equalsIgnoreCase(v.getStatus())) return v;
    	throw new IllegalArgumentException("No Status Found");
    }
}

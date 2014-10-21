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
package com.uimirror.core.user;

/**
 * Identifies possible status for the account
 * {@link AccountStatus#ACTIEVE} or {@link AccountStatus#BLOCKED} 
 * @author Jay
 */
public enum AccountStatus {

	ACTIVE("A"),
	BLOCKED("B");
	
	private final String status;
	
	private AccountStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
	@Override
    public String toString() {
    	return this.getStatus();
    } 
	
	public static AccountStatus getEnum(String status) {
    	if(status == null)
    		throw new IllegalArgumentException("Account Status Can't be empty");
    	for(AccountStatus v : values())
    		if(status.equalsIgnoreCase(v.getStatus())) return v;
    	return null;
    }
	
}

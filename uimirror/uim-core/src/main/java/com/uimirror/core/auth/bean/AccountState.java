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
 * This identifier for the possible state of an account
 * such as {@link AccountState#DISABLED} or {@link AccountState#ENABLED}
 * If account is created newly and not yet verified then it will have {@link AccountState#NEW}
 * remember, new account will have maximum of 24 hrs validity.
 *   
 * @author Jay
 */
public enum AccountState {
	
	NEW("N"),
	DISABLED("D"),
	ENABLED("E");
	
	private String state;
	
	private AccountState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}

	@Override
    public String toString() {
    	return this.getState();
    } 

    public static AccountState getEnum(String state) {
    	if(state == null)
    		throw new IllegalArgumentException("Account State Can't be empty");
    	for(AccountState v : values())
    		if(state.equalsIgnoreCase(v.getState())) return v;
    	throw new IllegalArgumentException("No Account State Found");
    }
}

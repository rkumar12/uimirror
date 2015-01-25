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
package com.uimirror.sso;

import com.uimirror.core.Constants;

/**
 * <p>It holds all the possible role available for the API </p>
 * @author Jayaram
 */
@Deprecated
public enum Role {
	ADMIN("1"), 
    PUBLIC("2"), 
    CONTRIBUTOR("3");
    
    private final String role;
    private final String descriotion;
 
    Role(String role) {
    	this.role = role;
    	this.descriotion = Constants.EMPTY;
    }
    
    Role(String role, String description) {
    	this.role = role;
    	this.descriotion = description;
    }

    public String getRole() {
    	return this.role;
    }
    
    public String getDescription() {
    	return this.descriotion;
    }

    @Override
    public String toString() {
    	return this.getRole();
    } 

    public static Role getEnum(String role) {
    	if(role == null)
    		throw new IllegalArgumentException("Role Can't be empty");
    	for(Role v : values())
    		if(role.equalsIgnoreCase(v.getRole())) return v;
    	throw new IllegalArgumentException("No Role Found");
    }
}

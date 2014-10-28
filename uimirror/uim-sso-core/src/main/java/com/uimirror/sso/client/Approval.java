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
package com.uimirror.sso.client;

/**
 * Identifies possible approval or deny for the client authorization by the user.
 * {@link Approval#APPROVE} will approve the client
 * {@link Approval#DENY} will deny the client
 * @author Jay
 */
public enum Approval {

	APPROVE("A"),
	DENY("D");
	
	private final String approval;
	
	private Approval(String scope) {
		this.approval = scope;
	}
	
	public String getApproval() {
		return approval;
	}

	@Override
    public String toString() {
    	return this.getApproval();
    } 
	
	public static Approval getEnum(String approval) {
    	if(approval == null)
    		throw new IllegalArgumentException("Approval can't be empty");
    	for(Approval v : values())
    		if(approval.equalsIgnoreCase(v.getApproval())) return v;
    	return null;
    }
	
}

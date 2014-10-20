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
package com.uimirror.account.user.form;

/**
 * verify source suggests from which origin this verification came from.
 * @author Jayaram
 */
public enum VerifySource {

	MAIL("M"),
	WEB("W");
	
	private final String source;
	
	private VerifySource(String status) {
		this.source = status;
	}
	
	public String getSource() {
		return source;
	}

	@Override
    public String toString() {
		return this.getSource();
	}
	
	public static VerifySource getEnum(String source){
		if(source == null)
			throw new IllegalArgumentException("Request VerifySource can't be empty");
		for(VerifySource v: values())
			if(source.equalsIgnoreCase(v.getSource())) return v;
		throw new IllegalArgumentException("No VerifySource Found");
	}
	
}

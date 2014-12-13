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
 * Contains the {@link ReachTypeOfView} types such as {@link #SHOP} which will be 
 * Show the basic shop dashboard.
 * {@link #USER} will show the user promo dashboard.
 * 
 * @author Jay
 */
public enum ReachTypeOfView {
	
	SHOP(0),
	USER(1);

	private final int code;
	
	private ReachTypeOfView(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	@Override
    public String toString() {
    	return Integer.toString(this.getCode());
    } 
	
	public static ReachTypeOfView getEnum(int code) {
    	for(ReachTypeOfView v : values())
    		if(code == v.getCode()) return v;
    	return null;
    }

}

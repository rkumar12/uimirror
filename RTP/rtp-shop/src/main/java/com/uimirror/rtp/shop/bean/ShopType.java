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
package com.uimirror.rtp.shop.bean;


/**
 * Suggests type of shop it has 
 * for example a shop which has been open for the sell will be treat as
 * {@link #OPEN_FOR_SELL} else {@link #OPEN_FOR_BRANCH}
 * @author Jay
 */
public enum ShopType {

	OPEN_FOR_SELL("O"),
	OPEN_FOR_BRANCH("B");
	
	private final String type;
	
	private ShopType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	@Override
    public String toString() {
    	return this.getType();
    } 
	
	public static ShopType getEnum(String status) {
    	if(status == null)
    		throw new IllegalArgumentException("Type Can't be empty");
    	for(ShopType v : values())
    		if(status.equalsIgnoreCase(v.getType())) return v;
    	return null;
    }
}

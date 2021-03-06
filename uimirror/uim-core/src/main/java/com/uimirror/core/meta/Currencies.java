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
package com.uimirror.core.meta;

/**
 * @author Jay
 */
public enum Currencies {
	
	RUPEE("Rs"),
	DOLLAR("USD");
	
	private final String currency;
	
	Currencies(String currency){
		this.currency = currency;
	}
	
	public final String getCurrency()
    {
        return currency;
    }
	
	@Override
	public String toString(){
		return currency;
	}
	
	
	public static Currencies getEnum(String currency){
		if(null == currency)
			return null;
		for(Currencies cur: Currencies.values()){
			if(currency.equalsIgnoreCase(cur.getCurrency()))
				return cur;
		}
		return null;
	}

}

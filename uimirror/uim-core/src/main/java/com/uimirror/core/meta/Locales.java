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
public enum Locales {
	
	ENGLISH("en"),
	ENGLISH_UK("en_uk"),
	ENHLISH_US("en_us"),
	HINDI("hn_in");
	
	private final String locale;
	
	Locales(String locale){
		this.locale = locale;
	}
	
	public String getLocale(){
		return locale;
	}
	
	@Override
	public String toString(){
		return locale;
	}
	
	public static Locales getEnum(String locale){
		if(null == locale)
			return null;
		for(Locales loc: Locales.values()){
			if(locale.equalsIgnoreCase(loc.getLocale()))
				return loc;
		}
		return null;
	}

}

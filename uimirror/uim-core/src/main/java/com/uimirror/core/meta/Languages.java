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
 * Different languages available.
 * @author Jay
 */
//TODO issue #11
public enum Languages {
	
	ENGLISH_US("en_US", "English(US)", 1),
	ENGLISH("en","English", 2),
	ENGLISH_UK("en_UK","English(UK)", 3),
	HINDI("hi_IN","Hindi(India)", 4);
	
	private final String locale;
	private final int id;
	private final String displayName;
	
	Languages(String locale, String displayName, int id){
		this.locale = locale;
		this.id = id;
		this.displayName = displayName;
	}

	public String getLocale() {
		return locale;
	}

	public int getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public String toString(){
		return "[id: "+id+", locale:"+ locale+", Display name: "+displayName+"]";
	}
	
	/**
	 * Finds the Enum by the display name Such as 1
	 * @param id on which basics it will be searched
	 * @return found enum of type {@link Languages} else <code>null</code>
	 */
	public Languages getById(int id){
		for(Languages lang : Languages.values()){
			if(id == lang.getId())
				return lang;
		}
		return null;
	}
	
	/**
	 * Finds the Enum by the display name Such as en_US
	 * @param locale on which basics it will be searched
	 * @return found enum of type {@link Languages} else <code>null</code>
	 */
	public Languages getByLocale(String locale){
		if(null == locale)
			return null;
		for(Languages lang: Languages.values()){
			if(locale.equalsIgnoreCase(lang.getLocale()))
				return lang;
		}
		return null;
	}
	
	/**
	 * Finds the Enum by the display name Such as Hindi(India)
	 * @param displayName on which basics it will be searched
	 * @return found enum of type {@link Languages} else <code>null</code>
	 */
	public Languages getByName(String displayName){
		if(null == displayName)
			return null;
		for(Languages lang: Languages.values()){
			if(displayName.equalsIgnoreCase(lang.getDisplayName()))
				return lang;
		}
		return null;
	}

}

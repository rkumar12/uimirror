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
 * Contains the education type information such 
 * as schooling, graduate, post graduate and doctorate
 * @author Jay
 */
public enum EducationType {

	SCHOOL("S"),
	GRADUATE("G"),
	POSTGRADUATE("PG"),
	DOCTRATE("DR");
	
	private final String type;
	
	EducationType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	@Override
	public String toString(){
		return type;
	}
	
	/**
	 * Get the education type based on the type specified
	 * @param type on which search will happen
	 * @return EducationType if found else <code>null</code> 
	 */
	public static EducationType getEnum(String type){
		if(null == type)
			return null;
		for(EducationType t : EducationType.values()){
			if(type.equalsIgnoreCase(t.getType()))
				return t;
		}
		return null;
	}
}

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
package com.uimirror.core.bean;


/**
 * @author Jay
 */
public enum Gender {

	MALE("M"),
	FEMALE("F"),
	TRANSGENDER("T"),
	BISEXUAL("B");
	
	private final String gender;
	
	private Gender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	@Override
    public String toString() {
    	return this.getGender();
    } 
	
	public static Gender getEnum(String gender) {
    	if(gender == null)
    		throw new IllegalArgumentException("Gender can't be empty");
    	for(Gender v : values())
    		if(gender.equalsIgnoreCase(v.getGender())) return v;
    	return null;
    }
}

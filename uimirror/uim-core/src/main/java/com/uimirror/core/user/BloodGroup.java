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
 * Container of the different available blood groups.
 * @author Jay
 */
public enum BloodGroup {
	
	NOTSPECIFIED("NS"),
	OPOSITIVE("O+"),
	ONEGATIVE("O-"),
	APOSITIVE("A+"),
	ANEGATIVE("A-"),
	BPOSITIVE("B+"),
	BNEGATIVE("B-"),
	ABPOSITIVE("AB+"),
	ABNEGATIVE("AB-");
	
	BloodGroup(String group){
		this.group = group;
	}
	
	private final String group;

	public String getGroup() {
		return group;
	}
	
	@Override
	public String toString(){
		return group;
	}
	
	/**
	 * Get the Enum value from the provided string,
	 * Iterates over the enums and find the more appropriate 
	 * matching Enum
	 * @param group which will be compared to the enum value
	 * @return {@link BloodGroup} if found else <code>null</code>
	 */
	public static BloodGroup getEnum(String group){
		if(group == null)
			return null;
		for(BloodGroup bloodGroup : BloodGroup.values()){
			if(group.equalsIgnoreCase(bloodGroup.getGroup())){
				return bloodGroup;
			}
		}
		return null;
	}

}

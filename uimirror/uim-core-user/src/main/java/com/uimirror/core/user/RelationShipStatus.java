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
 * Captures the relations ship status such as 
 * {@link #SINGLE}, {@link #MARRIED}
 * 
 * @author Jay
 */
public enum RelationShipStatus {
	
	NONE("NS"),
	SINGLE("S"),
	INARELATIONSHIP("IR"),
	ENGAGED("E"),
	MARRIED("M"),
	COMMPLICATED("C"),
	INAOPENRELATION("IOR"),
	WIDOW("W"),
	SEPERATED("SP"),
	DIVORCED("D");
	
	RelationShipStatus(String status){
		this.status = status;
	}
	
	private final String status;

	public String getStatus() {
		return status;
	}
	
	@Override
	public String toString(){
		return status;
	}
	
	/**
	 * Get the Enum value from the provided string,
	 * Iterates over the enums and find the more appropriate 
	 * matching Enum
	 * @param status which will be compared to the enum value
	 * @return {@link RelationShipStatus} if found else <code>null</code>
	 */
	public static RelationShipStatus getEnum(String status){
		if(status == null)
			return null;
		for(RelationShipStatus rs : RelationShipStatus.values()){
			if(status.equalsIgnoreCase(rs.getStatus()))
				return rs;
		}
		return null;
	}
	
}

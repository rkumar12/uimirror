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
 * @author Jay
 */
public enum AddressType {
	
	PRESENT("P"),
	PERMANENT("PE"),
	VISITED("V");
	
	private String type;
	
	 AddressType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
	 
	 public static AddressType getEnum(String type){
		 if(type == null)
			 return null;
		 for(AddressType add : AddressType.values()){
			 if(type.equalsIgnoreCase(add.getType()))
				 return add;
		 }
		 return null;
	 }
	 
	 @Override
		public String toString(){
			return type;
		}

}

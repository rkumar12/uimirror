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
public enum RelationShipType {
	
	FATHER("F"),
	MOTHER("M"),
	SPOUSE("S");
	
	private String relationType;
	
	RelationShipType(String relationType){
		this.relationType = relationType;
	}
	
	public String getRelationType() {
		return relationType;
	}

	public static RelationShipType getEnum(String type){
		 if(type == null)
			 return null;
		 for(RelationShipType rel : RelationShipType.values()){
			 if(type.equalsIgnoreCase(rel.getRelationType()))
				 return rel;
		 }
		 return null;
	 }
	 
	 @Override
		public String toString(){
			return relationType;
		}

}

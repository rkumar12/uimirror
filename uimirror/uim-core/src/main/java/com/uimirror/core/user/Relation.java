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
 * Conatins the relation details such as father, mother
 * @author Jay
 */
//TODO update all avaliable relations issue #12
public enum Relation {
	FATHER("F"),
	MOTHER("M"),
	SISTER("S");
	
	private final String relation;
	
	Relation(String relation) {
		this.relation = relation;
	}

	public String getRelation() {
		return relation;
	}
	
	@Override
	public String toString(){
		return relation;
	}
	
	/**
	 * Gets the relation enum based on the relation
	 * @param relation on which it will be searched
	 * @return {@link Relation} if found else <code>null</code>
	 */
	public static Relation getEnum(String relation){
		if(null == relation)
			return null;
		for(Relation r : Relation.values()){
			if(relation.equalsIgnoreCase(r.getRelation()))
				return r;
		}
		return null;
	}

}

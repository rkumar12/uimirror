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
package com.uimirror.core.mongo;

/**
 * @author Jay
 */
public class BasicMongoOperators {
	
	//Prevent instance
	private BasicMongoOperators(){
		//NOP
	}
	public static final String SET = "$set";
	public static final String LESSTHANEQUEAL = "$lte";
	public static final String GREATERTHANEQUEAL = "$gte";
	public static final String GREATERTHAN = "$gt";
	public static final String ELEMENT_MATCH = "$elemMatch";
	public static final String PULL = "$pull";
	public static final String EXISTS = "$exists";
	public static final String EACH = "$each";
	public static final String ADD_TO_SET = "$addToSet";
	public static final String NOT = "$not";
	public static final String NOT_EQUAL = "$ne";
	public static final String OR = "$or";
	public static final String INCREAMENT = "$inc";
	
	public static final String GEO_2SPEHERE_INDEX = "2dsphere";
	public static final String GEOMETERY = "$geometry";
	public static final String NEAR = "$near";
	public static final String MIN_DISTANCE = "$minDistance";
	public static final String MAX_DISTANCE = "$maxDistance";
}

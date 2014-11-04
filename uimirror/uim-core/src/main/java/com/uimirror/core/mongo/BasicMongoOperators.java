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
public interface BasicMongoOperators {
	String SET = "$set";
	String LESSTHANEQUEAL = "$lte";
	String GREATERTHANEQUEAL = "$gte";
	String GREATERTHAN = "$gt";
	String ELEMENT_MATCH = "$elemMatch";
	String PULL = "$pull";
	String EXISTS = "$exists";
	String EACH = "$each";
	String ADD_TO_SET = "$addToSet";
	String NOT = "$not";
	String NOT_EQUAL = "$ne";
	String OR = "$or";
	String INCREAMENT = "$inc";
	
	String GEO_2SPEHERE_INDEX = "2dsphere";
	String GEOMETERY = "$geometry";
	String NEAR = "$near";
	String MIN_DISTANCE = "$minDistance";
	String MAX_DISTANCE = "$maxDistance";
}

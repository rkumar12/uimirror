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
	String ELEMENT_MATCH = "$elemMatch";
	String LESS_THAN_EQUAL = "$lte";
	String PULL = "$pull";
	String EACH = "$each";
	String ADD_TO_SET = "$addToSet";
}

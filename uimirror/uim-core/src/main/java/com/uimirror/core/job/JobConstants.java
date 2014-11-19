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
package com.uimirror.core.job;

import com.uimirror.core.mongo.feature.BasicDBFields;

/**
 * @author Jay
 */
public class JobConstants extends BasicDBFields{

	//Prevent instance
	private JobConstants() {
		//NOP
	}
	
	public static final String NAME = "name";
	public static final String STATUS = "status";
	public static final String STARTED_ON = "started_on";
	public static final String COMPLETED_ON = "completed_on";
	public static final String MESSAGE = "message";
	public static final String DETAILS = "details";

}

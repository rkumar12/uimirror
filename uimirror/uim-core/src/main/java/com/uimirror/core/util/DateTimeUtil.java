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
package com.uimirror.core.util;

import java.time.Clock;
import java.time.ZonedDateTime;

/**
 * An Utility class that helps to get and manupulate the date time objects
 * @author Jay
 */
public class DateTimeUtil {

	/**
	 * Gets the system time w.r.t to UTC in EPOCH
	 * @return
	 */
	public static final long getCurrentSystemUTCEpoch(){
		return ZonedDateTime.now(Clock.systemUTC()).toEpochSecond();
	}

}

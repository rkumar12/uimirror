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
		return getCurrentUTCTime().toEpochSecond();
	}
	
	/**
	 * get the system time w.r.t UTC add the time interval in minutes and convert to EPOCH
	 * @param minutes
	 * @return
	 */
	public static final long addToCurrentUTCTimeConvertToEpoch(long minutes){
		return getCurrentUTCTime().plusMinutes(minutes).toEpochSecond();
	}
	
	/**
	 * get the system time w.r.t UTC minus the time interval in minutes and convert to EPOCH
	 * @param minutes
	 * @return
	 */
	public static final long minusToCurrentUTCTimeConvertToEpoch(long minutes){
		return getCurrentUTCTime().minusMinutes(minutes).toEpochSecond();
	}
	
	public static final ZonedDateTime getCurrentUTCTime(){
		return ZonedDateTime.now(Clock.systemUTC());
	}

}

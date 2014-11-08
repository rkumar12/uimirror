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
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.validator.routines.DateValidator;

/**
 * An Utility class that helps to get and manupulate the date time objects
 * @author Jay
 */
public class DateTimeUtil {
	

	private static final String DOB_FORMAT= "dd-MM-yyyy";
	private static final int AGE_LIMIT = 18;

	/**
	 * Gets the system time w.r.t to UTC in EPOCH
	 * @return the long representation
	 */
	public static final long getCurrentSystemUTCEpoch(){
		return getCurrentUTCTime().toEpochSecond();
	}
	
	/**
	 * Gets the system time w.r.t to UTC in {@link LocalDate}
	 * @return the long representation
	 */
	public static final LocalDate getCurrentSystemUTCDate(){
		return getCurrentUTCTime().toLocalDate();
	}
	
	/**
	 * get the system time w.r.t UTC add the time interval in minutes and convert to EPOCH
	 * @param minutes to be added
	 * @return the long representation
	 */
	public static final long addToCurrentUTCTimeConvertToEpoch(long minutes){
		return getCurrentUTCTime().plusMinutes(minutes).toEpochSecond();
	}
	
	/**
	 * get the system time w.r.t UTC minus the time interval in minutes and convert to EPOCH
	 * @param minutes to be subtracted
	 * @return the long representation
	 */
	public static final long minusToCurrentUTCTimeConvertToEpoch(long minutes){
		return getCurrentUTCTime().minusMinutes(minutes).toEpochSecond();
	}
	
	/**
	 * Get the current UTC time
	 * @return {@link ZonedDateTime} of the UTC
	 */
	public static final ZonedDateTime getCurrentUTCTime(){
		return ZonedDateTime.now(Clock.systemUTC());
	}
	
	
	/**
	 * validates date to dd-MM-yyyy format
	 * @param date which will be checked
	 * @return boolean <code>true</code> if the provided date is valid
	 */
	public static boolean isAValidDate(String date) {
		DateValidator validator = DateValidator.getInstance();
		return validator.isValid(date, DOB_FORMAT);
    }
	
	
	public static String[] splitDOB(String dob){
		String[] parts=null;
		if(isAValidDate(dob)){
			parts = dob.split("-");
		}
		return parts;
	}
	
	/**
	 * Validates if age is above 18 based on dob.
	 * @param dob that needs to be checked
	 * @return boolean <code>true</code> if date is above 18
	 */
	public static boolean isAgeAboveEighteen(String dob) {
		boolean result = Boolean.FALSE;
		LocalDate birthDay=LocalDate.parse(dob, DateTimeFormatter.ofPattern(DOB_FORMAT));
		Period period = Period.between(birthDay, getCurrentSystemUTCDate());
		
		if(period.getYears() >= AGE_LIMIT){
			result = Boolean.TRUE;
		}
		return result;
	}
	
}

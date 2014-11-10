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
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.apache.commons.validator.routines.DateValidator;

/**
 * An Utility class that helps to get and manupulate the date time objects
 * @author Jay
 */
public class DateTimeUtil {

	public static final String DOB_FORMAT_YYYY_MM_DD= "yyyy-MM-dd";
	private static final int AGE_LIMIT = 18;
	private static final DateTimeFormatter DTF_YYYY_MM_DD = DateTimeFormatter.ISO_LOCAL_DATE.withLocale(Locale.US);
//	private static final DateTimeFormatter DTF_YYYY_MM_DD = DateTimeFormatter.ofPattern("uuuu MM dd", Locale.US);

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
	 * validates date to yyyy-mm-dd format
	 * @param date which will be checked
	 * @return boolean <code>true</code> if the provided date is valid
	 */
	public static boolean isAValidDate(String date) {
		boolean valid = Boolean.FALSE;
		valid = isAValidDate(date, DOB_FORMAT_YYYY_MM_DD);
		return valid;
    }

	/**
	 * validates date to dd-MM-yyyy format
	 * @param date which will be checked
	 * @return boolean <code>true</code> if the provided date is valid
	 */
	public static boolean isAValidDate(String date, String format) {
		DateValidator validator = DateValidator.getInstance();
		boolean valid = Boolean.FALSE;
		valid = validator.isValid(date, format);
		return valid;
	}
	
	/**
	 * Converts the provided string usning the provided formatter
	 * if no valid input, then return null
	 * Make Sure input date is in 2011-12-03, YYYY-MM-DD
	 * @param str needs to parse
	 * @param formater using which date will be parsed
	 * @return localeDate
	 */
	public static LocalDate stringToDate(String str, DateTimeFormatter formater){
		if(formater == null || str == null)
			throw new IllegalArgumentException("Can't parse the Date");
		LocalDate dt = null;
		try{
			dt = LocalDate.parse(str);
		}catch(DateTimeParseException e){
			//NOP
		}
		return dt;
	}

	/**
	 * Converts the given string into the LocaleDate using default {@link #DTF_YYYY_MM_DD}
	 * Make Sure input date is in 2011-12-03, YYYY-MM-DD
	 * @param str needs to parse
	 * @return LocalDate
	 */
	public static LocalDate stringToDate(String str){
		return stringToDate(str, DTF_YYYY_MM_DD);
	}

	/**
	 * Converts the given date into {@link LocalDate} with given format and given {@link Locale}
	 * Make Sure input date is in 2011-12-03, YYYY-MM-DD
	 * @param str needs to parse
	 * @param format needs to translate
	 * @param locale to use
	 * @return a localDate
	 */
	public static LocalDate stringToDate(String str, String format, Locale locale){
		if(locale == null){
			locale = Locale.US;
		}
		DateTimeFormatter dtf = null;
		if(format == null)
			dtf = DTF_YYYY_MM_DD;
		else
			dtf = DateTimeFormatter.ofPattern(format);
		dtf = dtf.withLocale(locale);
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format, locale);
		return stringToDate(str, dtf);
	}

	/**
	 * Converts the given date into {@link LocalDate} with given format and given {@link Locale}
	 * Make Sure input date is in 2011-12-03, YYYY-MM-DD
	 * @param str needs to parse
	 * @param format needs to translate
	 * @param locale to use
	 * @return a localDate
	 */
	public static LocalDate stringToDate(String str, String format, String locale){
		Locale lo = null;
		if(locale != null){
			lo = Locale.forLanguageTag(locale);
		}
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format, locale);
		return stringToDate(str, format, lo);
	}

	/**
	 * Converts the given date into {@link LocalDate} with given format and default {@link Locale#US}
	 * Make Sure input date is in 2011-12-03, YYYY-MM-DD
	 * @param str needs to parse
	 * @param format needs to translate
	 * @return Localedate
	 */
	public static LocalDate stringToDate(String str, String format){
		return stringToDate(str, format, Locale.US);
	}
	
	/**
	 * Validates if age is above 18 based on dob.
	 * @param dob that needs to be checked
	 * @param dtf a date formatter to be used, if null use default one
	 * @return boolean <code>true</code> if date is above 18
	 */
	private static boolean isAgeAboveEighteen(String dob, DateTimeFormatter dtf) {
		boolean result = Boolean.FALSE;
		if(dtf == null)
			dtf = DTF_YYYY_MM_DD;
		LocalDate birthDay = stringToDate(dob, dtf);
		Period period = Period.between(birthDay, getCurrentSystemUTCDate());
		
		if(period.getYears() >= AGE_LIMIT){
			result = Boolean.TRUE;
		}
		return result;
	}

	/**
	 * Validates if age is above 18 based on dob.
	 * @param dob that needs to be checked
	 * @param locale to check against the locale
	 * @return boolean <code>true</code> if date is above 18
	 */
	public static boolean isAgeAboveEighteen(String dob, Locale locale) {
		DateTimeFormatter dtf= DTF_YYYY_MM_DD;
		locale = (locale == null) ? Locale.US : locale;
		dtf = dtf.withLocale(locale);
		return isAgeAboveEighteen(dob, dtf);
	}
	
}

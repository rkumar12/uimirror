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
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
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

	/**
	 * Checks if the provided epoch time is after the current time+ the amount of time added
	 * @param epoch which needs to be checked
	 * @param approachingTimeInMinute to be added
	 * @return <code>true</code> if its more else <code>false</code>
	 */
	public static final boolean isCurrentUTCApproachingBy(long epoch, long approachingTimeInMinute){
		long dif = ChronoUnit.MINUTES.between(getCurrentUTCTime().toInstant(), Instant.ofEpochSecond(epoch));
		return dif >= 0 && dif <= approachingTimeInMinute;
	}

	/**
	 * Checks the provided epoch time with current time in utc format.
	 * if the current time is greater than the provided time then its expired else valid.
	 * @param epoch which needs to be check against current time
	 * @return true if expired
	 */
	public static final boolean isExpired(long epoch){
		return getCurrentUTCTime().toInstant().isAfter(Instant.ofEpochSecond(epoch));
	}
	
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
	 * validates date to be in yyyy-mm-dd format
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
	 * @param format format to which it will be transformed
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
	 * Converts the given date into {@link LocalDate} with given format and given {@link Locale}
	 * Make Sure input date is in 2011-12-03, YYYY-MM-DD
	 * into a string
	 * @param ldt needs to parse
	 * @param format needs to translate
	 * @param locale to use
	 * @return a string representaion of the ldt
	 */
	public static String localDateToString(LocalDate ldt, String format, String locale){
		Locale lo = null;
		DateTimeFormatter dtf = null;
		if(locale != null){
			lo = Locale.forLanguageTag(locale);
		}
		lo = lo == null ? Locale.US : lo;
		if(format != null){
			dtf = DateTimeFormatter.ofPattern(format, lo);
		}
		dtf = dtf == null ? DateTimeFormatter.ISO_LOCAL_DATE.withLocale(lo) : dtf;
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format, locale);
		return ldt.format(dtf);
	}

	
	/**
	 * Validates if age is above 18 based on dob.
	 * @param dob that needs to be checked
	 * @param dtf a date formatter to be used, if null use default one
	 * @return boolean <code>true</code> if date is above 18
	 */
	private static boolean isAgeAboveEighteen(String dob, DateTimeFormatter dtf) {
		if(dtf == null)
			dtf = DTF_YYYY_MM_DD;
		LocalDate birthDay = stringToDate(dob, dtf);
		return isAgeAboveEighteen(birthDay);
	}
	
	/**
	 * Validates from the given localDate is more than 18 or not
	 * @param date which will be validated
	 * @return if its more than 18 years
	 */
	public static boolean isAgeAboveEighteen(LocalDate date) {
		return isDateMoreThan(date, AGE_LIMIT);
	}
	
	/**
	 * Validates from the given localDate is more than specified years 
	 * @param date which will be validated
	 * @param years number of years its more than
	 * @return <code>true</code> if it valid else <code>false</code>
	 */
	public static boolean isDateMoreThan(LocalDate date, int years) {
		boolean result = Boolean.FALSE;
		//TODO potential bug of checking it should be with time zone
		Period pd = Period.between(date, getCurrentSystemUTCDate());
		if(pd.getYears() >= years){
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

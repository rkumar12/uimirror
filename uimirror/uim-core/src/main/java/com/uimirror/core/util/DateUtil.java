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

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.validator.routines.DateValidator;
/**
 * An Utility class that helps to get to validate Date objects.
 * @author Jay
 */
public class DateUtil {
	
	private static final String DATE_FORMAT= "dd-MM-yyyy";
	private static final int AGE_LIMIT = 18;
	
	private DateUtil(){
		
	}
	
	public static boolean isAValidDate(String date) {
		DateValidator validator = DateValidator.getInstance();
		return validator.isValid(date, DATE_FORMAT);
    }
	
	public static boolean isAgeAboveEighteen(String dob) {
		boolean result = Boolean.FALSE;
		Date date=DateValidator.getInstance().validate(dob,DATE_FORMAT);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		 
		Period period = Period.between(birthday, today);
		System.out.println(period.getYears());
		if(period.getYears() >= AGE_LIMIT){
			result = Boolean.TRUE;
		}
		return result;
	}

}

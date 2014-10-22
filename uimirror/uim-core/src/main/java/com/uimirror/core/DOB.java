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
package com.uimirror.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.uimirror.core.user.UserDBFields;

/**
 * Gets the DOB of the user
 * @author Jay
 */
public class DOB {

	private int date;
	private int month;
	private int year;
	private String dob;
	
	public DOB(int date, int month, int year) {
		super();
		this.date = date;
		this.month = month;
		this.year = year;
	}
	
	public DOB(String dob) {
		super();
		this.dob = dob;
		//Write logic for the DOB split
	}

	public int getDate() {
		return date;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public String getDob() {
		return dob;
	}
	public DOB getDobInDate() {
		//TODO form the date here
		return null;
	}
	

	/**
	 * Get the map for dob
	 * @return
	 */
	public Map<String, Object> toMap(){
		Map<String, Object> map = new LinkedHashMap<String, Object>(8);
		map.put(UserDBFields.DATE_OF_BIRTH_DATE, getDate());
		map.put(UserDBFields.DATE_OF_BIRTH_MONTH, getMonth());
		map.put(UserDBFields.DATE_OF_BIRTH_YEAR, getYear());
		return map;
	}

	/**
	 * Get the map for dob
	 * @return
	 */
	public static DOB initFromMap(Map<String, Object> map){
		if(CollectionUtils.isEmpty(map))
			return null;
		int date = 0;
		int month = 0;
		int year = 0;
		if(map.get(UserDBFields.DATE_OF_BIRTH_DATE) != null)
			date = (int)map.get(UserDBFields.DATE_OF_BIRTH_DATE);
		if(map.get(UserDBFields.DATE_OF_BIRTH_MONTH) != null)
			month = (int)map.get(UserDBFields.DATE_OF_BIRTH_MONTH);

		if(map.get(UserDBFields.DATE_OF_BIRTH_YEAR) != null)
			year = (int)map.get(UserDBFields.DATE_OF_BIRTH_YEAR);
		return new DOB(date, month, year);
	}

	@Override
	public String toString() {
		return "DOB [date=" + date + ", month=" + month + ", year=" + year
				+ "]";
	}
	
}

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

import static com.uimirror.core.user.DOBDBFields.*;
import static com.uimirror.core.user.UserDBFields.DATE_OF_BIRTH;
import static com.uimirror.core.user.UserDBFields.META_INFO;
import java.time.LocalDate;
import java.util.Map;
import java.util.WeakHashMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.uimirror.core.user.MetaInfoDBFields;
import com.uimirror.core.util.DateTimeUtil;

/**
 * Gets the DOB of the user
 * @author Jay
 */
public class DOB{

	private final String dob;
	private final String format;
	private final Map<String, Integer> fragments;
	private final LocalDate dobInDate;
	private final String locale;

	/**Validates the given DOB to be in yyyy-mm-dd format
	 * @return
	 */
	public boolean isValidFormat() {
		return DateTimeUtil.isAValidDate(dob);
	}
	
	/**
	 * Get the map for dob
	 * @return {@link Map} representation of the state
	 */
	public Map<String, Object> toMap(){
		if(!isValidFormat()){
			return null;
		}
		Map<String, Object> map = new WeakHashMap<String, Object>(5);
		if(StringUtils.hasText(dob))
			map.put(DATE_OF_BIRTH, getDob());
		if(StringUtils.hasText(format))
			map.put(DOB_FORMAT, getFormat());
		return map;
	}

	/**
	 * Get the map for dob, which has depedancy on user meta info
	 * @param map from which state will be presumed
	 * @return object with state from the provided {@link Map}
	 */
	@SuppressWarnings("unchecked")
	public static DOB initFromMap(Map<String, Object> map){
		if(CollectionUtils.isEmpty(map))
			return null;
		Map<String, Object> dobMap = (Map<String, Object>)map.get(DATE_OF_BIRTH);
		if(dobMap == null)
			return null;
		String dob = (String)dobMap.get(DATE_OF_BIRTH);
		String format = (String)dobMap.get(DOB_FORMAT);
		Map<String, Object> metaMap = (Map<String, Object>)map.get(META_INFO);
		String locale = metaMap == null ? null : (String)metaMap.get(MetaInfoDBFields.LOCALE);
		return new DOBBuilder(dob).addFormat(format).addLocale(locale).build();
	}
	
	public static class DOBBuilder implements Builder<DOB>{
		private String dob;
		private String format;
		private Map<String, Integer> fragments;
		private String locale;
		private LocalDate dobInDate;
		
		public DOBBuilder(String dob){
			this.dob = dob;
		}
		
		public DOBBuilder addFormat(String format){
			this.format = format;
			return this;
		}

		public DOBBuilder addLocale(String locale){
			this.locale = locale;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public DOB build() {
			LocalDate ldt = DateTimeUtil.stringToDate(dob, format, locale);
			this.dobInDate = ldt;
			if(ldt != null){
				fragments = new WeakHashMap<String, Integer>(8);
				fragments.put(DATE_OF_BIRTH_DATE, ldt.getDayOfMonth());
				fragments.put(DATE_OF_BIRTH_MONTH, ldt.getMonthValue());
				fragments.put(DATE_OF_BIRTH_YEAR, ldt.getYear());
			}
			return new DOB(this);
		}
	}
	
	private DOB(DOBBuilder builder){
		this.dob = builder.dob;
		this.format = builder.format;
		this.fragments = builder.fragments;
		this.dobInDate = builder.dobInDate;
		this.locale = builder.locale;
	}
	
	public int getDate() {
		return fragments != null ? fragments.get(DATE_OF_BIRTH_DATE) : 0;
	}

	public int getMonth() {
		return fragments != null ? fragments.get(DATE_OF_BIRTH_MONTH) : 0;
	}

	public int getYear() {
		return fragments != null ? fragments.get(DATE_OF_BIRTH_YEAR) : 0;
	}

	public String getDob() {
		return dob;
	}
	
	public String getFormatedDob(){
		if(this.dobInDate == null)
			return dob;
		else{
			return DateTimeUtil.localDateToString(dobInDate, format, locale);
		}
	}
	
	public boolean isMoreThanighteen(){
		return DateTimeUtil.isAgeAboveEighteen(dobInDate);
	}

	public String getFormat() {
		return format;
	}

	public Map<String, Integer> getFragments() {
		return fragments;
	}

	@Override
	public String toString() {
		return "DOB [dob=" + dob + ", format=" + format + ", fragments="
				+ fragments + "]";
	}
	
}

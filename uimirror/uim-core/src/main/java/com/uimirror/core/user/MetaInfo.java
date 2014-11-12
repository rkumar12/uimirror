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
package com.uimirror.core.user;

import static com.uimirror.core.user.MetaInfoDBFields.CURRENCY;
import static com.uimirror.core.user.MetaInfoDBFields.LOCALE;
import static com.uimirror.core.user.MetaInfoDBFields.TIMEZONE;

import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;

/**
 * User's Meta Info Such as: currency, locale and time zone
 * @author Jay
 */
public class MetaInfo {
	
	private String locale;
	private String currency;
	private String timeZone;
	
	/**
	 * Get the map for dob, which has depedancy on user meta info
	 * @param map from which state will be presumed
	 * @return object with state from the provided {@link Map}
	 */
	public static MetaInfo initFromMap(Map<String, Object> map){
		if(CollectionUtils.isEmpty(map))
			throw new IllegalArgumentException("Can't restore from the given map");
		return new MetaBuilder((String)map.get(LOCALE)).
				addCurrency((String)map.get(CURRENCY)).
				addTimeZone((String)map.get(TIMEZONE)).
				build();
	}
	
	/**
	 * Persist the state of the object into the map
	 * @return populated map to be store, if nthing to persist then returns null
	 */
	public Map<String, Object> toMap(){
		Map<String, Object> rs = new WeakHashMap<String, Object>(8);
		if(StringUtils.hasText(locale))
			rs.put(LOCALE, locale);
		if(StringUtils.hasText(currency))
			rs.put(CURRENCY, currency);
		if(StringUtils.hasText(timeZone))
			rs.put(TIMEZONE, timeZone);
		return CollectionUtils.isEmpty(rs) ? null : rs;
	}
	
	public static class MetaBuilder implements Builder<MetaInfo>{
		private String locale;
		private String currency;
		private String timeZone;
		public MetaBuilder(String locale){
			this.locale = locale;
		}
		public MetaBuilder addCurrency(String currency){
			this.currency = currency;
			return this;
		}
		
		public MetaBuilder addTimeZone(String timeZone){
			this.timeZone = timeZone;
			return this;
		}
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public MetaInfo build() {
			return new MetaInfo(this);
		}
		
	}
	
	public MetaInfo(MetaBuilder builder){
		if(builder == null)
			throw new java.lang.IllegalArgumentException("Can't create the state from the given source");
		this.currency = builder.currency;
		this.timeZone = builder.timeZone;
		this.locale = builder.locale;
	}

	public String getLocale() {
		return locale;
	}

	public String getCurrency() {
		return currency;
	}

	public String getTimeZone() {
		return timeZone;
	}

	@Override
	public String toString() {
		return "MetaInfo [locale=" + locale + ", currency=" + currency
				+ ", timeZone=" + timeZone + "]";
	}

}

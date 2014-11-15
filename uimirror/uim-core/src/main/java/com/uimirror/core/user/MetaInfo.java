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

import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
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
		String locale = (String)map.get(LOCALE);
		String currency = (String)map.get(CURRENCY);
		String timeZone = (String)map.get(TIMEZONE);
		MetaBuilder builder = new MetaBuilder(timeZone);
		if(StringUtils.hasText(locale)) {
			String[] localeParts = locale.split("_");
			if( localeParts!= null && localeParts.length > 1){
				builder.addLang(localeParts[0]);
				builder.addCountryCode(localeParts[1]);
			}
		} 
		builder.addCurrency(currency);
		return builder.build();
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
			rs.put(TIMEZONE, TimeZone.getTimeZone(timeZone).toZoneId().toString());
		return CollectionUtils.isEmpty(rs) ? null : rs;
	}
	
	public static class MetaBuilder implements Builder<MetaInfo>{
		private String lang;
		private String timeZoneName;
		private String countryCode;
		private String currency;
		
		public MetaBuilder(String timeZoneName){
			this.timeZoneName = timeZoneName;
		}
		
		public MetaBuilder addLang(String lang){
			this.lang = lang;
			return this;
		}
		public MetaBuilder addCountryCode(String countryCode){
			this.countryCode = countryCode;
			return this;
		}

		public MetaBuilder addCurrency(String currency){
			this.currency = currency;
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
			throw new IllegalArgumentException("Can't create the state from the given source");
		Locale loc = new Locale(builder.lang, builder.countryCode);
		this.locale = loc.toString();
		this.timeZone = TimeZone.getTimeZone(builder.timeZoneName).getID();
		if(builder.currency == null){
			try{
				this.currency = Currency.getInstance(loc).getCurrencyCode();
			}catch(IllegalArgumentException e){
				//NOP
			}
		}else{
			this.currency = builder.currency;
		}
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

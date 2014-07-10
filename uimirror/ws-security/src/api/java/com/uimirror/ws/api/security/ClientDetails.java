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
package com.uimirror.ws.api.security;

import java.io.Serializable;
import java.time.ZoneOffset;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;
import com.uimirror.util.Constants;
import com.uimirror.util.web.WebUtil;

/**
 * <p>Bean to hold all the client details
 * <p>Class has been marked as immutable, leaving the modifier as final to enable other users
 * to extend it, but please note, any body wants to extend it take proper care to make this as immutable
 * 
 * @author Jayaram
 */
public class ClientDetails implements Serializable{

	private static final long serialVersionUID = 7467080501618136784L;
	private final long privateKey;
	private final String name;
	private final String applicationUrl;
	private final String timezone;
	private final String loacle;
	private final String currency;
	
	/**
	 * <p>A constructor with the required fields to create an object</p>
	 * @param id
	 * @param name
	 * @param applicationUrl
	 */
	public ClientDetails(long id, String name, String applicationUrl) {
		super();
		this.privateKey = id;
		this.name = name;
		this.applicationUrl = applicationUrl;
		this.timezone = TimeZone.getTimeZone(ZoneOffset.UTC).getDisplayName();
		this.currency = Constants.EMPTY;
		this.loacle = Locale.ENGLISH.getDisplayName();
	}

	/**
	 * <p>A constructor with the fields to create an object</p>
	 * @param id
	 * @param name
	 * @param applicationUrl
	 * @param timezone
	 * @param loacle
	 * @param currency
	 */
	public ClientDetails(long id, String name, String applicationUrl, String timezone, String loacle, String currency) {
		super();
		this.privateKey = id;
		this.name = name;
		this.applicationUrl = applicationUrl;
		this.timezone = timezone;
		this.loacle = loacle;
		this.currency = currency;
	}
	/**
	 * <p>This will create a new instance with the time zone</p>
	 * @param timeZone in string format
	 * @return
	 */
	public ClientDetails addTimeZone(String timeZone){
		return new ClientDetails(this.privateKey, this.name, this.applicationUrl, TimeZone.getTimeZone(timeZone).getDisplayName(), this.loacle, this.currency);
	}
	
	/**
	 * <p>This will create a new instance with the locale</p>
	 * @param loacle in string format
	 * @return
	 */
	public ClientDetails addLoacle(String loacle){
		return new ClientDetails(this.privateKey, this.name, this.applicationUrl, this.timezone, Locale.forLanguageTag(loacle).getDisplayName(), this.currency);
	}
	
	/**
	 * <p>This will create a new instance with the currency</p>
	 * @param currency in string format
	 * @return
	 * @throws <code>{@link IllegalArgumentException}</code> in case currency is not a valid representation
	 */
	public ClientDetails addCurrency(String currency){
		return new ClientDetails(this.privateKey, this.name, this.applicationUrl, this.timezone, this.loacle, Currency.getInstance(currency).getSymbol());
	}
	
	/**
	 * <p>This will create a new instance with the updated url</p>
	 * @param url in string format wrt IEE standard
	 * @return
	 * @throws <code>{@link IllegalArgumentException}</code> in case url is not a valid representation or url specified without schema i.e protocol
	 */
	public ClientDetails updateUrl(String url){
		return new ClientDetails(this.privateKey, this.name, WebUtil.getUrl(url).toString(), this.timezone, this.loacle, this.currency);
	}
	/**
	 * <p>This will create a new instance with the update name </p>
	 * @param name
	 * @return
	 * @throws <code>{@link IllegalArgumentException}</code> in case name is empty or blank or <code>null</code>
	 */
	public ClientDetails updateName(String name){
		if(StringUtils.isBlank(name))
			throw new IllegalArgumentException("Provided Name is not Valid");
		return new ClientDetails(this.privateKey, name, this.applicationUrl, this.timezone, this.loacle, this.currency);
	}

	/**
	 * @return the privateKey
	 */
	public long getPrivateKey() {
		return privateKey;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the applicationUrl
	 */
	public String getApplicationUrl() {
		return applicationUrl;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @return the loacle
	 */
	public String getLoacle() {
		return loacle;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClientDetails [privateKey=" + privateKey + ", name=" + name
				+ ", applicationUrl=" + applicationUrl + ", timezone="
				+ timezone + ", loacle=" + loacle + ", currency=" + currency
				+ "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (privateKey ^ (privateKey >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientDetails other = (ClientDetails) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (privateKey != other.privateKey)
			return false;
		return true;
	}
	
}

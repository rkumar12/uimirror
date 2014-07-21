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
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.uimirror.ws.api.security.base.ClientDetails;
import com.uimirror.ws.api.security.base.SecurityFieldConstants;

/**
 * <p>Bean to hold all the client details of uimirror
 * <p>Class has been marked as immutable </p>
 * <code>{@link ClientDetails}</code>
 * @author Jayaram
 */
public final class UimClientDetails extends ClientDetails implements Serializable{


	private static final long serialVersionUID = 5424883781797485757L;

	/**
	 * @param id
	 * @param name
	 * @param applicationUrl
	 * @param timezone
	 * @param loacle
	 * @param currency
	 * @param createdOn
	 */
	private UimClientDetails(long id, String name, String applicationUrl,
			String timezone, String loacle, String currency, long createdOn) {
		super(id, name, applicationUrl, timezone, loacle, currency, createdOn);
	}

	/**
	 * @param name
	 * @param applicationUrl
	 */
	public UimClientDetails(String name, String applicationUrl) {
		super(name, applicationUrl);
	}
	
	/**
	 *<p>This will populate the Map from the details available</p> 
	 */
	public Map<String, String> serailizeToDoucmentMap() {
		
		Map<String, String> map = new HashMap<String, String>(7,1);
		if(this.getClientId() < 0l){
			throw new IllegalArgumentException("Can't do serailization as required field as of now is not yet present i.e id");
		}
		map.put(SecurityFieldConstants._ID, String.valueOf(this.getClientId()));
		map.put(SecurityFieldConstants._CLIENT_NAME, this.getName());
		map.put(SecurityFieldConstants._CLIENT_CREATED_ON, String.valueOf(this.getCreatedOn()));
		if(StringUtils.isNotBlank(this.getApplicationUrl())){
			map.put(SecurityFieldConstants._CLIENT_APP_URL, this.getApplicationUrl());
		}
		if(StringUtils.isNotBlank(this.getTimezone())){
			map.put(SecurityFieldConstants._TIME_ZONE, this.getTimezone());
		}
		if(StringUtils.isNotBlank(this.getLocale())){
			map.put(SecurityFieldConstants._LOCALE, this.getLocale());
		}
		if(StringUtils.isNotBlank(this.getCurrency())){
			map.put(SecurityFieldConstants._CURRENCY, this.getCurrency());
		}
		return map;
	}

	/**
	 * <p>This will convert a map to the CLientdetails object</p>
	 * <p>While deserailizing it verifies if it has any id associated with it or not, if not
	 * <code>throws new {@link IllegalArgumentException}</code></p>
	 * @param data
	 * @return a new instance of <code>{@link ClientDetails}</code>
	 */
	public static ClientDetails deserailizeFromDocumentMap(Map<String, String> data){
		if(CollectionUtils.isEmpty(data) || !StringUtils.isNumeric(data.get(SecurityFieldConstants._ID))){
			throw new IllegalArgumentException("Data Can't be Deserailized as it has no data.");
		}
		return new UimClientDetails(Long.valueOf(data.get(SecurityFieldConstants._ID)), 
				data.get(SecurityFieldConstants._CLIENT_NAME), 
				data.get(SecurityFieldConstants._CLIENT_APP_URL), 
				data.get(SecurityFieldConstants._TIME_ZONE), 
				data.get(SecurityFieldConstants._LOCALE), 
				data.get(SecurityFieldConstants._CURRENCY),
				Long.valueOf(data.get(SecurityFieldConstants._CLIENT_CREATED_ON)));
	}
	
}

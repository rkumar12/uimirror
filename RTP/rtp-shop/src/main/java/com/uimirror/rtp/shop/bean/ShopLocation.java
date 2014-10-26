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
package com.uimirror.rtp.shop.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.uimirror.core.GeoLongLat;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Contains the Shop name and exact longitude and latitude
 * @author Jay
 */
public class ShopLocation extends BeanBasedDocument<ShopLocation> implements BeanValidatorService {

	private static final long serialVersionUID = 2214130276159001384L;
	private ShortShopInfo info;
	private GeoLongLat exactLoc;
	

	// Don't Use this until it has specific requirement
	public ShopLocation() {
	}

	public ShopLocation(ShortShopInfo info, GeoLongLat exactLoc) {
		Assert.notNull(info, "Shop Info can't be null");
		this.info = info;
		this.exactLoc = exactLoc;
	}
	
	@Override
	public Map<String, Object> toMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if (!StringUtils.hasText(getShopId()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getShopName()))
			valid = Boolean.FALSE;
		if (getExactLoc() == null)
			valid = Boolean.FALSE;
		return valid;
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * 
	 * @return
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new LinkedHashMap<String, Object>(16);
		state.put(ShopDBFields.ID, getShopId());
		state.put(ShopDBFields.NAME, getShopName());
		state.put(ShopDBFields.LOCATION, getExactLoc().toGeoCordMap());
		return state;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public ShopLocation initFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		validateSource(src);
		// Initialize the state
		return init(src);
	}
	
	/**
	 * converts a map that comes from DB into ShopInfo object.
	 * 
	 * @param raw
	 * @return {@link UserAccountLogs}
	 */
	@SuppressWarnings("unchecked")
	private ShopLocation init(Map<String, Object> raw) {

		String id = (String) raw.get(ShopDBFields.ID);
		String shopName = (String) raw.get(ShopDBFields.NAME);
		ShortShopInfo info = new ShortShopInfo(id, shopName, null);
		GeoLongLat geoLonglat = GeoLongLat.initFromGeoCordMap((Map<String, Object>)raw.get(ShopDBFields.LOCATION));
		return new ShopLocation(info, geoLonglat);
	}

	public String getShopName() {
		return info.getName();
	}

	public GeoLongLat getExactLoc() {
		return exactLoc;
	}
	
	public String getShopId(){
		return info.getShopId();
	}

	public ShortShopInfo getInfo() {
		return info;
	}
	
}

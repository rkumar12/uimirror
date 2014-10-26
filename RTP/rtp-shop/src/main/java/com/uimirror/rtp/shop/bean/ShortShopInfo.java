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

/**
 * Contains the basic info for the shop such as name, id, and location name
 * @author Jay
 */
public class ShortShopInfo {
	
	private String shopId;
	private String name;
	private String location;

	/**
	 * @param shopId
	 * @param name
	 * @param location
	 */
	public ShortShopInfo(String shopId, String name, String location) {
		super();
		this.shopId = shopId;
		this.name = name;
		this.location = location;
	}
	
	/**
	 * Creates the state of an object from the provided map
	 * @param map
	 * @return
	 */
	public ShortShopInfo initFromMap(Map<String, Object> map){
		String shopId = (String)map.get(ShopDBFields.ID);
		String name = (String)map.get(ShopDBFields.NAME);
		String location = (String)map.get(ShopDBFields.LOCATION);
		return new ShortShopInfo(shopId, name, location);
		
	}
	
	/**
	 * Shop Id and Name Map
	 * @return
	 */
	public Map<String, Object> toShopIdNameMap(){
		Map<String, Object> map = new LinkedHashMap<String, Object>(4);
		map.put(ShopDBFields.ID, getShopId());
		map.put(ShopDBFields.NAME, getName());
		return map;
	}
	/**
	 * Shop Id, Name and Location Map
	 * @return
	 */
	public Map<String, Object> toShopIdNameWithLocMap(){
		Map<String, Object> map = toShopIdNameMap();
		map.put(ShopDBFields.LOCATION, getLocation());
		return map;
	}

	public String getShopId() {
		return shopId;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

}

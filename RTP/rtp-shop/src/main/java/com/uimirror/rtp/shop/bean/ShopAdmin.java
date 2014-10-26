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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.user.ShortUserInfo;

/**
 * Contains the admin who are responsible for the shop
 * 
 * @author Jay
 */
public class ShopAdmin extends BeanBasedDocument<ShopAdmin> implements BeanValidatorService {

	private static final long serialVersionUID = 2214130276159001384L;
	private List<ShortUserInfo> admins;
	private ShortShopInfo info;

	// Don't Use this until it has specific requirement
	public ShopAdmin() {
	}
	
	public ShopAdmin(List<ShortUserInfo> admins, ShortShopInfo info) {
		Assert.notNull(info, "Shop Info can't be empty");
		Assert.notEmpty(admins, "There should be at least one Admin");
		this.admins = admins;
		this.info = info;
		setId(info.getShopId());
	}
	
	@Override
	public ShopAdmin initFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		validateSource(src);
		// Initialize the state
		return init(src);
	}
	
	/**
	 * converts a map that comes from DB into ShopAdmin object.
	 * 
	 * @param raw
	 * @return {@link UserAccountLogs}
	 */
	@SuppressWarnings("unchecked")
	private ShopAdmin init(Map<String, Object> raw) {
		String id = (String) raw.get(ShopDBFields.ID);
		String shopName = (String) raw.get(ShopDBFields.NAME);
		ShortShopInfo info = new ShortShopInfo(id, shopName, null);
		List<Map<String, Object>> adminObjs = (List<Map<String, Object>>)raw.get(ShopDBFields.ADMINS);
		final List<ShortUserInfo> users = new ArrayList<ShortUserInfo>(10);
		if(!CollectionUtils.isEmpty(adminObjs)){
			adminObjs.forEach((map) -> {
				users.add(ShortUserInfo.initFromMap(map));
			});
		}
		return new ShopAdmin(users, info);
	}


	@Override
	public Map<String, Object> toMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if (!StringUtils.hasText(getShopid()))
			valid = Boolean.FALSE;
		if (CollectionUtils.isEmpty(getAdmins()))
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
		
		state.put(ShopDBFields.ID, getShopid());
		final List<Map<String, Object>> admins = new ArrayList<Map<String,Object>>(getAdmins().size());
		getAdmins().forEach(shortInfo ->{
			admins.add(shortInfo.getNameIdMap());
		});
		state.put(ShopDBFields.ADMINS, admins);
		return state;
	}
	
	public String getShopid(){
		return info.getShopId();
	}

	public List<ShortUserInfo> getAdmins() {
		return admins;
	}

	public ShortShopInfo getInfo() {
		return info;
	}
 
}

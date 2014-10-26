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

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Contains the other note of the shop
 * Intialy it will be added to the customer db, once customer has confirmed its the user
 * it will convert to that and deleted the customer profile.
 * @author Jay
 */
public class ShopPreferableCustomer extends BeanBasedDocument<ShopPreferableCustomer> implements BeanValidatorService {

	private static final long serialVersionUID = -6504474875834652281L;

	private ShortShopInfo shopinfo;
	//One customer who is going to be registered it will be first check in user list if found else create customer.
	private List<String> customers;
	private List<String> users;

	// Don't Use this until it has specific requirement
	public ShopPreferableCustomer() {
		super();
	}
	
	public ShopPreferableCustomer(ShortShopInfo shopinfo, List<String> customers, List<String> users) {
		Assert.notNull(shopinfo, "Info can't be empty");
		this.shopinfo = shopinfo;
		this.customers = customers;
		this.users = users;
		setId(shopinfo.getShopId());
	}

	/**
	 * @param details
	 */
	public ShopPreferableCustomer(Map<String, Object> details) {
		super(details);
	}
	
	@Override
	public ShopPreferableCustomer initFromMap(Map<String, Object> src) {
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
	private ShopPreferableCustomer init(Map<String, Object> raw) {
		String id = (String) raw.get(ShopDBFields.ID);
		String shopName = (String) raw.get(ShopDBFields.NAME);
		ShortShopInfo info = new ShortShopInfo(id, shopName, null);
		List<String> customers = (List<String>) raw.get(ShopPreferableCustomerDBFields.CUSTOMERS);
		List<String> users = (List<String>) raw.get(ShopPreferableCustomerDBFields.USERS);
		return new ShopPreferableCustomer(info, customers, users);
	}


	@Override
	public boolean isValid() {
		//NOP
		return Boolean.TRUE;
	}

	@Override
	public Map<String, Object> toMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException(
					"Can't be serailized the state of the object");
		return serailize();
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
		state.put(ShopPreferableCustomerDBFields.CUSTOMERS, getCustomers());
		state.put(ShopPreferableCustomerDBFields.USERS, getUsers());
		return state;
	}

	public ShortShopInfo getShopinfo() {
		return shopinfo;
	}

	public List<String> getCustomers() {
		return CollectionUtils.isEmpty(customers) ? new ArrayList<String>() : customers;
	}

	public List<String> getUsers() {
		return CollectionUtils.isEmpty(users) ? new ArrayList<String>() : users;
	}

	public String getShopId(){
		return getShopinfo().getShopId();
	}
	public String getShopName(){
		return getShopinfo().getName();
	}

}

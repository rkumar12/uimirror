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
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.user.AccountStatus;

/**
 * Contains the Basic Info about the shop, such as name, longLat and telephone
 * no
 * 
 * @author Jay
 */
public class ShopInfo extends BeanBasedDocument<ShopInfo> implements BeanValidatorService {

	private static final long serialVersionUID = -6504474875834652281L;
	private ShortShopInfo info;
	//SHop Contact Number
	private String contactNo;
	//Category of product it deals with
	private String category;
	//when its established
	private long startedOn;
	//This shop retporting to which are shops
	private String reporting_to;
	//who are reporting to this
	private List<String> shops_reporting;
	//Either this will be a operational sell branch or main branch to control other shop
	private ShopType type;
	// Shop Total Prmos
	private int numberOfCampaigns;
	// Shop Account status
	private AccountStatus status;

	// Don't Use this until it has specific requirement
	public ShopInfo() {
		super();
	}

	public ShopInfo(Map<String, Object> details) {
		super(details);
	}

	

	public ShopInfo(ShortShopInfo info, String contactNo,
			String category, long startedOn, String reporting_to,
			List<String> shops_reporting, ShopType type, int numberOfCampaigns,
			AccountStatus status) {
		Assert.notNull(info, "Shop Info can't be invalid");
		setId(info.getShopId());
		this.info = info;
		this.contactNo = contactNo;
		this.category = category;
		this.startedOn = startedOn;
		this.reporting_to = reporting_to;
		this.shops_reporting = shops_reporting;
		this.type = type;
		this.numberOfCampaigns = numberOfCampaigns;
		this.status = status;
	}

	@Override
	public ShopInfo initFromMap(Map<String, Object> src) {
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
	private ShopInfo init(Map<String, Object> raw) {

		String id = (String) raw.get(ShopDBFields.ID);
		String shopName = (String) raw.get(ShopDBFields.NAME);
		String locName = (String) raw.get(ShopDBFields.LOCATION);
		ShortShopInfo info = new ShortShopInfo(id, shopName, locName);
		String contactNo = (String) raw.get(ShopDBFields.CONTACT_NUMBER);
		String category = (String) raw.get(ShopDBFields.CATEGORY);
		long startedOn = (long) raw.getOrDefault(ShopDBFields.STARTED_ON, 0l);
		int	numberOfCampaign = (int) raw.getOrDefault(ShopDBFields.NUMBER_OF_CAMPAIGN, 0);
		String reporting_to = (String) raw.get(ShopDBFields.REPORTING_TO_SHOP);
		List<String> shops_reporting = (List<String>) raw.get(ShopDBFields.SHOPS_REPORTING);
		String type = (String) raw.get(ShopDBFields.TYPE);
		ShopType shopType = StringUtils.hasText(type) ? ShopType.getEnum(type) : null;
		String status = (String) raw.get(ShopDBFields.ACCOUNT_STATUS);
		AccountStatus accountStatus = StringUtils.hasText(status) ? AccountStatus.getEnum(status) : null;
		return new ShopInfo(info, contactNo, category, startedOn, reporting_to, shops_reporting, shopType, numberOfCampaign, accountStatus);
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
		if (!StringUtils.hasText(getName()))
			valid = Boolean.FALSE;
		if (getType() == null)
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
		
		if(StringUtils.hasText(getShopId()))
			state.put(ShopDBFields.ID, getShopId());
		
		state.put(ShopDBFields.NAME, getName());
		if(StringUtils.hasText(getLocName()))
			state.put(ShopDBFields.LOCATION, getLocName());
		if(StringUtils.hasText(getContactNo()))
			state.put(ShopDBFields.CONTACT_NUMBER, getContactNo());
		if(StringUtils.hasText(getCategory()))
			state.put(ShopDBFields.CATEGORY, getCategory());
		if(getStartedOn() > 0l)
			state.put(ShopDBFields.STARTED_ON, getStartedOn());
		if(getNumberOfCampaigns() > 0)
			state.put(ShopDBFields.NUMBER_OF_CAMPAIGN, getNumberOfCampaigns());
		if(StringUtils.hasText(getReporting_to()))
			state.put(ShopDBFields.REPORTING_TO_SHOP, getReporting_to());
		if(!CollectionUtils.isEmpty(getShops_reporting()))
			state.put(ShopDBFields.SHOPS_REPORTING, getShops_reporting());
		state.put(ShopDBFields.TYPE, getType().getType());
		state.put(ShopDBFields.ACCOUNT_STATUS, getStatus().getStatus());
		return state;
	}

	public String getName() {
		return info.getName();
	}

	public String getLocName() {
		return info.getLocation();
	}

	public String getCategory() {
		return category;
	}

	public long getStartedOn() {
		return startedOn;
	}

	public String getReporting_to() {
		return reporting_to;
	}

	public List<String> getShops_reporting() {
		return shops_reporting;
	}

	public ShopType getType() {
		return type;
	}

	public int getNumberOfCampaigns() {
		return numberOfCampaigns;
	}

	public AccountStatus getStatus() {
		return status;
	}
	
	public String getShopId(){
		return getId();
	}

	public String getContactNo() {
		return contactNo;
	}

	public ShortShopInfo getInfo() {
		return info;
	}
	
}

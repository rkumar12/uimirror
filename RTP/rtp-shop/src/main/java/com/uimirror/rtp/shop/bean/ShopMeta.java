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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Contains the other note of the shop
 * 
 * @author Jay
 */
public class ShopMeta extends BeanBasedDocument<ShopMeta> implements BeanValidatorService {

	private static final long serialVersionUID = -6504474875834652281L;

	private ShortShopInfo shopinfo;
	private long createdOn;
	private long lastModifiedOn;
	private String createdBy;
	private Map<String, Object> note;

	// Don't Use this until it has specific requirement
	public ShopMeta() {
		super();
	}

	/**
	 * @param shopinfo
	 * @param createdOn
	 * @param lastModifiedOn
	 * @param createdBy
	 * @param note
	 */
	public ShopMeta(ShortShopInfo shopinfo, long createdOn, long lastModifiedOn, String createdBy, Map<String, Object> note) {
		Assert.notNull(shopinfo, "Can't be empty");
		this.shopinfo = shopinfo;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
		this.createdBy = createdBy;
		this.note = note;
		setId(shopinfo.getShopId());
	}

	/**
	 * @param details
	 */
	public ShopMeta(Map<String, Object> details) {
		super(details);
	}


	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if (!StringUtils.hasText(getId()))
			valid = Boolean.FALSE;
		if (getCreatedOn() <= 0l)
			valid = Boolean.FALSE;
		return valid;
	}

	@Override
	public ShopMeta initFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		validateSource(src);
		// Initialize the state
		return init(src);
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
		state.put(ShopDBFields.NAME, getShopinfo().getName());
		state.put(ShopDBFields.CREATED_ON, getCreatedOn());
		if(getLastModifiedOn() > 0l)
			state.put(ShopDBFields.LAST_MODIFIED_ON, getLastModifiedOn());
		if(!CollectionUtils.isEmpty(getNote()))
			state.put(ShopDBFields.NOTES, getNote());
		return state;
	}

	/**
	 * converts a map that comes from DB into ShopInfo object.
	 * 
	 * @param raw
	 * @return {@link UserAccountLogs}
	 */
	@SuppressWarnings("unchecked")
	private ShopMeta init(Map<String, Object> raw) {
		String id = (String) raw.get(ShopDBFields.ID);
		String shopName = (String) raw.get(ShopDBFields.NAME);
		ShortShopInfo info = new ShortShopInfo(id, shopName, null);
		long createdOn = (long) raw.getOrDefault(ShopDBFields.CREATED_ON, 0l);
		long modifiedOn = (long) raw.getOrDefault(ShopDBFields.LAST_MODIFIED_ON, 0l);
		Map<String, Object> notes = (Map<String, Object>) raw.get(ShopDBFields.NOTES);
		return new ShopMeta(info, createdOn, modifiedOn, shopName, notes);
	}

	public String getShopId() {
		return getId();
	}

	public long getCreatedOn() {
		return createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public ShortShopInfo getShopinfo() {
		return shopinfo;
	}

	public long getLastModifiedOn() {
		return lastModifiedOn;
	}

	public Map<String, Object> getNote() {
		return note;
	}
	
	

}

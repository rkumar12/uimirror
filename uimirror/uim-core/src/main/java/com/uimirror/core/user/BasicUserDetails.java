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

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * @author Jay
 */
public class BasicUserDetails extends BeanBasedDocument<BasicUserDetails> implements UserDetails, BeanValidatorService {

	private static final long serialVersionUID = -5282406171053226490L;

	private String address;
	private String dateOfBirth;

	// DOn't Use this until it has specific requirement
	public BasicUserDetails() {
		super();
	}

	public BasicUserDetails(Map<String, Object> map) {
		super(map);
	}

	public BasicUserDetails(String id,String address,String dateOfBirth) {
		super(id);
		this.address = address;
		this.dateOfBirth = dateOfBirth;
	}

	
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.user.UserDetails#getProfileId()
	 */
	@Override
	public String getProfileId() {
		return getId();
	}
	
	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	@Override
	public Map<String, Object> toMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}

	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * 
	 * @return
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new LinkedHashMap<String, Object>(16);
		state.put(UserDBFields.ID, getId());
		state.put(UserDBFields.DATE_OF_BIRTH, dateOfBirth);
		state.put(UserDBFields.ADDRESS, address);
		return state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if (!StringUtils.hasText(getId()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getAddress()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getDateOfBirth()))
			valid = Boolean.FALSE;

		return valid;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java
	 * .util.Map)
	 */
	@Override
	public BasicUserDetails initFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		validateSource(src);
		// Initialize the state
		return init(src);

	}

	/**
	 * converts a map that comes from DB into BasicUserDetails object.
	 * 
	 * @param raw
	 * @return {@link BasicUserDetails}
	 */
	private BasicUserDetails init(Map<String, Object> raw) {

		String id = (String) raw.get(UserDBFields.ID);
		String address = (String) raw.get(UserDBFields.ADDRESS);
		String dateOfBirth = (String) raw.get(UserDBFields.DATE_OF_BIRTH);

		return new BasicUserDetails(id,address,dateOfBirth);
	}

	

}

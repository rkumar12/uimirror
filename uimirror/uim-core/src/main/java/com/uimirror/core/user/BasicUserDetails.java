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

import com.uimirror.core.DOB;
import com.uimirror.core.Location;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Contains the User details of the user
 * @author Jay
 */
public class BasicUserDetails extends BeanBasedDocument<BasicUserDetails> implements BeanValidatorService {

	private static final long serialVersionUID = -5282406171053226490L;

	private Location presentAddress;
	private Location permanetAddress;
	private DOB dateOfBirth;
	private Map<String, Object> details;

	// DOn't Use this until it has specific requirement
	public BasicUserDetails() {
		super();
	}

	public BasicUserDetails(Map<String, Object> map) {
		super(map);
	}
	
	public BasicUserDetails(String profileId, Location presentAddress, Location permanetAddress, DOB dateOfBirth, Map<String, Object> details) {
		super(profileId);
		this.presentAddress = presentAddress;
		this.permanetAddress = permanetAddress;
		this.dateOfBirth = dateOfBirth;
		this.details = details;
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
		//state.put(UserDBFields.ADDRESS, address);
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
		if(getDateOfBirth() == null)
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
	@SuppressWarnings("unchecked")
	private BasicUserDetails init(Map<String, Object> raw) {
		String id = (String) raw.get(UserDBFields.ID);
		Map<String, Object> dateOfBirth = (Map<String, Object>) raw.get(UserDBFields.DATE_OF_BIRTH);
		DOB dob = DOB.initFromMap(dateOfBirth);
		Map<String, Object> presentAddress = (Map<String, Object>) raw.get(UserDBFields.PRESENT_ADDRESS);
		Map<String, Object> permanetAddress = (Map<String, Object>) raw.get(UserDBFields.PRESENT_ADDRESS);
		Map<String, Object> details = (Map<String, Object>) raw.get(UserDBFields.DETAILS);
		//return new BasicUserDetails(id,address,dateOfBirth);
		return null;
	}

	public String getProfileId() {
		return getId();
	}

	public DOB getDateOfBirth() {
		return this.dateOfBirth;
	}

	public Object getDetails() {
		return this.details;
	}

	public Location getPresentAddress() {
		return this.presentAddress;
	}

	public Location getPermanetAddress() {
		return this.permanetAddress;
	}

	@Override
	public String toString() {
		return "BasicUserDetails [presentAddress=" + presentAddress
				+ ", permanetAddress=" + permanetAddress + ", dateOfBirth="
				+ dateOfBirth + ", details=" + details + "]";
	}

}
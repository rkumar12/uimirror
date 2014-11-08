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

import com.uimirror.core.DOB;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Contains the User details of the user
 * @author Jay
 */
public class BasicDetails extends BeanBasedDocument<BasicDetails> implements BeanValidatorService {

	private static final long serialVersionUID = -5282406171053226490L;

	private String presentAddress;
	private String permanetAddress;
	private DOB dateOfBirth;
	private Map<String, Object> details;

	// DOn't Use this until it has specific requirement
	public BasicDetails() {
		super();
	}

	public BasicDetails(Map<String, Object> map) {
		super(map);
	}
	
	public BasicDetails(String profileId, String presentAddress, String permanetAddress, DOB dateOfBirth, Map<String, Object> details) {
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
	 * @return {@link Map} represention of the current state
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new LinkedHashMap<String, Object>(16);
		state.put(UserDBFields.ID, getId());
		state.put(UserDBFields.DATE_OF_BIRTH, dateOfBirth.toMap());
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
	public BasicDetails initFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		validateSource(src);
		// Initialize the state
		return init(src);
	}

	/**
	 * converts a map that comes from DB into BasicDetails object.
	 * 
	 * @param raw from which it will be serialized
	 * @return {@link BasicDetails}
	 */
	@SuppressWarnings("unchecked")
	private BasicDetails init(Map<String, Object> raw) {
		String id = (String) raw.get(UserDBFields.ID);
		Map<String, Object> dateOfBirth = (Map<String, Object>) raw.get(UserDBFields.DATE_OF_BIRTH);
		DOB dob = DOB.initFromMap(dateOfBirth);
		//TODO other parts also 
		return new BasicDetails(id,null, null ,dob, null);
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

	@Override
	public String toString() {
		return "BasicDetails [presentAddress=" + presentAddress
				+ ", permanetAddress=" + permanetAddress + ", dateOfBirth="
				+ dateOfBirth + ", details=" + details + "]";
	}

}

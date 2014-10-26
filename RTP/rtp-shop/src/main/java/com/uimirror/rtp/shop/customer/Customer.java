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
package com.uimirror.rtp.shop.customer;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Contains the customer details such as name and email
 * @author Jay
 */
public class Customer extends BeanBasedDocument<Customer> implements BeanValidatorService{

	private static final long serialVersionUID = -2416811474827046583L;

	private String name;
	private String contactNo;
	private String email;
	private String dob;
	
	// Don't Use this until it has specific requirement
	public Customer(){
	}

	/**
	 * @param name
	 * @param contactNo
	 * @param email
	 * @param dob
	 */
	public Customer(String id, String name, String contactNo, String email, String dob) {
		super(id);
		this.name = name;
		this.contactNo = contactNo;
		this.email = email;
		this.dob = dob;
	}
	
	@Override
	public Customer initFromMap(Map<String, Object> src) {
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
	private Customer init(Map<String, Object> raw) {
		String id = (String) raw.get(CustomerDBFields.ID);
		String name = (String) raw.get(CustomerDBFields.NAME);
		String contact_no = (String) raw.get(CustomerDBFields.CONTACT_NO);
		String email = (String) raw.get(CustomerDBFields.EMAIL);
		String dob = (String) raw.get(CustomerDBFields.DOB);
		return new Customer(id, name, contact_no, email, dob);
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
		if (!StringUtils.hasText(getContactNo()) && !StringUtils.hasText(getEmail()))
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
		if(StringUtils.hasText(getCustomerId()))
			state.put(CustomerDBFields.ID, getCustomerId());
		if(StringUtils.hasText(getName()))
			state.put(CustomerDBFields.NAME, getName());
		if(StringUtils.hasText(getContactNo()))
			state.put(CustomerDBFields.CONTACT_NO, getContactNo());
		if(StringUtils.hasText(getEmail()))
			state.put(CustomerDBFields.EMAIL, getEmail());
		if(StringUtils.hasText(getDob()))
			state.put(CustomerDBFields.DOB, getDob());
		return state;
	}

	public String getName() {
		return name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public String getEmail() {
		return email;
	}

	public String getDob() {
		return dob;
	}
	
	public String getCustomerId(){
		return getId();
	}

}

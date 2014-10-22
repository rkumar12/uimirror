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
package com.uimirror.core.user.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.uimirror.core.bean.Gender;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.core.user.UserDBFields;
import com.uimirror.core.user.UserInfo;

/**
 * @author Jay
 */
// TODO implement the Store for this
public class BasicUserInfo extends BeanBasedDocument<BasicUserInfo> implements UserInfo, BeanValidatorService {

	private static final long serialVersionUID = -5282406171053226490L;

	private String firstName;
	private String lastName;
	private String email;
	private Gender gender;
	private AccountStatus accountStatus;
	private AccountState accountState;

	// DOn't Use this until it has specific requirement
	public BasicUserInfo() {
		super();
	}

	public BasicUserInfo(Map<String, Object> map) {
		super(map);
	}

	public BasicUserInfo(String id,String firstName, String lastName, String email, Gender gender, 
			AccountStatus accountStatus, AccountState accountState) {
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.accountStatus = accountStatus;
		this.accountState = accountState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.user.UserDetails#getFirstName()
	 */
	@Override
	public String getFirstName() {
		return firstName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.user.UserDetails#getLastName()
	 */
	@Override
	public String getLastName() {
		return lastName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.user.UserDetails#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.user.UserDetails#getGender()
	 */
	@Override
	public Gender getGender() {
		return gender;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.user.UserDetails#getAccountStatus()
	 */
	@Override
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.user.UserDetails#getAccountState()
	 */
	@Override
	public AccountState getAccountState() {
		return accountState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.user.UserDetails#getProfileId()
	 */
	@Override
	public String getProfileId() {
		return getId();
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
		state.put(UserDBFields.FIRST_NAME, getFirstName());
		state.put(UserDBFields.LAST_NAME, getLastName());
		state.put(UserDBFields.EMAIL, getEmail());
		state.put(UserDBFields.GENDER, getGender().toString());
		state.put(UserDBFields.ACCOUNT_STATUS, getAccountStatus().getStatus());
		state.put(UserDBFields.ACCOUNT_STATE, getAccountState().getState());
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
		if (StringUtils.hasText(getFirstName()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getLastName()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getEmail()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getGender().toString()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getAccountState().toString()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getAccountState().toString()))
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
	public BasicUserInfo initFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		validateSource(src);
		// Initialize the state
		return init(src);

	}

	/**
	 * converts a map that comes from DB into BasicUserInfo object.
	 * 
	 * @param raw
	 * @return {@link BasicUserInfo}
	 */
	private BasicUserInfo init(Map<String, Object> raw) {

		String id = (String) raw.get(UserDBFields.ID);
		String firstName = (String) raw.get(UserDBFields.FIRST_NAME);
		String lastName = (String) raw.get(UserDBFields.LAST_NAME);
		String email = (String) raw.get(UserDBFields.EMAIL);
		String gender = (String) raw.get(UserDBFields.GENDER);
		// TODO: check about the below conversion
		Gender genderVal = StringUtils.hasText(gender) ? Gender.getEnum(gender) : Gender.MALE;
		String statVal = (String) raw.get(UserDBFields.ACCOUNT_STATUS);
		String stateVal = (String) raw.get(UserDBFields.ACCOUNT_STATE);
		AccountStatus accountStatus = StringUtils.hasText(statVal) ? AccountStatus.getEnum(statVal)
				: AccountStatus.ACTIVE;
		AccountState accountState = StringUtils.hasText(stateVal) ? AccountState.getEnum(stateVal)
				: AccountState.ENABLED;

		return new BasicUserInfo(id,firstName, lastName, email, genderVal,  accountStatus, accountState);
	}

}
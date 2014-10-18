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

import java.util.Map;
import java.util.UUID;

import com.uimirror.core.bean.Gender;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.core.user.UserDetails;

/**
 * @author Jay
 */
//TODO check Client.java and do the necessary changes
public class BasicUserDetails extends BeanBasedDocument<BasicUserDetails> implements UserDetails, BeanValidatorService{

	private static final long serialVersionUID = -5282406171053226490L;
	
	private String profileId;
	private String firstName;
	private String lastName;
	private String email;
	private Gender gender;
	private String dateOfBirth;
	private AccountStatus accountStatus;
	private AccountState accountState;
	private Map<String, Object> details;

	public BasicUserDetails(String firstName, String lastName, String email, Gender gender, String dateOfBirth, AccountStatus accountStatus, AccountState accountState, Map<String, Object> details) {
		super(UUID.randomUUID().toString());
		this.profileId = getId();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.accountStatus = accountStatus;
		this.accountState = accountState;
		this.details = details;
	}
	
	public BasicUserDetails(String firstName, String lastName, String email, Gender gender, String dateOfBirth, String accountStatus, String accountState, Map<String, Object> details) {
		super(UUID.randomUUID().toString());
		this.profileId = getId();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.accountStatus = AccountStatus.getEnum(accountStatus);
		this.accountState = AccountState.getEnum(accountState);
		this.details = details;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.UserDetails#getFirstName()
	 */
	@Override
	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.UserDetails#getLastName()
	 */
	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.UserDetails#getEmail()
	 */
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.UserDetails#getDateOfBirth()
	 */
	@Override
	public String getDateOfBirth() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.UserDetails#getGender()
	 */
	@Override
	public Gender getGender() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.UserDetails#getAccountStatus()
	 */
	@Override
	public AccountStatus getAccountStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public BasicUserDetails initFromMap(Map<String, Object> src) {
		// TODO Auto-generated method stub
		//First check map is valid
		//take reference from Client
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.UserDetails#getAccountState()
	 */
	@Override
	public AccountState getAccountState() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.UserDetails#getDetails()
	 */
	@Override
	public Map<String, Object> getDetails() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> toMap(){
		//TODO make sure no null value going to DB
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.UserDetails#getProfileId()
	 */
	@Override
	public String getProfileId() {
		// TODO Auto-generated method stub
		return null;
	}

}

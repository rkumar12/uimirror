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

import static com.uimirror.core.Constants.SINGLE_SPACE;
import static com.uimirror.core.mongo.feature.BasicDBFields.ID;
import static com.uimirror.core.user.UserDBFields.ACCOUNT_STATE;
import static com.uimirror.core.user.UserDBFields.ACCOUNT_STATUS;
import static com.uimirror.core.user.UserDBFields.EMAIL;
import static com.uimirror.core.user.UserDBFields.FIRST_NAME;
import static com.uimirror.core.user.UserDBFields.GENDER;
import static com.uimirror.core.user.UserDBFields.LAST_NAME;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.bean.Gender;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Basic Information of a user 
 * such as name, email, gender, current account status and state of the account.
 * @author Jay
 */
public final class BasicInfo extends AbstractBeanBasedDocument<BasicInfo> implements BeanValidatorService {

	private static final long serialVersionUID = -5282406171053226490L;
	private String firstName;
	private String lastName;
	private String email;
	private Gender gender;
	private AccountStatus accountStatus;
	private AccountState accountState;

	// DOn't Use this until it has specific requirement
	public BasicInfo() {
		//NOP
	}


	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#updateId(java.lang.String)
	 */
	@Override
	public BasicInfo updateId(String id) {
		return new BasicInfoBuilder(id).
				addEmail(email).
				addFirstName(firstName).
				addGender(gender).
				addLastName(lastName).
				addState(accountState).
				addStatus(accountStatus).build();
	}

	@Override
	public Map<String, Object> writeToMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if (!StringUtils.hasText(getFirstName()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getEmail()))
			valid = Boolean.FALSE;
		if (getGender() == null)
			valid = Boolean.FALSE;
		if(getAccountState() == null)
			valid = Boolean.FALSE;
		if(getAccountStatus() == null)
			valid = Boolean.FALSE;
		return valid;

	}

	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * 
	 * @return {@link Map} with the current state
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new WeakHashMap<String, Object>(16);
		if(StringUtils.hasText(getId()))
			state.put(ID, getId());
		state.put(FIRST_NAME, getFirstName());
		if(StringUtils.hasText(getLastName()))
			state.put(LAST_NAME, getLastName());
		state.put(EMAIL, getEmail());
		state.put(GENDER, getGender().toString());
		state.put(ACCOUNT_STATUS, getAccountStatus().getStatus());
		state.put(ACCOUNT_STATE, getAccountState().getState());
		return state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java
	 * .util.Map)
	 */
	@Override
	public BasicInfo readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);

	}

	/**
	 * converts a map that comes from DB into BasicInfo object.
	 * @param raw {@link Map} from which it will be intialized
	 * @return {@link BasicInfo}
	 */
	private BasicInfo init(Map<String, Object> raw) {
		String id = (String) raw.get(ID);
		String firstName = (String) raw.get(FIRST_NAME);
		String lastName = (String) raw.get(LAST_NAME);
		String email = (String) raw.get(EMAIL);
		String gender = (String) raw.get(GENDER);
		Gender genderVal = StringUtils.hasText(gender) ? Gender.getEnum(gender) : null;
		String statVal = (String) raw.get(ACCOUNT_STATUS);
		String stateVal = (String) raw.get(ACCOUNT_STATE);
		AccountStatus accountStatus = StringUtils.hasText(statVal) ? AccountStatus.getEnum(statVal): null;
		AccountState accountState = StringUtils.hasText(stateVal) ? AccountState.getEnum(stateVal): null;
		BasicInfoBuilder builder = new BasicInfoBuilder(id);
		builder.addEmail(email).addFirstName(firstName).addGender(genderVal).addLastName(lastName).addState(accountState).addStatus(accountStatus);
		return builder.build();
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		if(StringUtils.hasText(lastName) && StringUtils.hasText(firstName))
			return this.firstName + SINGLE_SPACE+ this.lastName;
		else if(StringUtils.hasText(firstName))
			return this.firstName;
		return this.lastName;
	}
	public String getEmail() {
		return email;
	}

	public Gender getGender() {
		return gender;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public AccountState getAccountState() {
		return accountState;
	}

	public String getProfileId() {
		return getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((getProfileId() == null) ? 0 : getProfileId().hashCode());
		return result;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicInfo other = (BasicInfo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (getProfileId() == null) {
			if (other.getProfileId() != null)
				return false;
		} else if (!getProfileId().equals(other.getProfileId()))
			return false;
		return true;
	}
	
	public static class BasicInfoBuilder implements Builder<BasicInfo>{
		private String profileId;
		private String firstName;
		private String lastName;
		private String email;
		private Gender gender;
		private AccountStatus accountStatus;
		private AccountState accountState;
		
		public BasicInfoBuilder(String profileId) {
			this.profileId = profileId;
		}
		
		public BasicInfoBuilder addFirstName(String firstName){
			this.firstName = firstName;
			return this;
		}
		
		public BasicInfoBuilder addLastName(String lastName){
			this.lastName = lastName;
			return this;
		}
		
		public BasicInfoBuilder addEmail(String email){
			this.email = email;
			return this;
		}
		
		public BasicInfoBuilder addGender(String gender){
			this.gender = Gender.getEnum(gender);
			return this;
		}
		
		public BasicInfoBuilder addGender(Gender gender){
			this.gender = gender;
			return this;
		}
		
		public BasicInfoBuilder addStatus(String status){
			this.accountStatus = AccountStatus.getEnum(status);
			return this;
		}
		
		public BasicInfoBuilder addStatus(AccountStatus status){
			this.accountStatus = status;
			return this;
		}
		
		public BasicInfoBuilder addState(String state){
			this.accountState = AccountState.getEnum(state);
			return this;
		}
		
		public BasicInfoBuilder addState(AccountState state){
			this.accountState = state;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public BasicInfo build() {
			return new BasicInfo(this);
		}
		
	}
	
	private BasicInfo(BasicInfoBuilder builder){
		super(builder.profileId);
		this.accountState = builder.accountState;
		this.accountStatus = builder.accountStatus;
		this.email = builder.email;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.gender = builder.gender;
	}

	@Override
	public String toString() {
		StandardToStringStyle style = new StandardToStringStyle();
	    style.setFieldSeparator(", ");
	    style.setUseClassName(false);
	    style.setUseIdentityHashCode(false);
	    return new ReflectionToStringBuilder(this, style).toString();
	}

}

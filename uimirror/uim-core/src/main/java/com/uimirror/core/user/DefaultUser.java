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

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;


/**
 * A Default User representing the UIM System 
 * @author Jay
 */
public final class DefaultUser extends AbstractBeanBasedDocument<DefaultUser> implements BeanValidatorService{

	private static final long serialVersionUID = 3920171901998649548L;
	private BasicInfo basicInfo;
	private Credentials credentials;
	private BasicDetails details;
	private AccountLogs accountLogs;
	
	//Don't Use It untill it has some special requirment
	public DefaultUser() {
		//NOP
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(!getUserInfo().isValid())
			valid = Boolean.FALSE;
		if(!getCredentials().isValid())
			valid = Boolean.FALSE;
		if(!getBasicDetails().isValid())
			valid = Boolean.FALSE;
		if(!getLogs().isValid())
			valid = Boolean.FALSE;
		return valid;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public DefaultUser readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#toMap()
	 */
	@Override
	public Map<String, Object> writeToMap() {
		return serailize();
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * 
	 * @return {@link Map} representation of the current state
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new LinkedHashMap<String, Object>(20);
		state.putAll(getUserInfo().writeToMap());
		state.putAll(getCredentials().writeToMap());
		state.putAll(getBasicDetails().writeToMap());
		state.putAll(getLogs().writeToMap());
		return state;
	}
	
	/**
	 * converts a map that comes from DB into BasicInfo object.
	 * @param raw from where it will be presumed the state
	 * @return {@link BasicInfo}
	 */
	private DefaultUser init(Map<String, Object> raw) {
		String profileId = (String) raw.get(UserDBFields.ID);
		BasicInfo userInfo = new BasicInfo().readFromMap(raw);
		Credentials userCredentials = new Credentials().readFromMap(raw);
		BasicDetails userDetails = new BasicDetails().readFromMap(raw);
		AccountLogs userAccountLogs = new AccountLogs().readFromMap(raw);
		return new DefaultUserBuilder(profileId).
				addBasicInfo(userInfo).
				addCredentials(userCredentials).
				addDetails(userDetails).
				addLogs(userAccountLogs).
				build();
	}
	
	public static class DefaultUserBuilder implements Builder<DefaultUser>{
		private String profileId;
		private BasicInfo userInfo;
		private Credentials userCredentials;
		private BasicDetails userDetails;
		private AccountLogs userAccountLogs;
		
		public DefaultUserBuilder(String id){
			this.profileId = id;
		}

		public DefaultUserBuilder addBasicInfo(BasicInfo userInfo){
			this.userInfo = userInfo;
			return this;
		}
		
		public DefaultUserBuilder addCredentials(Credentials userCredentials){
			this.userCredentials = userCredentials;
			return this;
		}
		
		public DefaultUserBuilder addDetails(BasicDetails userDetails){
			this.userDetails = userDetails;
			return this;
		}
		
		public DefaultUserBuilder addLogs(AccountLogs userAccountLogs){
			this.userAccountLogs = userAccountLogs;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public DefaultUser build() {
			return new DefaultUser(this);
		}
		
	}
	private DefaultUser(DefaultUserBuilder builder){
		super(builder.profileId);
		this.accountLogs = builder.userAccountLogs;
		this.credentials = builder.userCredentials;
		this.details = builder.userDetails;
		this.basicInfo = builder.userInfo;
	}
	
	public BasicInfo getUserInfo() {
		return this.basicInfo;
	}

	public Credentials getCredentials() {
		return this.credentials;
	}

	public BasicDetails getBasicDetails() {
		return this.details;
	}

	public AccountLogs getLogs() {
		return this.accountLogs;
	}
	
	public String getProfileId(){
		return getId();
	}
	
	/**
	 * Updates the User info
	 * @param userInfo to be updated
	 * @return a new instance
	 */
	public DefaultUser updateInfo(BasicInfo userInfo){
		return new DefaultUserBuilder(getProfileId()).
				addBasicInfo(userInfo).
				addCredentials(credentials).
				addDetails(details).
				addLogs(accountLogs).
				build();
	}
	
	/**
	 * Updates the User Credentials
	 * @param userCredentials cedentials
	 * @return user object
	 */
	public DefaultUser updateCredentials(Credentials userCredentials){
		return new DefaultUserBuilder(getProfileId()).
				addBasicInfo(basicInfo).
				addCredentials(userCredentials).
				addDetails(details).
				addLogs(accountLogs).
				build();
	}
	
	/**
	 * Updates the User Details
	 * @param userDetails details
	 * @return user instance
	 */
	public DefaultUser updateDetails(BasicDetails userDetails){
		return new DefaultUserBuilder(getProfileId()).
				addBasicInfo(basicInfo).
				addCredentials(credentials).
				addDetails(userDetails).
				addLogs(accountLogs).
				build();
	}
	
	/**
	 * Updates the User Details
	 * @param userAccountLogs  to be updated
	 * @return user instance
	 */
	public DefaultUser updateLogs(AccountLogs userAccountLogs){
		return new DefaultUserBuilder(getProfileId()).
				addBasicInfo(basicInfo).
				addCredentials(credentials).
				addDetails(details).
				addLogs(userAccountLogs).
				build();
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#updateId()
	 */
	@Override
	public DefaultUser updateId(String id) {
		BasicInfo info = basicInfo== null ? null : basicInfo.updateId(id);
		Credentials crd = credentials == null ? null : credentials.updateId(id);
		BasicDetails dt = details == null ? null : details.updateId(id);
		AccountLogs logs = accountLogs == null ? null : accountLogs.updateId(id);
		return new DefaultUserBuilder(id).
				addBasicInfo(info).
				addCredentials(crd).
				addDetails(dt).
				addLogs(logs).
				build();
	}

	@Override
	public String toString() {
		return "DefaultUser [basicInfo=" + basicInfo + ", credentials="
				+ credentials + ", details=" + details + ", accountLogs="
				+ accountLogs + "]";
	}

}

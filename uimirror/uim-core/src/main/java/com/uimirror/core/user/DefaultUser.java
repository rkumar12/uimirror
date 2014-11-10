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
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;


/**
 * A Default User representing the UIM System 
 * @author Jay
 */
public class DefaultUser extends BeanBasedDocument<DefaultUser> implements BeanValidatorService{

	private static final long serialVersionUID = 3920171901998649548L;
	private BasicInfo userInfo;
	private Credentials userCredentials;
	private BasicDetails userDetails;
	private AccountLogs userAccountLogs;
	
	//Don't Use It untill it has some special requirment
	public DefaultUser() {
		super();
	}
	
	/**
	 * Updates the User info
	 * @param userInfo
	 * @return
	 */
	public DefaultUser updateInfo(BasicInfo userInfo){
		return new DefaultUserBuilder(userInfo).addCredentials(userCredentials).addDetails(userDetails).addLogs(userAccountLogs).build();
	}
	
	/**
	 * Updates the User Credentials
	 * @param userCredentials cedentials
	 * @return user object
	 */
	public DefaultUser updateCredentials(Credentials userCredentials){
		return new DefaultUserBuilder(userInfo).addCredentials(userCredentials).addDetails(userDetails).addLogs(userAccountLogs).build();
	}
	
	/**
	 * Updates the User Details
	 * @param userDetails details
	 * @return user instance
	 */
	public DefaultUser updateDetails(BasicDetails userDetails){
		return new DefaultUserBuilder(userInfo).addCredentials(userCredentials).addDetails(userDetails).addLogs(userAccountLogs).build();
	}
	
	/**
	 * Updates the User Details
	 * @param userAccountLogs 
	 * @return user instance
	 */
	public DefaultUser updateLogs(AccountLogs userAccountLogs){
		return new DefaultUserBuilder(userInfo).addCredentials(userCredentials).addDetails(userDetails).addLogs(userAccountLogs).build();
	}

	public BasicInfo getUserInfo() {
		return this.userInfo;
	}

	public Credentials getCredentials() {
		return this.userCredentials;
	}

	public BasicDetails getBasicDetails() {
		return this.userDetails;
	}

	public AccountLogs getLogs() {
		return this.userAccountLogs;
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
	public DefaultUser initFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		validateSource(src);
		// Initialize the state
		return init(src);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#toMap()
	 */
	@Override
	public Map<String, Object> toMap() {
		return serailize();
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * 
	 * @return {@link Map} representation of the current state
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new LinkedHashMap<String, Object>(20);
		state.putAll(getUserInfo().toMap());
		state.putAll(getCredentials().toMap());
		state.putAll(getBasicDetails().toMap());
		state.putAll(getLogs().toMap());
		return state;
	}
	
	/**
	 * converts a map that comes from DB into BasicInfo object.
	 * @param raw from where it will be presumed the state
	 * @return {@link BasicInfo}
	 */
	private DefaultUser init(Map<String, Object> raw) {
		BasicInfo userInfo = new BasicInfo().initFromMap(raw);
		Credentials userCredentials = new Credentials().initFromMap(raw);
		BasicDetails userDetails = new BasicDetails().initFromMap(raw);
		AccountLogs userAccountLogs = new AccountLogs().initFromMap(raw);
		return new DefaultUserBuilder(userInfo).addCredentials(userCredentials).addDetails(userDetails).addLogs(userAccountLogs).build();
	}
	
	public static class DefaultUserBuilder implements Builder<DefaultUser>{
		
		private BasicInfo userInfo;
		private Credentials userCredentials;
		private BasicDetails userDetails;
		private AccountLogs userAccountLogs;
		
		public DefaultUserBuilder(BasicInfo userInfo){
			this.userInfo = userInfo;
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
		this.userAccountLogs = builder.userAccountLogs;
		this.userCredentials = builder.userCredentials;
		this.userDetails = builder.userDetails;
		this.userInfo = builder.userInfo;
	}

}

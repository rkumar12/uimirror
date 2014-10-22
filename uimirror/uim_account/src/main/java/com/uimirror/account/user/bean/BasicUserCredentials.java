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
package com.uimirror.account.user.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.account.user.UserAccountDBFields;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.AccountStatus;

/**
 * A Basic User Credentials Object
 * @author Jay
 */
public class BasicUserCredentials extends BeanBasedDocument<BasicUserCredentials> implements BeanValidatorService{

	private static final long serialVersionUID = -4031964375913451254L;
	private List<String> userNames;
	private String password;
	private String screenPassword;
	private AccountState accountState;
	private AccountStatus accountStatus;
	private String encryptionStratgy;
	
	
	//DOn't Use this until it has specific requirement
	public BasicUserCredentials(){
		super();
	}
	
	/**
	 * @param raw
	 */
	public BasicUserCredentials(Map<String, Object> raw) {
		super(raw);
	}

	/**
	 * @param profileId
	 * @param userNames
	 * @param password
	 * @param screenPassword
	 * @param accountState
	 * @param accountStatus
	 * @param encryptionStratgy
	 */
	public BasicUserCredentials(String id, List<String> userNames, String password,
			String screenPassword, AccountState accountState,
			AccountStatus accountStatus, String encryptionStratgy) {
		super(id);
		this.userNames = userNames;
		this.password = password;
		this.screenPassword = screenPassword;
		this.accountState = accountState;
		this.accountStatus = accountStatus;
		this.encryptionStratgy = encryptionStratgy;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public BasicUserCredentials initFromMap(Map<String, Object> src) {
		//Validate the source shouldn't be empty
		validateSource(src);
		//Initialize the state
		return init(src);
	}

	public String getProfileId() {
		return getId();
	}

	public List<String> getUserId() {
		return userNames;
	}

	
	public String getPassword() {
		return password;
	}

	
	public AccountState getAccountState() {
		return accountState;
	}

	
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	
	public String getEncryptionStratgy() {
		return encryptionStratgy;
	}

	
	public String getScreenPassword() {
		return screenPassword;
	}
	
	
	/**
	 * converts a map that comes from DB into BasicUserCredentials object.
	 * @param raw
	 * @return {@link BasicUserCredentials}
	 */
	@SuppressWarnings("unchecked")
	private BasicUserCredentials init(Map<String, Object> raw){
		
		String id = (String)raw.get(UserAccountDBFields.ID);
		//TODO:check if mongo returns back List<String>
		List<String> userNames = (List<String>) raw.get(UserAccountDBFields.USER_ID);
		String password = (String)raw.get(UserAccountDBFields.PASSWORD);
		String screenPassword = (String)raw.get(UserAccountDBFields.SCREEN_PASSWORD);
		String encryptionStratgy = (String)raw.get(UserAccountDBFields.ENCRYPTION_PWD);
		String statVal = (String)raw.get(UserAccountDBFields.ACCOUNT_STATUS);
		String stateVal = (String)raw.get(UserAccountDBFields.ACCOUNT_STATE);
		AccountStatus accountStatus = StringUtils.hasText(statVal) ? AccountStatus.getEnum(statVal) : AccountStatus.ACTIVE;
		AccountState accountState = StringUtils.hasText(stateVal) ? AccountState.getEnum(stateVal) : AccountState.ENABLED;

		return new BasicUserCredentials(id, userNames, password, screenPassword, accountState, accountStatus, encryptionStratgy);
		}
	
	/** 
	 * Checks the necessary fields that needs to be present to demonstrate a state of the client. 
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 * TODO:check if all fields are required
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(!StringUtils.hasText(getId()))
			valid = Boolean.FALSE;
		if(CollectionUtils.isEmpty(getUserId()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getPassword()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getScreenPassword()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getAccountStatus().toString()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getAccountState().toString()))
			valid = Boolean.FALSE;
		
		return valid;
	}
	
	/**
	 * Create a map that needs to be persisted
	 * @return Map<String,Object>
	 * @throws IllegalStateException 
	 */
	@Override
	public Map<String, Object> toMap() throws IllegalStateException{
		//First check if it represents a valid state then can be serialized
		if(!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * @return
	 */
	public Map<String, Object> serailize(){
		Map<String, Object> state = new LinkedHashMap<String, Object>(16);
		state.put(UserAccountDBFields.ID, getId());
		state.put(UserAccountDBFields.USER_ID, getUserId());
		state.put(UserAccountDBFields.PASSWORD, getPassword());
		if(StringUtils.hasText(getScreenPassword()))
			state.put(UserAccountDBFields.SCREEN_PASSWORD, getScreenPassword());
		if(StringUtils.hasText(getEncryptionStratgy()))
			state.put(UserAccountDBFields.ENCRYPTION_PWD, getEncryptionStratgy());
		state.put(UserAccountDBFields.ACCOUNT_STATUS, getAccountStatus().getStatus());
		state.put(UserAccountDBFields.ACCOUNT_STATE, getAccountState().getState());
		return state;
	}

}

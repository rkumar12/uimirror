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

import com.uimirror.account.user.UserAuthDBFields;
import com.uimirror.account.user.UserCredentials;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.AccountStatus;

/**
 * A Basic User Credentials Object
 * @author Jay
 */
public class DefaultUserCredentials extends BeanBasedDocument<DefaultUserCredentials> implements UserCredentials, BeanValidatorService{

	private static final long serialVersionUID = -8054579659925533437L;
	private List<String> userNames;
	private String password;
	private String screenPassword;
	private AccountState accountState;
	private AccountStatus accountStatus;
	private String encryptionStratgy;
	private Map<String, Object> instructions;
	
	//DOn't Use this until it has specific requirement
	public DefaultUserCredentials(){
		super();
	}
	
	/**
	 * @param raw
	 */
	public DefaultUserCredentials(Map<String, Object> raw) {
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
	 * @param instructions
	 */
	public DefaultUserCredentials(String profileId, List<String> userNames, String password,
			String screenPassword, AccountState accountState,
			AccountStatus accountStatus, String encryptionStratgy,
			Map<String, Object> instructions) {
		super(profileId);
		this.userNames = userNames;
		this.password = password;
		this.screenPassword = screenPassword;
		this.accountState = accountState;
		this.accountStatus = accountStatus;
		this.encryptionStratgy = encryptionStratgy;
		this.instructions = instructions;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public DefaultUserCredentials initFromMap(Map<String, Object> src) {
		//Validate the source shouldn't be empty
		validateSource(src);
		//Initialize the state
		return init(src);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.UserCredentials#getProfileId()
	 */
	@Override
	public String getProfileId() {
		return getId();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.UserCredentials#getUserId()
	 */
	@Override
	public List<String> getUserId() {
		return userNames;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.UserCredentials#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.UserCredentials#getAccountState()
	 */
	@Override
	public AccountState getAccountState() {
		return accountState;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.UserCredentials#getAccountStatus()
	 */
	@Override
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.UserCredentials#getEncryptionStratgy()
	 */
	@Override
	public String getEncryptionStratgy() {
		return encryptionStratgy;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.UserCredentials#getInstructions()
	 */
	@Override
	public Map<String, Object> getInstructions() {
		return instructions;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.user.bean.UserCredentials#getScreenPassword()
	 */
	@Override
	public String getScreenPassword() {
		return screenPassword;
	}
	
	@SuppressWarnings("unchecked")
	private DefaultUserCredentials init(Map<String, Object> raw){
		String id = (String)raw.get(UserAuthDBFields.ID);
		List<String> userNames = (List<String>) raw.get(UserAuthDBFields.USER_ID);
		String password = (String)raw.get(UserAuthDBFields.PASSWORD);
		String screenPassword = (String)raw.get(UserAuthDBFields.SCREEN_PASSWORD);
		String encryptionStratgy = (String)raw.get(UserAuthDBFields.ENCRYPTION_PWD);
		Map<String, Object> instructions = (Map<String, Object>)raw.get(UserAuthDBFields.ACCOUNT_INSTRUCTION);
		String statVal = (String)raw.get(UserAuthDBFields.ACCOUNT_STATUS);
		String stateVal = (String)raw.get(UserAuthDBFields.ACCOUNT_STATE);
		AccountStatus accountStatus = StringUtils.hasText(statVal) ? AccountStatus.getEnum(statVal) : AccountStatus.ACTIEVE;
		AccountState accountState = StringUtils.hasText(stateVal) ? AccountState.getEnum(stateVal) : AccountState.ENABLED;
		return new DefaultUserCredentials(id, userNames, password, screenPassword, accountState, accountStatus, encryptionStratgy, instructions);
	}
	
	/** 
	 * Checks the necessary fields that needs to be present to demonstrate a state of the client. 
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
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
		return valid;
	}
	
	/**
	 * Create a map that needs to be persisted
	 * @return
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
		state.put(UserAuthDBFields.ID, getId());
		state.put(UserAuthDBFields.USER_ID, getUserId());
		state.put(UserAuthDBFields.PASSWORD, getPassword());
		if(StringUtils.hasText(getScreenPassword()))
			state.put(UserAuthDBFields.SCREEN_PASSWORD, getScreenPassword());
		if(StringUtils.hasText(getEncryptionStratgy()))
			state.put(UserAuthDBFields.ENCRYPTION_PWD, getEncryptionStratgy());
		if(!CollectionUtils.isEmpty(getInstructions()))
			state.put(UserAuthDBFields.ACCOUNT_INSTRUCTION, getInstructions());
		state.put(UserAuthDBFields.ACCOUNT_STATUS, getAccountStatus().getStatus());
		state.put(UserAuthDBFields.ACCOUNT_STATE, getAccountState().getState());
		return state;
	}

}

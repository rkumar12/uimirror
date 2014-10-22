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
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * A Basic User Credentials Object
 * @author Jay
 */
public class UserCredentials extends BeanBasedDocument<UserCredentials> implements BeanValidatorService{

	private static final long serialVersionUID = -8054579659925533437L;
	private List<String> userNames;
	private String password;
	private String screenPassword;
	private AccountState accountState;
	private AccountStatus accountStatus;
	private String encryptionStratgy;
	private Map<String, Object> instructions;
	
	//DOn't Use this until it has specific requirement
	public UserCredentials(){
		super();
	}
	
	/**
	 * @param raw
	 */
	public UserCredentials(Map<String, Object> raw) {
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
	public UserCredentials(String profileId, List<String> userNames, String password,
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
	
	public UserCredentials updateProfileId(String profileId) {
		return new UserCredentials(profileId, userNames, password, screenPassword, accountState, accountStatus, encryptionStratgy, instructions);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public UserCredentials initFromMap(Map<String, Object> src) {
		//Validate the source shouldn't be empty
		validateSource(src);
		//Initialize the state
		return init(src);
	}
	
	@SuppressWarnings("unchecked")
	private UserCredentials init(Map<String, Object> raw){
		String id = (String)raw.get(UserAuthDBFields.ID);
		List<String> userNames = (List<String>) raw.get(UserAuthDBFields.USER_ID);
		String password = (String)raw.get(UserAuthDBFields.PASSWORD);
		String screenPassword = (String)raw.get(UserAuthDBFields.SCREEN_PASSWORD);
		String encryptionStratgy = (String)raw.get(UserAuthDBFields.ENCRYPTION_PWD);
		Map<String, Object> instructions = (Map<String, Object>)raw.get(UserAuthDBFields.ACCOUNT_INSTRUCTION);
		String statVal = (String)raw.get(UserAuthDBFields.ACCOUNT_STATUS);
		String stateVal = (String)raw.get(UserAuthDBFields.ACCOUNT_STATE);
		AccountStatus accountStatus = StringUtils.hasText(statVal) ? AccountStatus.getEnum(statVal) : AccountStatus.ACTIVE;
		AccountState accountState = StringUtils.hasText(stateVal) ? AccountState.getEnum(stateVal) : AccountState.ENABLED;
		return new UserCredentials(id, userNames, password, screenPassword, accountState, accountStatus, encryptionStratgy, instructions);
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

	public Map<String, Object> getInstructions() {
		return instructions;
	}

	public String getScreenPassword() {
		return screenPassword;
	}

}
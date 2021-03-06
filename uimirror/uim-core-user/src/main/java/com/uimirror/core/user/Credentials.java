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

import static com.uimirror.core.mongo.feature.BasicDBFields.ID;
import static com.uimirror.core.user.UserAuthDBFields.ACCOUNT_INSTRUCTION;
import static com.uimirror.core.user.UserAuthDBFields.ACCOUNT_STATE;
import static com.uimirror.core.user.UserAuthDBFields.ACCOUNT_STATUS;
import static com.uimirror.core.user.UserAuthDBFields.ENCRYPTION_PWD;
import static com.uimirror.core.user.UserAuthDBFields.PASSWORD;
import static com.uimirror.core.user.UserAuthDBFields.USER_ID;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.AccountState;
import com.uimirror.core.AccountStatus;
import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * A Basic User Credentials Object
 * @author Jay
 */
public class Credentials extends AbstractBeanBasedDocument<Credentials> implements BeanValidatorService{

	private static final long serialVersionUID = -8054579659925533437L;
	private List<String> userNames;
	private String password;
	private AccountState accountState;
	private AccountStatus accountStatus;
	private String encryptionStratgy;
	private Map<String, Object> instructions;
	
	/** 
	 * Checks the necessary fields that needs to be present to demonstrate a state of the client. 
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(CollectionUtils.isEmpty(getUserId()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getPassword()))
			valid = Boolean.FALSE;
		if(accountState == null)
			valid = Boolean.FALSE;
		if(accountStatus == null)
			valid = Boolean.FALSE;
		return valid;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#updateId()
	 */
	@Override
	public Credentials updateId(String id) {
		return new CredentialsBuilder(id).
				addEncStartegy(encryptionStratgy).
				addInstructions(instructions).
				addPassword(password).
				addState(accountState).
				addStatus(accountStatus).
				addUserNames(userNames).build();
	}

	public Credentials enable() {
		return new CredentialsBuilder(getProfileId()).
				addEncStartegy(encryptionStratgy).
				addInstructions(instructions).
				addPassword(password).
				addState(AccountState.ENABLED).
				addStatus(accountStatus).
				addUserNames(userNames).build();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public Credentials readFromMap(Map<String, Object> src) {
		//Validate the source shouldn't be empty
		isValidSource(src);
		//Initialize the state
		return init(src);
	}
	
	@SuppressWarnings("unchecked")
	private Credentials init(Map<String, Object> raw){
		String id = (String)raw.get(ID);
		List<String> userNames = (List<String>) raw.get(USER_ID);
		String password = (String)raw.get(PASSWORD);
		String encryptionStratgy = (String)raw.get(ENCRYPTION_PWD);
		Map<String, Object> instructions = (Map<String, Object>)raw.get(ACCOUNT_INSTRUCTION);
		String statVal = (String)raw.get(ACCOUNT_STATUS);
		String stateVal = (String)raw.get(ACCOUNT_STATE);
		AccountStatus accountStatus = StringUtils.hasText(statVal) ? AccountStatus.getEnum(statVal) : AccountStatus.ACTIVE;
		AccountState accountState = StringUtils.hasText(stateVal) ? AccountState.getEnum(stateVal) : AccountState.ENABLED;
		CredentialsBuilder build = new CredentialsBuilder(id).
				addEncStartegy(encryptionStratgy).
				addInstructions(instructions).
				addPassword(password).
				addState(accountState).
				addStatus(accountStatus).
				addUserNames(userNames);
		return build.build();
	}
	
	/**
	 * Create a map that needs to be persisted
	 * @return state in a {@link Map}
	 * @throws IllegalStateException  when the state is not valid
	 */
	@Override
	public Map<String, Object> writeToMap() throws IllegalStateException{
		//First check if it represents a valid state then can be serialized
		if(!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * @return serialized {@link Map}
	 */
	public Map<String, Object> serailize(){
		Map<String, Object> state = new LinkedHashMap<String, Object>(16);
		state.put(ID, getId());
		state.put(USER_ID, getUserId());
		state.put(PASSWORD, getPassword());
		if(StringUtils.hasText(getEncryptionStratgy()))
			state.put(ENCRYPTION_PWD, getEncryptionStratgy());
		if(!CollectionUtils.isEmpty(getInstructions()))
			state.put(ACCOUNT_INSTRUCTION, getInstructions());
		state.put(ACCOUNT_STATUS, getAccountStatus().getStatus());
		state.put(ACCOUNT_STATE, getAccountState().getState());
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
	
	public static class CredentialsBuilder implements Builder<Credentials>{
		
		private String profileId;
		private List<String> userNames;
		private String password;
		private AccountState accountState;
		private AccountStatus accountStatus;
		private String encryptionStratgy;
		private Map<String, Object> instructions;
		
		public CredentialsBuilder(String profileId) {
			this.profileId = profileId;
		}
		
		public CredentialsBuilder addUserNames(List<String> userNames){
			this.userNames = userNames;
			return this;
		}
		
		public CredentialsBuilder addPassword(String password){
			this.password = password;
			return this;
		}
		
		public CredentialsBuilder addState(String state){
			this.accountState = AccountState.getEnum(state);
			return this;
		}
		
		public CredentialsBuilder addState(AccountState state){
			this.accountState = state;
			return this;
		}
		
		public CredentialsBuilder addStatus(String status){
			this.accountStatus = AccountStatus.getEnum(status);
			return this;
		}
		
		public CredentialsBuilder addStatus(AccountStatus status){
			this.accountStatus = status;
			return this;
		}
		
		public CredentialsBuilder addEncStartegy(String startegy){
			this.encryptionStratgy = startegy;
			return this;
		}
		
		public CredentialsBuilder addInstructions(Map<String, Object> instructions){
			this.instructions = instructions;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public Credentials build() {
			return new Credentials(this);
		}
		
	}
	
	private Credentials(CredentialsBuilder builder){
		super(builder.profileId);
		this.accountState = builder.accountState;
		this.accountStatus = builder.accountStatus;
		this.encryptionStratgy = builder.encryptionStratgy;
		this.instructions = builder.instructions;
		this.password = builder.password;
		this.userNames = builder.userNames;
	}
	
	//DOn't Use this until it has specific requirement
	public Credentials(){
		//NOP
		this.userNames = null;
		this.password = null;
		this.accountState = null;
		this.accountStatus = null;
		this.encryptionStratgy = null;
		this.instructions = null;
	}
	
	@Override
	public String toString() {
		return "Credentials [userNames=" + userNames + ", password=[*********]"
				+ ", screenPassword= [*********], accountState="
				+ accountState + ", accountStatus=" + accountStatus
				+ ", encryptionStratgy=" + encryptionStratgy
				+ ", instructions=" + instructions + "]";
	}

}

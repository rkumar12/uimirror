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
package com.uimirror.auth.user;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.uimirror.auth.bean.AccountState;
import com.uimirror.auth.bean.AccountStatus;
import com.uimirror.auth.user.bean.UserCredentials;
import com.uimirror.core.mongo.feature.BeanBasedDocument;

/**
 * A Basic User Credentials Object
 * @author Jay
 */
public class DefaultUserCredentials extends BeanBasedDocument implements UserCredentials{

	private static final long serialVersionUID = -8054579659925533437L;
	private List<String> userNames;
	private String password;
	private String screenPassword;
	private AccountState accountState;
	private AccountStatus accountStatus;
	private String encryptionStratgy;
	private Map<String, Object> instructions;
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public DefaultUserCredentials(Map<String, Object> raw) {
		super((String)raw.get(UserAuthDBFields.ID));
		this.userNames = (List<String>) raw.get(UserAuthDBFields.USER_ID);
		this.password = (String)raw.get(UserAuthDBFields.PASSWORD);
		this.encryptionStratgy = (String)raw.get(UserAuthDBFields.ENCRYPTION_PWD);
		this.instructions = (Map<String, Object>)raw.get(UserAuthDBFields.ACCOUNT_INSTRUCTION);
		String status = (String)raw.get(UserAuthDBFields.ACCOUNT_STATUS);
		String state = (String)raw.get(UserAuthDBFields.ACCOUNT_STATE);
		this.accountStatus = StringUtils.hasText(status) ? AccountStatus.getEnum(status) : AccountStatus.ACTIEVE;
		this.accountState = StringUtils.hasText(state) ? AccountState.getEnum(state) : AccountState.ENABLED;
	}

	

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public UserCredentials initFromMap(Map<String, Object> src) {
		//This Implementation not required as value is already de-serialized
		return null;
	}

	public List<String> getUserNames() {
		return userNames;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.UserCredentials#getProfileId()
	 */
	@Override
	public String getProfileId() {
		return getId();
	}



	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.UserCredentials#getUserId()
	 */
	@Override
	public List<String> getUserId() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.UserCredentials#getPassword()
	 */
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.UserCredentials#getAccountState()
	 */
	@Override
	public AccountState getAccountState() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.UserCredentials#getAccountStatus()
	 */
	@Override
	public AccountStatus getAccountStatus() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.UserCredentials#getEncryptionStratgy()
	 */
	@Override
	public String getEncryptionStratgy() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.UserCredentials#getInstructions()
	 */
	@Override
	public Map<String, Object> getInstructions() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.uimirror.auth.user.bean.UserCredentials#getScreenPassword()
	 */
	@Override
	public String getScreenPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

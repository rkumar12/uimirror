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

import com.uimirror.auth.DBFileds;
import com.uimirror.auth.bean.AccountState;
import com.uimirror.auth.bean.AccountStatus;
import com.uimirror.auth.bean.BasicCredentials;
import com.uimirror.core.mongo.feature.BeanBasedDocument;

/**
 * A Basic User Credentials Object
 * @author Jay
 */
public class BasicUserCredentials extends BeanBasedDocument implements BasicCredentials{

	private static final long serialVersionUID = -8054579659925533437L;
	private final List<String> userNames;
	private final String password;
	private final AccountState accountState;
	private final AccountStatus accountStatus;
	private final String encryptionStratgy;
	private final Map<String, Object> instructions;
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public BasicUserCredentials(Map<String, Object> raw) {
		super((String)raw.get(DBFileds.ID));
		this.userNames = (List<String>) raw.get(DBFileds.UC_USER_ID);
		this.password = (String)raw.get(DBFileds.PASSWORD);
		this.encryptionStratgy = (String)raw.get(DBFileds.UC_ENCRYPTION_PWD);
		this.instructions = (Map<String, Object>)raw.get(DBFileds.UC_ACCOUNT_INSTRUCTION);
		String status = (String)raw.get(DBFileds.UC_ACCOUNT_STATUS);
		String state = (String)raw.get(DBFileds.UC_ACCOUNT_STATE);
		this.accountStatus = StringUtils.hasText(status) ? AccountStatus.getEnum(status) : AccountStatus.ACTIEVE;
		this.accountState = StringUtils.hasText(state) ? AccountState.getEnum(state) : AccountState.ENABLED;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.BasicCredentials#getUserId()
	 */
	@Override
	public Object getUserId() {
		return super.getId();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.BasicCredentials#getPassword()
	 */
	@Override
	public Object getPassword() {
		return this.password;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.BasicCredentials#getAccountState()
	 */
	@Override
	public AccountState getAccountState() {
		return this.accountState;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.BasicCredentials#getAccountStatus()
	 */
	@Override
	public AccountStatus getAccountStatus() {
		return this.accountStatus;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.BasicCredentials#getEncryptionStratgy()
	 */
	@Override
	public Object getEncryptionStratgy() {
		return this.encryptionStratgy;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.BasicCredentials#getInstructions()
	 */
	@Override
	public Map<String, Object> getInstructions() {
		return this.instructions;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.BasicCredentials#getRedirectUri()
	 */
	@Override
	public String getRedirectUri() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public void initFromMap(Map<String, Object> src) {
		//This Implementation not required as value is already de-serialized
	}

	public List<String> getUserNames() {
		return userNames;
	}

}

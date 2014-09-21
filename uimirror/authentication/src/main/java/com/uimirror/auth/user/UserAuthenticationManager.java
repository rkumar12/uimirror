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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.uimirror.auth.AuthExceptionMapper;
import com.uimirror.auth.DBFileds;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AccountState;
import com.uimirror.core.auth.AccountStatus;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.AuthenticationException;
import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.BadCredentialsException;
import com.uimirror.core.auth.dao.CredentialsStore;
import com.uimirror.core.extra.MapException;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will issue or re issue the {@link AccessToken}
 * 
 * @author Jay
 */
@Service
public class UserAuthenticationManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(UserAuthenticationManager.class);
	
	private @Autowired CredentialsStore userCredentialStore;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(by=AuthExceptionMapper.class)
	public AccessToken authenticate(Authentication authentication) throws AuthenticationException {
		LOG.info("[START]- validating user credentials");
		Map<String, Object> userCredential = getAuthenticationDetails(authentication.getName());
		
		UserCredentials usr = new UserCredentials(userCredential);
		LOG.info("[END]- validating user credentials");
		return null;
	}
	
	/**
	 * Will try to find the user based on the user ID, if no record then throw
	 * an {@link BadCredentialsException}
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getAuthenticationDetails(String userId){
		return (Map<String, Object>)userCredentialStore.getCredentials(userId);
	}
	
	private final class UserCredentials{
		
		private final String userId;
		private final List<String> userNames;
		private final String password;
		private final AccountState accountState;
		private final AccountStatus accountStatus;
		private final String encryptionStratgy;
		private final Map<String, Object> instructions;
		
		public UserCredentials(Map<String, Object> raw){
			this.userId = (String)raw.get(DBFileds.ID);
			this.userNames = (List<String>) raw.get(DBFileds.UC_USER_ID);
			this.password = (String)raw.get(DBFileds.PASSWORD);
			this.encryptionStratgy = (String)raw.get(DBFileds.UC_ENCRYPTION_PWD);
			this.instructions = (Map<String, Object>)raw.get(DBFileds.UC_ACCOUNT_INSTRUCTION);
			String status = (String)raw.get(DBFileds.UC_ACCOUNT_STATUS);
			String state = (String)raw.get(DBFileds.UC_ACCOUNT_STATE);
			this.accountStatus = StringUtils.hasText(status) ? AccountStatus.getEnum(status) : AccountStatus.ACTIEVE;
			this.accountState = StringUtils.hasText(state) ? AccountState.getEnum(state) : AccountState.ENABLED;
		}

		public String getUserId() {
			return userId;
		}

		public List<String> getUserNames() {
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
		
	}

}

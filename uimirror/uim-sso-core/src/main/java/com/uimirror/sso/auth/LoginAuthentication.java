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
package com.uimirror.sso.auth;

import java.util.Map;

import com.uimirror.core.Builder;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.token.AccessTokenFields;
import com.uimirror.sso.auth.OAuth2Authentication;

/**
 * AccessToken <code>Authentication</code> objects.
 * <p>
 * Implementations which use this class should be immutable.
 * Should accomodate the following details
 * accessToken=code&
 * tokenEncryptionStartegy=parapharse
 * &userId=userid&password=password&keepMeLogedIn=y
 * @author Jay
 */
public class LoginAuthentication extends OAuth2Authentication{

	private static final long serialVersionUID = -4739920105672151406L;
	
	public static class LoginAuthBuilder implements Builder<LoginAuthentication>{
		
		private String token; 
		private String userId;
		private String password;
		private boolean keepMeLoggedIn;
		private String ip;
		private String userAgent;
		
		public LoginAuthBuilder(String token){
			this.token = token;
		}
		public LoginAuthBuilder addUserId(String userId){
			this.userId = userId;
			return this;
		}
		public LoginAuthBuilder addPassword(String password){
			this.password = password;
			return this;
		}
		public LoginAuthBuilder addKeepMeLoggedIn(){
			this.keepMeLoggedIn = Boolean.TRUE;
			return this;
		}
		
		public LoginAuthBuilder addIp(String ip){
			this.ip = ip;
			return this;
		}
		public LoginAuthBuilder addAgent(String userAgent){
			this.userAgent = userAgent;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public LoginAuthentication build() {
			return new LoginAuthentication(this);
		}
		
	}
	private LoginAuthentication(LoginAuthBuilder builder){
		super(builder.token, builder.ip, builder.userAgent);
		init(builder.userId, builder.password, builder.keepMeLoggedIn);
	}

	/**
	 * @param tokenPrincipal
	 * @param details
	 */
	public LoginAuthentication(String tokenPrincipal, Map<String, Object> details) {
		super(tokenPrincipal, details);
	}
	
	/**
	 * @param tokenPrincipal
	 * @param details
	 */
	public LoginAuthentication(Object tokenPrincipal, Map<String, Object> details) {
		super(tokenPrincipal, details);
	}
	
	/**
	 * @param tokenPrincipal
	 */
	public LoginAuthentication(Object tokenPrincipal) {
		super(tokenPrincipal);
	}
	
	/**
	 * @param userId
	 * @param password
	 * @param keepMeLoggedIn
	 */
	private void init(String userId, String password, boolean keepMeLoggedIn){
		addCrdentials(password);
		addDetails(userId, keepMeLoggedIn);
	}
	
	/**
	 * @param password
	 */
	@SuppressWarnings("unchecked")
	private void addCrdentials(String password){
		Map<String, String> credentials = (Map<String, String>)getCredentials();
		credentials.put(AuthConstants.PASSWORD, password);
		updateCredentials(credentials);
	}
	
	/**
	 * @param userId
	 * @param keepMeLoggedIn
	 */
	@SuppressWarnings("unchecked")
	private void addDetails(String userId, boolean keepMeLoggedIn){
		Map<String, Object> details = (Map<String, Object>)getDetails();
		details.put(AuthConstants.USER_ID, userId);
		details.put(AuthConstants.KEEP_ME_LOGIN, keepMeLoggedIn);
		setDetails(details);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected String computePrincipalOwner(){
		String owner = null;
		Map<String, Object> details = (Map<String, Object>) getDetails();
		owner = (String) details.get(AuthConstants.USER_ID);
		if(owner == null){
			owner = (String)details.get(AccessTokenFields.AUTH_TKN_OWNER);
		}
		return owner;
	}

}

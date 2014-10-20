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
package com.uimirror.account.auth.user.bean;

import java.util.Map;

import com.uimirror.account.auth.client.bean.OAuth2Authentication;
import com.uimirror.account.auth.core.AccessTokenFields;
import com.uimirror.core.auth.AuthConstants;

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
	
	/**
	 * @param token
	 * @param userId
	 * @param password
	 * @param keepMeLoggedIn
	 * @param ip
	 * @param userAgent
	 */
	public LoginAuthentication(String token, String userId, String password, boolean keepMeLoggedIn, String ip, String userAgent) {
		super(token, ip, userAgent);
		init(userId, password, keepMeLoggedIn);
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

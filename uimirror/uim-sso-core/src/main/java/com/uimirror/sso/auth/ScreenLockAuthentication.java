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

import com.uimirror.core.auth.AuthConstants;
import com.uimirror.sso.auth.OAuth2Authentication;

/** 
 * AccessToken <code>Authentication</code> objects.
 * <p>
 * Implementations which use this class should be immutable.
 * Should accomodate the following details
 * accessToken=code&
 * tokenEncryptionStartegy=parapharse
 * &password=password
 * @author Jay
 */
public class ScreenLockAuthentication extends OAuth2Authentication{
	
	private static final long serialVersionUID = 3795112886906141341L;

	/**
	 * @param token
	 * @param password
	 */
	public ScreenLockAuthentication(String token, String password) {
		super(token);
		init(password);
	}
	/**
	 * @param token
	 * @param password
	 * @param ip
	 * @param userAgent
	 */
	public ScreenLockAuthentication(String token, String password, String ip, String userAgent) {
		super(token, ip, userAgent);
		init(password);
	}

	/**
	 * @param tokenPrincipal
	 * @param details
	 */
	public ScreenLockAuthentication(Object tokenPrincipal, Map<String, Object> details) {
		super(tokenPrincipal, details);
	}
	
	/**
	 * @param tokenPrincipal
	 */
	public ScreenLockAuthentication(Object tokenPrincipal) {
		super(tokenPrincipal);
	}
	
	/**
	 * @param password
	 */
	private void init(String password){
		addCrdentials(password);
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
	
}

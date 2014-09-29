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
package com.uimirror.auth.user.bean;

import java.util.Map;

import org.springframework.util.Assert;

import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.bean.Scope;

/** 
 * <p>Contains the user authentication details which will be the principal details of the user which should be of type form</p>
 * <p>Which might have only password field but doesn't have any accesskey Header information</p>
 * This details will be erased once authentication process over by {@link AuthenticationManager#authenticate(Authentication)}
 * This expects a password field needs to be populated
 * @author Jay
 */
public class ScreenLockAuthentication extends AccessKeyBasedAuthentication{
	
	private static final long serialVersionUID = 3795112886906141341L;
	
	private String password;

	/**
	 * @param accesskey
	 * @param paraPharse
	 * @param ip
	 * @param uAgent
	 */
	private ScreenLockAuthentication(String accesskey, String paraPharse, String ip, String uAgent) {
		super(CredentialType.SCREENLOCK, accesskey, paraPharse, ip, uAgent);
	}

	/**
	 * @param accessKey
	 * @param paraPharse
	 * @param details
	 * @param ip
	 * @param uAgent
	 */
	private ScreenLockAuthentication(String accessKey, String paraPharse, Map<String, Object> details, String ip, String uAgent) {
		super(CredentialType.SCREENLOCK, accessKey, paraPharse, details, ip, uAgent);
	}

	/**
	 * @param accessKey
	 * @param paraPharse
	 * @param details
	 */
	private ScreenLockAuthentication(String accessKey, String paraPharse, Map<String, Object> details) {
		super(CredentialType.SCREENLOCK, accessKey, paraPharse, details);
	}
	
	/**
	 * @param accessKey
	 * @param details
	 */
	private ScreenLockAuthentication(String accessKey, Map<String, Object> details) {
		super(CredentialType.SCREENLOCK, accessKey, details);
	}
	
	/**
	 * @param accessKey
	 * @param ip
	 * @param uAgent
	 */
	private ScreenLockAuthentication(String accessKey, String ip, String uAgent) {
		super(CredentialType.SCREENLOCK, accessKey, ip, uAgent);
	}

	/**
	 * @param accessKey
	 * @param paraPharse
	 * @param password
	 * @param details
	 * @param ip
	 * @param uAgent
	 */
	public ScreenLockAuthentication(String accessKey, String paraPharse, String password, Map<String, Object> details, String ip, String uAgent) {
		this(accessKey, paraPharse, details, ip, uAgent);
		intialize(password);
	}

	/**
	 * @param accessKey
	 * @param paraPharse
	 * @param password
	 * @param details
	 */
	public ScreenLockAuthentication(String accessKey, String paraPharse, String password, Map<String, Object> details) {
		this(accessKey, paraPharse, details);
		intialize(password);
	}
	
	/**
	 * @param details
	 * @param password
	 * @param accessKey
	 */
	public ScreenLockAuthentication(Map<String, Object> details, String password, String accessKey) {
		this(accessKey, details);
		intialize(password);
	}
	
	/**
	 * @param accessKey
	 * @param paraPharse
	 * @param password
	 * @param ip
	 * @param uAgent
	 */
	public ScreenLockAuthentication(String accessKey, String paraPharse, String password, String ip, String uAgent) {
		this(accessKey, paraPharse, ip, uAgent);
		intialize(password);
	}
	
	private void intialize(String password){
		Assert.hasText(password, "Password Can't Be Empty");
		this.password = password;
	}
	
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getCredentials()
	 */
	@Override
	public String getCredentials() {
		return this.password;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getAuthenticationScope()
	 */
	@Override
	public Scope getAuthenticationScope() {
		return null;
	}

	@Override
	public String toString() {
		return "FormBasedAuthentication [password=<<*****>>]";
	}
	
}

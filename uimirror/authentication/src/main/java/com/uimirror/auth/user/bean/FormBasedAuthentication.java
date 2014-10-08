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

import com.uimirror.auth.bean.Authentication;
import com.uimirror.auth.bean.CommonAuthentication;
import com.uimirror.auth.bean.CredentialType;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.core.auth.Scope;

/** 
 * <p>Contains the user authentication details which will be the principal details of the user which should be of type form</p>
 * <p>Which might have only password field but doesn't have any accesskey Header information</p>
 * This details will be erased once authentication process over by {@link AuthenticationManager#authenticate(Authentication)}
 * This expects a password field and extra details needs to be populated
 * @author Jay
 */
public abstract class FormBasedAuthentication extends CommonAuthentication{
	
	private static final long serialVersionUID = 3795112886906141341L;
	
	private String password;

	/**
	 * @param type
	 * @param ip
	 * @param uAgent
	 */
	private FormBasedAuthentication(CredentialType type, String ip, String uAgent) {
		super(type, ip, uAgent);
	}
	
	/**
	 * @param details
	 * @param type
	 * @param ip
	 * @param uAgent
	 */
	private FormBasedAuthentication(Map<String, Object> details, CredentialType type, String ip, String uAgent) {
		super(type, details, ip, uAgent);
	}
	
	/**
	 * @param details
	 * @param type
	 */
	private FormBasedAuthentication(Map<String, Object> details, CredentialType type) {
		super(type, details);
	}

	/**
	 * @param type
	 * @param password
	 * @param details
	 * @param ip
	 * @param uAgent
	 */
	public FormBasedAuthentication(CredentialType type, String password, Map<String, Object> details, String ip, String uAgent) {
		this(details, type, ip, uAgent);
		intialize(password);
	}

	/**
	 * @param type
	 * @param password
	 * @param details
	 */
	public FormBasedAuthentication(CredentialType type, String password, Map<String, Object> details) {
		this(details, type);
		intialize(password);
	}
	
	/**
	 * @param type
	 * @param password
	 * @param ip
	 * @param uAgent
	 */
	public FormBasedAuthentication(CredentialType type, String password, String ip, String uAgent) {
		this(type, ip, uAgent);
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

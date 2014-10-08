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

import com.uimirror.auth.bean.CredentialType;

/**
 * User Login form that server has submitted and needs to populate as part of the authentication
 * @author Jay
 */
public class LoginFormAuthentication extends FormBasedAuthentication{

	private static final long serialVersionUID = -4739920105672151406L;
	private String id;
	private boolean keepMeLogin;
	
	private LoginFormAuthentication(String password, String ip, String uAgent){
		super(CredentialType.LOGINFORM, password, ip, uAgent);
	}
	private LoginFormAuthentication(String password, Map<String, Object> details, String ip, String uAgent){
		super(CredentialType.LOGINFORM, password, details, ip, uAgent);
	}
	private LoginFormAuthentication(String password, Map<String, Object> details) {
		super(CredentialType.LOGINFORM, password, details);
	}
	/**
	 * @param id
	 * @param password
	 * @param keepmeLogedIn
	 * @param details
	 * @param ip
	 * @param uAgent
	 */
	public LoginFormAuthentication(String id, String password, boolean keepmeLogedIn, Map<String, Object> details, String ip, String uAgent) {
		this(password, ip, uAgent);
		intialize(id, keepmeLogedIn);
	}

	/**
	 * @param id
	 * @param password
	 * @param keepmeLogedIn
	 * @param ip
	 * @param uAgent
	 */
	public LoginFormAuthentication(String id, String password, boolean keepmeLogedIn, String ip, String uAgent){
		this(password, ip, uAgent);
		intialize(id, keepmeLogedIn);
	}
	
	/**
	 * @param id
	 * @param password
	 * @param keepmeLogedIn
	 * @param details
	 */
	public LoginFormAuthentication(String id, String password, boolean keepmeLogedIn, Map<String, Object> details){
		this(password, details);
		intialize(id, keepmeLogedIn);
	}
	/**
	 * Intialize the objects
	 * @param id
	 * @param keepMeLogedIn
	 */
	private void intialize(String id, boolean keepMeLogedIn){
		this.id = id;
		this.keepMeLogin = keepMeLogedIn;
	}
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#keepMeLogin()
	 */
	@Override
	public boolean keepMeLogin() {
		return this.keepMeLogin;
	}
	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return this.id;
	}

}

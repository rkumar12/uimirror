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
import com.uimirror.core.auth.bean.CommonAuthentication;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.bean.Scope;

/** 
 * <p>Contains the user authentication details which will be the principal details of the user</p>
 * <p>implements the {@link Authentication} which has basic user id in raw format and password in encrypted format</p>
 * This details will be erased once authentication process over by {@link AuthenticationManager#authenticate(Authentication)}
 * This expects a user id and password field needs to be populated
 * @author Jay
 */
public class FormBasedAuthentication extends CommonAuthentication{
	
	private static final long serialVersionUID = 3795112886906141341L;

	private String id;
	private String password;
	private Map<String, Object> details;
	private boolean keepMeLogin;

	/**
	 * @param type
	 * @param ip
	 * @param uAgent
	 */
	private FormBasedAuthentication(CredentialType type, String ip, String uAgent) {
		super(type, ip, uAgent);
	}
	
	public FormBasedAuthentication(String id, String password, Map<String, Object> details, boolean keepMeLogin, String ip, String uAgent) {
		this(CredentialType.LOGINFORM, ip, uAgent);
		Assert.hasText(id, "User Identifier can't be empty");
		Assert.hasText(password, "Password Can't be empty");
		this.id = id;
		this.password = password;
		this.keepMeLogin = keepMeLogin;
		this.details = details;
	}

	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return this.id;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getCredentials()
	 */
	@Override
	public String getCredentials() {
		return this.password;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getDetails()
	 */
	@Override
	public Map<String, Object> getDetails() {
		return this.details;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#keepMeLogin()
	 */
	@Override
	public boolean keepMeLogin() {
		return this.keepMeLogin;
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
		return "FormBasedAuthentication [id=" + id + ", password=<<******>>"
				+ ", details=" + details + ", keepMeLogin=" + keepMeLogin + "]";
	}
	
	

}

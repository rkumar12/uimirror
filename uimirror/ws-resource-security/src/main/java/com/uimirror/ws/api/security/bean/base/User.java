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
package com.uimirror.ws.api.security.bean.base;

import java.util.Set;

import com.uimirror.ws.api.security.Scope;
import com.uimirror.ws.api.security.ouath.License;

/**
 * <p>This holds the basic information about the user</p>
 * <p>This will be used for the normal user basic details as principal</p>
 * <p>Password will be auto erased once this field required by other modules but not by this</p>
 * @author Jay
 *
 */
public class User {
	
	//This is also known as profile id for the user, which will be unique per user
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private Set<String> userId;
	private License license;
	private String password;
	//User may have multiple scope for a single application
	private Set<Scope> scopes;
	

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getUserId() {
		return userId;
	}

	public void setUserId(Set<String> userId) {
		this.userId = userId;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Scope> getScopes() {
		return scopes;
	}

	public void setScopes(Set<Scope> scopes) {
		this.scopes = scopes;
	}

	
}

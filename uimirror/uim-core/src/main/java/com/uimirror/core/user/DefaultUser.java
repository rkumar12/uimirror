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
package com.uimirror.core.user;


/**
 * A Default User representing the UIM System 
 * @author Jay
 */
public class DefaultUser{

	private BasicUserInfo userInfo;
	private UserCredentials userCredentials;
	private BasicUserDetails userDetails;
	private DefaultUserAccountLogs userAccountLogs;

	public DefaultUser(BasicUserInfo userInfo, UserCredentials userCredentials, BasicUserDetails userDetails, DefaultUserAccountLogs userAccountLogs) {
		super();
		this.userInfo = userInfo;
		this.userCredentials = userCredentials;
		this.userDetails = userDetails;
		this.userAccountLogs = userAccountLogs;
	}
	
	/**
	 * Updates the User info
	 * @param userInfo
	 * @return
	 */
	public DefaultUser updateInfo(BasicUserInfo userInfo){
		return new DefaultUser(userInfo, this.userCredentials, this.userDetails, this.userAccountLogs);
	}
	
	/**
	 * Updates the User Credentials
	 * @param userInfo
	 * @return
	 */
	public DefaultUser updateCredentials(UserCredentials userCredentials){
		return new DefaultUser(this.userInfo, userCredentials, this.userDetails, this.userAccountLogs);
	}
	
	/**
	 * Updates the User Details
	 * @param userInfo
	 * @return
	 */
	public DefaultUser updateDetails(BasicUserDetails userDetails){
		return new DefaultUser(this.userInfo, this.userCredentials, userDetails, this.userAccountLogs);
	}
	
	/**
	 * Updates the User Details
	 * @param userInfo
	 * @return
	 */
	public DefaultUser updateLogs(DefaultUserAccountLogs userAccountLogs){
		return new DefaultUser(this.userInfo, this.userCredentials, this.userDetails, userAccountLogs);
	}

	public BasicUserInfo getUserInfo() {
		return this.userInfo;
	}

	public UserCredentials getCredentials() {
		return this.userCredentials;
	}

	public BasicUserDetails getBasicDetails() {
		return this.userDetails;
	}

	public DefaultUserAccountLogs getLogs() {
		return this.userAccountLogs;
	}

}

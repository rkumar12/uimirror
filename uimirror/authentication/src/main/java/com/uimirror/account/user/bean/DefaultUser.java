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
package com.uimirror.account.user.bean;

import com.uimirror.core.user.User;
import com.uimirror.core.user.UserAccountLogs;
import com.uimirror.core.user.UserCredentials;
import com.uimirror.core.user.UserDetails;
import com.uimirror.core.user.UserInfo;

/**
 * A Default User representing the UIM System 
 * @author Jay
 */
public class DefaultUser implements User{

	private UserInfo userInfo;
	private UserCredentials userCredentials;
	private UserDetails userDetails;
	private UserAccountLogs userAccountLogs;

	public DefaultUser(UserInfo userInfo, UserCredentials userCredentials, UserDetails userDetails, UserAccountLogs userAccountLogs) {
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
	public User updateInfo(UserInfo userInfo){
		return new DefaultUser(userInfo, this.userCredentials, this.userDetails, this.userAccountLogs);
	}
	
	/**
	 * Updates the User Credentials
	 * @param userInfo
	 * @return
	 */
	public User updateCredentials(UserCredentials userCredentials){
		return new DefaultUser(this.userInfo, userCredentials, this.userDetails, this.userAccountLogs);
	}
	
	/**
	 * Updates the User Details
	 * @param userInfo
	 * @return
	 */
	public User updateDetails(UserDetails userDetails){
		return new DefaultUser(this.userInfo, this.userCredentials, userDetails, this.userAccountLogs);
	}
	
	/**
	 * Updates the User Details
	 * @param userInfo
	 * @return
	 */
	public User updateLogs(UserAccountLogs userAccountLogs){
		return new DefaultUser(this.userInfo, this.userCredentials, this.userDetails, userAccountLogs);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.User#getUserInfo()
	 */
	@Override
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.User#getCredentials()
	 */
	@Override
	public UserCredentials getCredentials() {
		return this.userCredentials;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.User#getBasicDetails()
	 */
	@Override
	public UserDetails getBasicDetails() {
		return this.userDetails;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.user.User#getLogs()
	 */
	@Override
	public UserAccountLogs getLogs() {
		return this.userAccountLogs;
	}

}

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
package com.uimirror.core.form;


/**
 * This will define the contract that every request should have at minimum
 * user agent and IP associated with the request.
 * 
 * @author Jay
 */
public interface DefaultHeader {

	/**
	 * This gives the client IP, tries to connect 
	 * this should present in all communication
	 * @return ip address
	 */
	String getIp();

	/**
	 * Specifies the user browser meta information, from where user tries to get connect
	 * it should present in all the communication
	 * @return user agent
	 */
	String getUserAgent();
}

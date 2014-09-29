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
package com.uimirror.core.bean;


/**
 * Stores the basic information about the client such as 
 * ip and userAgent  
 * @author Jay
 */
public class ClientMeta {

	private final String ip;
	private final String userAgent;
	
	public ClientMeta(String ip, String userAgent) {
		this.ip = ip;
		this.userAgent = userAgent;
	}
	
	public ClientMeta(){
		this.ip = null;
		this.userAgent = null;
	}

	public String getIp() {
		return ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	@Override
	public String toString() {
		return "ClientMeta [ip=" + ip + ", userAgent=" + userAgent + "]";
	}

}

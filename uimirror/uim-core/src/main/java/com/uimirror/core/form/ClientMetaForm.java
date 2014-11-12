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

import static com.uimirror.core.Constants.IP;
import static com.uimirror.core.Constants.USER_AGENT;

import java.io.Serializable;

import javax.ws.rs.HeaderParam;

/**
 * Stores the basic information about the client such as 
 * ip and userAgent  
 * 
 * @author Jay
 */
public class ClientMetaForm implements Serializable, DefaultHeader{

	private static final long serialVersionUID = -8154326668744495565L;
	
	@HeaderParam(IP)
	private String ip;
	@HeaderParam(USER_AGENT)
	private String userAgent;

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

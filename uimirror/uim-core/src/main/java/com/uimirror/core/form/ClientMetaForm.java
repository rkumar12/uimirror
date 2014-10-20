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

import java.io.Serializable;

import javax.ws.rs.HeaderParam;

import com.uimirror.core.auth.AuthConstants;

/**
 * Stores the basic information about the client such as 
 * ip and userAgent  
 * 
 * @author Jay
 */
public class ClientMetaForm implements Serializable, DefaultHeader{

	private static final long serialVersionUID = -8154326668744495565L;
	
	@HeaderParam(AuthConstants.IP)
	private String ip;
	@HeaderParam(AuthConstants.USER_AGENT)
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

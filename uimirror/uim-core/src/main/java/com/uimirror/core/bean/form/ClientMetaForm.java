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
package com.uimirror.core.bean.form;

import javax.ws.rs.HeaderParam;

import com.uimirror.core.auth.AuthParamExtractor;

/**
 * Stores the basic information about the client such as 
 * ip and userAgent  
 * @author Jay
 */
public class ClientMetaForm {

	@HeaderParam(AuthParamExtractor.IP)
	private String ip;
	@HeaderParam(AuthParamExtractor.USER_AGENT)
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

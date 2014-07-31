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

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.uimirror.ws.api.security.ouath.UIMPrincipal;

/**
 * @author Jay
 *
 */
public class AccessToken implements Serializable, UIMPrincipal{

	private String token;
	private ZonedDateTime grantedOn;
	private ZonedDateTime expiresOn;
	private Scope scope;
	private Client client;
	//private User user;
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientId() {
		// TODO Auto-generated method stub
		return null;
	}

}

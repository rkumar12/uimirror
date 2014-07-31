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
package com.uimirror.ws.api.security.service;

import com.uimirror.ws.api.security.bean.base.AccessToken;


/**
 * @author Jayaram
 *
 */
public class UimClientSecurityServiceImpl implements ClientSecurityService {

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.ClientSecurityService#getClientSession(java.lang.String)
	 */
	@Override
	public AccessToken getClientSession(String apiKey) {
		//TODO implemnt the mongo and redis part by tommorow
		//TODO populate the client details
//		UimClientDetails details = new UimClientDetails(12, "Test", "http://abc.com");
//		Set<Role> roles = new HashSet<Role>();
//		roles.add(Role.ADMIN);
//		UimClient client = new UimClient(apiKey, details , 123l, roles, Boolean.TRUE);
//		UimClientSession uimSession = new UimClientSession(client, null);
//		return uimSession;
		return null;
	}

}

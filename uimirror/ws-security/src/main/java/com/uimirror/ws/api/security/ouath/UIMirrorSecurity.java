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
package com.uimirror.ws.api.security.ouath;

import java.security.Principal;

import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.uimirror.ws.api.security.bean.base.AccessToken;

/**
 * @author Jayaram
 *
 */
public class UIMirrorSecurity implements UIMSecurityContext{

	protected static final Logger LOG = LoggerFactory.getLogger(UIMirrorSecurity.class);
	private final AccessToken token;
	private final UriInfo uriInfo;
	
	
	/**
	 * @param session
	 */
	public UIMirrorSecurity(AccessToken token, UriInfo uriInfo) {
		super();
		Assert.notNull(token, "Access Token can't be Empty, user/client should have authenticated eariller.");
		System.out.println(uriInfo);
		this.token = token;
		this.uriInfo = uriInfo;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#getUserPrincipal()
	 */
	@Override
	public Principal getUserPrincipal() {
		return token;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#isUserInRole(java.lang.String)
	 */
	@Override
	public boolean isUserInRole(String role) {
		//TODO this implemntation is pending
		return Boolean.TRUE;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#isSecure()
	 */
	@Override
	public boolean isSecure() {
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#getAuthenticationScheme()
	 */
	@Override
	public String getAuthenticationScheme() {
		return UIMSecurityContext.BEARER;
	}

	@Override
	public boolean isUserHasLicense(String license) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public License getUserLicense() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

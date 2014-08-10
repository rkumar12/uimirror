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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author Jayaram
 *
 */
public class UIMirrorSecurity implements UIMSecurityContext{

	protected static final Logger LOG = LoggerFactory.getLogger(UIMirrorSecurity.class);
	private final Principal preAuthToken;
	private final UIMPrincipal ouathToken;
	
	
	/**
	 * <p>When Client is pre authenticated populate this</p>
	 * @param token
	 */
	public UIMirrorSecurity(Principal token) {
		super();
		Assert.notNull(token, "Principal can't be Empty, user/client should have authenticated eariller.");
		this.preAuthToken = token;
		this.ouathToken = null;
	}

	/**
	 * <p>When Client will be authenticated and authorized populate this</p>
	 * @param preOuathToken
	 * @param accessToken
	 */
	public UIMirrorSecurity(Principal preOuathToken, UIMPrincipal accessToken) {
		this.ouathToken = accessToken;
		this.preAuthToken = preOuathToken;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#getUserPrincipal()
	 */
	@Override
	public Principal getUserPrincipal() {
		return ouathToken != null ? ouathToken : preAuthToken;
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

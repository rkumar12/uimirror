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
package com.uimirror.core.auth.bean;

import org.springframework.util.Assert;

import com.uimirror.core.bean.ClientMeta;

/**
 * Specifies the common attributes that are used for all type of {@link Authentication}
 * @author Jay
 */
public abstract class CommonAuthentication extends ClientMeta implements Authentication{

	private static final long serialVersionUID = -9035737880003059815L;
	
	private final CredentialType cType;
	
	public CommonAuthentication(CredentialType type, String ip, String uAgent ) {
		super(ip, uAgent);
		Assert.notNull(type, "Principal type can't be null");
		this.cType = type;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.CommonAuthentication#getCredentialType()
	 */
	@Override
	public CredentialType getCredentialType() {
		return this.cType;
	}


	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getAuthenticationScheme()
	 */
	@Override
	public Object getAuthenticationScheme() {
		return Authentication.BEARER;
	}


}

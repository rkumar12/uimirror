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
package com.uimirror.core.auth.bean.form;

import javax.ws.rs.HeaderParam;
import com.uimirror.core.auth.bean.CredentialType;

/**
 * Converts the {@link HeaderParam} provided in the request for the
 * authentication purpose.
 * 
 * @author Jay
 */
public abstract class AccessKeyHeaderAuthenticationForm extends HeaderAuthenticationForm{

	private static final long serialVersionUID = -1215523730014366150L;

	public String getUserId() {
		return null;
	}

	public String getPassword() {
		return null;
	}

	public String getKeepMeLogedIn() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.BasicAuthenticationForm#getRedirectUri()
	 */
	@Override
	public String getRedirectUri() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.BasicAuthenticationForm#getScope()
	 */
	@Override
	public String getScope() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.BasicAuthenticationForm#getClientId()
	 */
	@Override
	public String getClientId() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.BasicAuthenticationForm#getClientSecret()
	 */
	@Override
	public String getClientSecret() {
		return null;
	}
	
	//######Below are the common authentication details########//
	
	public CredentialType getCredentialType() {
		return CredentialType.ACCESSKEY;
	}
	
	public String getIp() {
		return super.getIp();
	}

	public String getUserAgent() {
		return super.getUserAgent();
	}
	
}

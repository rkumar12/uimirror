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

import java.io.Serializable;
import java.security.Principal;

/**
 * @author Jay
 */
public class PreAuthenticatedAuthenticationToken implements Principal, Serializable {

	private static final long serialVersionUID = -3752070756750159791L;

	private final String oPrincipal;
	public PreAuthenticatedAuthenticationToken(String principal) {
		this.oPrincipal = principal;
	}

	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return this.oPrincipal;
	}

}

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
package com.uimirror.core.auth.context;

import java.io.Serializable;

import com.uimirror.core.auth.Authentication;

/**
 * @author Jay
 */
public interface SecurityContext extends Serializable {

	/**
     * Obtains the currently authenticated principal, or an authentication request token.
     *
     * @return the <code>Authentication</code> or <code>null</code> if no authentication information is available
     */
	Authentication getAuthentication();
	
    /**
     * Changes the currently authenticated principal, or removes the authentication information.
     *
     * @param authentication the new <code>Authentication</code> token, or <code>null</code> if no further
     *        authentication information should be stored
     */
	void setAuthentication();
}

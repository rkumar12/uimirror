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
package com.uimirror.auth;

import com.uimirror.core.auth.bean.AuthenticatedDetails;
import com.uimirror.core.auth.bean.Authentication;

/**
 * This helps to generate the authentication token 
 * refresh period calcluation.
 * @author Jay
 */
public interface AuthRefreshPeriodProvider {

	/**
	 * Common Interface for the Authentication provider
	 * which needs to calculate the refresh period for the authentication
	 * @param auth
	 * @param details
	 * @return
	 */
	public int decideRefreshPeriod(Authentication auth, AuthenticatedDetails details);
}

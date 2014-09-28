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
package com.uimirror.core.auth;

import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.Authentication;


/**
 * Processes an {@link Authentication} request.
 * 
 * @author Jay
 */
public interface AuthenticationManager {

	/**
     * Attempts to authenticate the passed {@link Authentication} object, returning a fully populated
     * <code>AccessToken</code> object (including granted authorities) if successful.
     * <p>
     * An <code>AuthenticationManager</code> must honour the following contract concerning exceptions:
     * <ul>
     * <li>A {@link DisabledException} must be thrown if an account is disabled and the
     * <code>AuthenticationManager</code> can test for this state.</li>
     * <li>A {@link LockedException} must be thrown if an account is locked and the
     * <code>AuthenticationManager</code> can test for account locking.</li>
     * <li>A {@link BadCredentialsException} must be thrown if incorrect credentials are presented. Whilst the
     * above exceptions are optional, an <code>AuthenticationManager</code> must <B>always</B> test credentials.</li>
     * </ul>
     * Exceptions should be tested for and if applicable thrown in the order expressed above (i.e. if an
     * account is disabled or locked, the authentication request is immediately rejected and the credentials testing
     * process is not performed). This prevents credentials being tested against  disabled or locked accounts.
     * 
     * After successful  authentication, check if user has 2 factor authentication enabled,
     * if so, populate a interim {@link AccessToken} and send back to the user for 2FA screen. 
     *
     * @param authentication the authentication request object
     *
     * @return a fully authenticated object excluding credentials
     *
     * @throws AuthenticationException if authentication fails
     */
	AccessToken authenticate(Authentication authentication) throws AuthenticationException;
}

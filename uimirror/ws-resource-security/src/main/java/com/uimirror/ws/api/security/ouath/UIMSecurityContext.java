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

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * An injectable interface that provides access to security related
 * information, it extends the feature of {@link SecurityContext}.
 * 
 * @author Jay
 * @see Context
 * @since 1.0
 */
public interface UIMSecurityContext extends SecurityContext {
	
	/**
	 * <p>String identifier for the Ouath2 authorization header.
	 */
	public static final String BEARER = "Bearer";
	
	/**
	 * The access token issued by the authorization server. This value is REQUIRED.
	 */
	public static String ACCESS_TOKEN = "access_token";
	
	/**
	 * Returns a boolean indicating whether the authenticated user is included
     * in the specified logical "license". If the user has not been authenticated,
     * the method returns <code>false</code>.
     *
     * @param license a <code>String</code> specifying the name of the license
     * @return a <code>boolean</code> indicating whether the user making
     *         the request belongs to a assigned license; <code>false</code> if the user
     *         has not been authenticated or don't have license
     * @throws java.lang.IllegalStateException
     *          if called outside the scope of a request
	 */
	public boolean isUserHasLicense(String license);
	
	/**
	 * Returns a boolean indicating whether the authenticated client is included
     * in the specified logical "license". If the client has not been authenticated,
     * the method returns <code>false</code>.
     *
     * @param license a <code>String</code> specifying the name of the license
     * @return a <code>boolean</code> indicating whether the user making
     *         the request belongs to a assigned license; <code>false</code> if the client
     *         has not been authenticated or don't have license
     * @throws java.lang.IllegalStateException
     *          if called outside the scope of a request
	 */
	public boolean isClientHasLicense(String license);
	
	/**
     * Returns a <code>com.uimirror.ws.api.security.ouath.License</code> object containing the
     * license of the current authenticated user. If the user
     * has not been authenticated, the method returns null.
     *
     * @return a <code>com.uimirror.ws.api.security.ouath.License</code> containing the license
     *         of the user making this request; null if the user has not been
     *         authenticated
     * @throws java.lang.IllegalStateException
     *          if called outside the scope of a request
     */
	public License getUserLicense();
	
	/**
     * Returns a <code>com.uimirror.ws.api.security.ouath.License</code> object containing the
     * license of the current authenticated client. If the client
     * has not been authenticated, the method returns null.
     *
     * @return a <code>com.uimirror.ws.api.security.ouath.License</code> containing the license
     *         of the user making this request; null if the client has not been
     *         authenticated
     * @throws java.lang.IllegalStateException
     *          if called outside the scope of a request
     */
	public License getClientLicense();
	
	

}

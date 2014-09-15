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

import java.io.Serializable;
import java.security.Principal;

/**
 * <p>This will be the common principal contract
 * every uimirror application are supposed to follow the mail rules from this</p>
 * This holds the user authentication details prior to the authentication,
 * once user has been authenticated he will be granted with the {@link AccessToken}
 * @author Jay
 */
public interface Authentication extends Principal, Serializable {
	
	public static final String BEARER = "Bearer";
	
	/**
	 * The credential Type that suggest the authentication is of which type like.
	 * This usually a enum containing one of type {@link CredentialType#LOGINFORM} etc
	 *  
	 * @return
	 */
	public CredentialType getCredentialType();
	
	/**
     * The credentials that prove the principal is correct. This is usually a password, but could be anything
     * relevant to the <code>AuthenticationManager</code>. Callers are expected to populate the credentials.
     *
     * @return the credentials that prove the identity of the <code>Principal</code>
     */
    Object getCredentials();

    /**
     * Stores additional details about the authentication request. These might be an IP address, certificate
     * serial number etc.
     *
     * @return additional details about the authentication request, or <code>null</code> if not used
     */
    Object getDetails();
    
    /**
     * <p>This specifies the authentication schema getting supported</p>
     * @return
     */
    public Object getAuthenticationScheme();

}

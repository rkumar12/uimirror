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
 * This holds the principal details after user has been logged in
 * 
 * @author Jay
 */
public interface AccessToken extends Principal, Serializable{

	/**
	 * Specifies the type of token
	 * {@link TokenType#ACCESS} if it is a access token,
	 * {@link TokenType#SECRET} if it is a token issued to the client
	 * {@link TokenType#TEMPORAL} if this token is issued for temproal purpose only
	 *  
	 * @return
	 */
	TokenType getTokenType();
	
	/**
	 * Represents the issued token and its paraphrase
	 * @return
	 */
	Token getToken();
	
	/**
	 * Specifies the time in mills, the token will expire on
	 * @return
	 */
	String getExpiresOn();
	
	/**
	 * Defines the scope for this token
	 * @see AccessTokenScope
	 * @return
	 * 
	 */
	AccessTokenScope getScope();

}

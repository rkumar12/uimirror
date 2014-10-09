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
import java.util.Map;

/**
 * This holds the principal details after user has been logged in
 * 
 * @author Jay
 */
public interface AccessToken extends Principal, Serializable{

	String ERR_MSG = "Not a valid AccessToken";
	
	/**
	 * Specifies the type of token
	 * {@link TokenType#ACCESS} if it is a access token,
	 * {@link TokenType#SECRET} if it is a token issued to the client
	 * {@link TokenType#TEMPORAL} if this token is issued for temproal purpose only
	 * {@linkplain TokenType#_2FA} if this token is issued where user wants to perform 2FA
	 *  
	 * @return
	 */
	TokenType getType();
	
	/**
	 * Represents the issued token and its paraphrase
	 * @return
	 */
	String getToken();
	
	/**
	 * Specifies the time in mills, the token will expire on
	 * @return
	 */
	long getExpire();
	
	/**
	 * Defines the scope for this token
	 * @return
	 * 
	 */
	String getScope();
	
	/**
	 * Specifies the owner for this token
	 * @return
	 */
	String getOwner();
	
	/**
	 * Specifies the client from which request generated
	 * @return
	 */
	String getClient();
	
	/**
	 * If this token as some additional Notes
	 * @return
	 */
	Map<String, Object> getNotes();
	
	/**
	 * If this token has any instructions that needs to be taken care
	 * @return
	 */
	Map<String, Object> getInstructions();
	
	/**
	 * Converts to the response Map, which in-terms will be transformed to the json
	 * Necessary to remove unnecessary info while serializing such as it might have user agent
	 * host name, extra authentication parameters etc
	 * @return
	 */
	Map<String, Object> toResponseMap();
	
	/**
	 * Should remove the un-necessary info from the token details
	 * @return
	 */
	AccessToken eraseEsential();

}

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



/**
 * Initialize a token for the given scope and token type
 * @author Jay
 */
public class DefaultToken extends BasicAccessToken{

	private static final long serialVersionUID = -1202938196660387547L;

	public DefaultToken(String id, String expireOn, TokenType type, Scope scope) {
		super(id, expireOn, type, scope);
	}
	
	public DefaultToken(Token token, String id, String expireOn, TokenType type, Scope scope) {
		super(token, id, expireOn, type, scope);
	}

}

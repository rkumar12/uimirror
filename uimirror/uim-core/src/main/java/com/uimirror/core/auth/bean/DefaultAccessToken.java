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
 * Default token of type {@link TokenType#ACCESS}
 * 
 * @author Jay
 */
public class DefaultAccessToken extends DefaultToken{

	private static final long serialVersionUID = -9008753259161873541L;

	public DefaultAccessToken(Token token, String id, String expireOn, Scope scope) {
		super(token, id, expireOn, TokenType.ACCESS, scope);
	}
	
	public DefaultAccessToken(String id, String expireOn, Scope scope) {
		super(id, expireOn, TokenType.ACCESS, scope);
	}

}

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

import com.uimirror.core.auth.AccessTokenScope;
import com.uimirror.core.auth.BasicAccessToken;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;

/**
 * Default token of type {@link TokenType#ACCESS}
 * 
 * @author Jay
 */
public class DefaultAccessToken extends BasicAccessToken{

	private static final long serialVersionUID = -9008753259161873541L;

	public DefaultAccessToken(Token token, String id, String expireOn, AccessTokenScope scope) {
		super(token, id, expireOn, TokenType.ACCESS, scope);
	}
	
	public DefaultAccessToken(String id, String expireOn, AccessTokenScope scope) {
		super(id, expireOn, TokenType.ACCESS, scope);
	}

}

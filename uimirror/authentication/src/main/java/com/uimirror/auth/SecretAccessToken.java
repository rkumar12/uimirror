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
 * An AccessToken which is of type {@link TokenType#SECRET}
 * 
 * @author Jay
 */
public class SecretAccessToken extends BasicAccessToken{

	private static final long serialVersionUID = -4416386144348399818L;

	public SecretAccessToken(Token token, String id, String expireOn, AccessTokenScope scope) {
		super(token, id, expireOn, TokenType.SECRET, scope);
	}
	
	public SecretAccessToken(String id, String expireOn, AccessTokenScope scope) {
		super(id, expireOn, TokenType.SECRET, scope);
	}

}

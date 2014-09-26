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
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;

/**
 * An AccessToken which is of type {@link TokenType#ACCESS}
 * A default scope will be applied with type {@link AccessTokenScope#WRITE}
 * @author Jay
 */
public class WriteAccessToken extends DefaultAccessToken{

	private static final long serialVersionUID = -4758380772701102332L;

	public WriteAccessToken(Token token, String id, String expireOn) {
		super(token, id, expireOn, AccessTokenScope.WRITE);
	}
	
	public WriteAccessToken(String id, String expireOn) {
		super(id, expireOn, AccessTokenScope.WRITE);
	}

}

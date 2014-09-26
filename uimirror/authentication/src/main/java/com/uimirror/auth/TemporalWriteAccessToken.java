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
 * Default token of type {@link TokenType#TEMPORAL}
 * And scope of this token will be {@link AccessTokenScope#WRITE}
 * 
 * @author Jay
 */
public class TemporalWriteAccessToken extends TemporalAccessToken{

	private static final long serialVersionUID = 7692305352923925713L;

	public TemporalWriteAccessToken(Token token, String id, String expireOn) {
		super(token, id, expireOn, AccessTokenScope.WRITE);
	}
	
	public TemporalWriteAccessToken(String id, String expireOn) {
		super(id, expireOn, AccessTokenScope.WRITE);
	}

}

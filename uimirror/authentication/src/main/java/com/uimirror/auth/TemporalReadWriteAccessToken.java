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
 * And scope of this token will be {@link AccessTokenScope#READWRITE}
 * 
 * @author Jay
 */
public class TemporalReadWriteAccessToken extends TemporalAccessToken{

	private static final long serialVersionUID = 239159776426603622L;

	public TemporalReadWriteAccessToken(Token token, String id, String expireOn) {
		super(token, id, expireOn, AccessTokenScope.READWRITE);
	}
	
	public TemporalReadWriteAccessToken(String id, String expireOn) {
		super(id, expireOn, AccessTokenScope.READWRITE);
	}

}

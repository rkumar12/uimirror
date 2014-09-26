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
 * Default token of type {@link TokenType#TEMPORAL}
 * @author Jay
 */
public class TemporalAccessToken extends BasicAccessToken{

	private static final long serialVersionUID = 3678715401846447631L;

	public TemporalAccessToken(Token token, String id, String expireOn, AccessTokenScope scope) {
		super(token, id, expireOn, TokenType.TEMPORAL, scope);
	}
	
	public TemporalAccessToken(String id, String expireOn, AccessTokenScope scope) {
		super(id, expireOn, TokenType.TEMPORAL, scope);
	}

}

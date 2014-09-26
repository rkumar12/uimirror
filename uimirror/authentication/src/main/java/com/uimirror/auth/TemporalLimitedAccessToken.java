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
 * And scope of this token will be {@link AccessTokenScope#LIMITED}
 * 
 * @author Jay
 */
public class TemporalLimitedAccessToken extends TemporalAccessToken{

	private static final long serialVersionUID = -8523472631408440445L;

	public TemporalLimitedAccessToken(Token token, String id, String expireOn) {
		super(token, id, expireOn, AccessTokenScope.LIMITED);
	}
	
	public TemporalLimitedAccessToken(String id, String expireOn) {
		super(id, expireOn, AccessTokenScope.LIMITED);
	}

}

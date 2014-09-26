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
 * An AccessToken which is of type {@link TokenType#SECRET}
 * A default scope will be applied with type {@link AccessTokenScope#LIMITED}
 * 
 * @author Jay
 */
public final class SecretLimitedAccessToken extends SecretAccessToken{

	private static final long serialVersionUID = -689702056614058499L;

	public SecretLimitedAccessToken(String id, String expireOn) {
		super(id, expireOn, AccessTokenScope.LIMITED);
	}
	
	public SecretLimitedAccessToken(Token token, String id, String expireOn) {
		super(token, id, expireOn, AccessTokenScope.LIMITED);
	}

}

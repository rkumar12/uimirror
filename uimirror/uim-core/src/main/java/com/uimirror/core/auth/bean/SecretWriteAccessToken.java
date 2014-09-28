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
 * An AccessToken which is of type {@link TokenType#SECRET}
 * A default scope will be applied with type {@link Scope#WRITE}
 * @author Jay
 */
public final class SecretWriteAccessToken extends SecretAccessToken{

	private static final long serialVersionUID = 3957196987440745985L;

	public SecretWriteAccessToken(String id, String expireOn) {
		super(id, expireOn, Scope.WRITE);
	}
	
	public SecretWriteAccessToken(Token token, String id, String expireOn) {
		super(token, id, expireOn, Scope.WRITE);
	}

}

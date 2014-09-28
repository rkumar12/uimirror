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
 * A default scope will be applied with type {@link Scope#READWRITE}
 * 
 * @author Jay
 */
public final class SecretReadWriteAccessToken extends SecretAccessToken{

	private static final long serialVersionUID = -1974004224736169601L;

	public SecretReadWriteAccessToken(String id, String expireOn) {
		super(id, expireOn, Scope.READWRITE);
	}
	
	public SecretReadWriteAccessToken(Token token, String id, String expireOn) {
		super(token, id, expireOn, Scope.READWRITE);
	}

}

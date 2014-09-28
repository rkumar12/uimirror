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
 * 
 * @author Jay
 */
public class SecretAccessToken extends DefaultToken{

	private static final long serialVersionUID = -4416386144348399818L;

	public SecretAccessToken(Token token, String id, String expireOn, Scope scope) {
		super(token, id, expireOn, TokenType.SECRET, scope);
	}
	
	public SecretAccessToken(String id, String expireOn, Scope scope) {
		super(id, expireOn, TokenType.SECRET, scope);
	}

}

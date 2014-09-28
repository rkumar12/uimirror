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
 * Default token of type {@link TokenType#TEMPORAL}
 * @author Jay
 */
public class TemporalAccessToken extends DefaultToken{

	private static final long serialVersionUID = 3678715401846447631L;

	public TemporalAccessToken(Token token, String id, String expireOn, Scope scope) {
		super(token, id, expireOn, TokenType.TEMPORAL, scope);
	}
	
	public TemporalAccessToken(String id, String expireOn, Scope scope) {
		super(id, expireOn, TokenType.TEMPORAL, scope);
	}

}

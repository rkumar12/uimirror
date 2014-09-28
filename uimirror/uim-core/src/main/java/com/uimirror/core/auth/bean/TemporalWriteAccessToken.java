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
 * And scope of this token will be {@link Scope#WRITE}
 * 
 * @author Jay
 */
public class TemporalWriteAccessToken extends TemporalAccessToken{

	private static final long serialVersionUID = 7692305352923925713L;

	public TemporalWriteAccessToken(Token token, String id, String expireOn) {
		super(token, id, expireOn, Scope.WRITE);
	}
	
	public TemporalWriteAccessToken(String id, String expireOn) {
		super(id, expireOn, Scope.WRITE);
	}

}

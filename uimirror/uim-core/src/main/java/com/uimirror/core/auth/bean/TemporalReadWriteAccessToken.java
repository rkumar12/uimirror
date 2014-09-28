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
 * And scope of this token will be {@link Scope#READWRITE}
 * 
 * @author Jay
 */
public class TemporalReadWriteAccessToken extends TemporalAccessToken{

	private static final long serialVersionUID = 239159776426603622L;

	public TemporalReadWriteAccessToken(Token token, String id, String expireOn) {
		super(token, id, expireOn, Scope.READWRITE);
	}
	
	public TemporalReadWriteAccessToken(String id, String expireOn) {
		super(id, expireOn, Scope.READWRITE);
	}

}

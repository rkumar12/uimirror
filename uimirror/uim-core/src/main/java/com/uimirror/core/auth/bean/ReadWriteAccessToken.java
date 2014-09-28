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
 * An AccessToken which is of type {@link TokenType#ACCESS}
 * A default scope will be applied with type {@link Scope#READWRITE}
 * @author Jay
 */
public class ReadWriteAccessToken extends DefaultAccessToken{

	private static final long serialVersionUID = 7646758164630080924L;

	public ReadWriteAccessToken(Token token, String id, String expireOn) {
		super(token, id, expireOn, Scope.READWRITE);
	}
	
	public ReadWriteAccessToken(String id, String expireOn) {
		super(id, expireOn, Scope.READWRITE);
	}

}

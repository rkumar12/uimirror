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
package com.uimirror.auth.bean;

import java.util.Map;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;

/**
 * An {@linkplain AccessToken} issued to the user
 * @author Jay
 */
public class DefaultAccessToken extends AbstractAccessToken{

	private static final long serialVersionUID = -6156839027050013727L;
	
	/**
	 * @param map
	 */
	public DefaultAccessToken(Map<String, Object> map) {
		super(map);
	}
	//TODO think about it again for erase credentials
	private DefaultAccessToken(Token token){
		super(token);
	}
	
	/**
	 *Don't Use this until has some special use case
	 * @param token
	 */
	public DefaultAccessToken() {
		super();
	}

	/**
	 * @param token
	 * @param owner
	 * @param client
	 * @param expire
	 * @param type
	 * @param scope
	 * @param notes
	 * @param instructions
	 */
	public DefaultAccessToken(Token token, String owner, String client, long expire, TokenType type, Scope scope, Map<String, Object> notes, Map<String, Object> instructions) {
		super(token, owner, client, expire, type, scope, notes, instructions);
	}

	/**
	 * @param token
	 * @param owner
	 * @param client
	 * @param expire
	 * @param type
	 * @param scope
	 */
	public DefaultAccessToken(Token token, String owner, String client, long expire, TokenType type, Scope scope) {
		super(token, owner, client, expire, type, scope);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessToken#eraseEsential()
	 */
	@Override
	public AccessToken eraseEsential() {
		return new DefaultAccessToken(this.getToken());
	}

}

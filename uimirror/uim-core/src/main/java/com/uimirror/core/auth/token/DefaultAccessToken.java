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
package com.uimirror.core.auth.token;

import java.util.Map;

import org.springframework.util.StringUtils;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;

/**
 * An {@linkplain AccessToken} issued to the user
 * @author Jay
 */
public class DefaultAccessToken extends AbstractAccessToken<DefaultAccessToken>{

	private static final long serialVersionUID = -6156839027050013727L;
	
	/**
	 * @param map from which state will be presumed
	 */
	public DefaultAccessToken(Map<String, Object> map) {
		super(map);
	}
	
	/**
	 *Don't Use this until has some special use case
	 */
	public DefaultAccessToken() {
		super();
	}

	/**
	 * @param token issued token
	 * @param owner owner of this token
	 * @param client client to whom its granted
	 * @param expire time interval of token expiry
	 * @param type token type
	 * @param scope scope of this token
	 * @param notes if any notes associated with this
	 * @param instructions if any insrutctions associated with this
	 */
	public DefaultAccessToken(Token token, String owner, String client, long expire, TokenType type, Scope scope, Map<String, Object> notes, Map<String, Object> instructions) {
		super(token, owner, client, expire, type, scope, notes, instructions);
	}

	/**
	 * @param token issued token
	 * @param owner owner of this token
	 * @param client client to whom its granted
	 * @param expire time interval of token expiry
	 * @param type token type
	 * @param scope scope of this token
	 */
	public DefaultAccessToken(Token token, String owner, String client, long expire, TokenType type, Scope scope) {
		super(token, owner, client, expire, type, scope);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessToken#eraseEsential()
	 */
	@Override
	public AccessToken eraseEsential() {
		return new DefaultAccessToken(this.getToken().getEncrypted(), this.getOwner(), this.getClient(), this.getExpire(), this.getType(), this.getScope());
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.AbstractAccessToken#init(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected DefaultAccessToken init(Map<String, Object> src) {
		String tokenId = (String)src.get(AccessTokenFields.ID);
		String pharse = (String)src.get(AccessTokenFields.ENCRYPT_STARTEGY);
		Token token = new Token(tokenId, pharse);
		String tokenType = (String)src.get(AccessTokenFields.TYPE);
		TokenType type = StringUtils.hasText(tokenType)? TokenType.getEnum(tokenType) : null;
		String owner = (String)src.get(AccessTokenFields.AUTH_TKN_OWNER);
		String client = (String)src.get(AccessTokenFields.AUTH_TKN_CLIENT);
		long expire = (long)src.getOrDefault(AccessTokenFields.AUTH_TKN_EXPIRES, 0l);
		String tokenScope = (String)src.get(AccessTokenFields.SCOPE);
		Scope scope = StringUtils.hasText(tokenScope) ? Scope.getEnum(tokenScope):null;
		Map<String, Object> instructions = (Map<String, Object>)src.get(AccessTokenFields.AUTH_TKN_INSTRUCTIONS);
		Map<String, Object> notes = (Map<String, Object>)src.get(AccessTokenFields.AUTH_TKN_NOTES);
		return new DefaultAccessToken(token, owner, client, expire, type, scope, notes, instructions);
	}

}

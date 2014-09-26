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
package com.uimirror.core.auth;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.uimirror.core.mongo.feature.MongoDocumentSerializer;

/**
 * A basic implementation of the accesstoken
 * @author Jay
 */
public class BasicAccessToken extends MongoDocumentSerializer implements AccessToken{

	private static final long serialVersionUID = 1758356201287067187L;
	private final Token token;
	private final String id;
	private final String expireOn;
	private final TokenType type;
	private final AccessTokenScope scope;

	/**
	 * Make sure, if trying to create the object, then initialize from the map
	 * else any operation on this will give exceptions
	 */
	public BasicAccessToken(){
		this.token = null;
		this.id = null;
		this.expireOn = null;
		this.type = null;
		this.scope = null;
	}
	
	public BasicAccessToken(Token token, String id, String expireOn, TokenType type, AccessTokenScope scope) {
		super();
		this.token = token;
		this.id = id;
		this.expireOn = expireOn;
		this.type = type;
		this.scope = scope;
	}
	
	public BasicAccessToken(String id, String expireOn, TokenType type, AccessTokenScope scope) {
		super();
		this.token = AccessTokenGenerator.getNewOne();
		this.id = id;
		this.expireOn = expireOn;
		this.type = type;
		this.scope = scope;
	}

	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessToken#getTokenType()
	 */
	@Override
	public TokenType getTokenType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessToken#getToken()
	 */
	@Override
	public Token getToken() {
		return this.token;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessToken#getExpiresOn()
	 */
	@Override
	public String getExpiresOn() {
		return this.expireOn;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessToken#getScope()
	 */
	@Override
	public AccessTokenScope getScope() {
		return this.scope;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public AccessToken initFromMap(Map<String, Object> src) {
		String token = (String)src.get(AccessTokenFields.ID);
		String pharse = (String)src.get(AccessTokenFields.ENCRYPT_STARTEGY);
		String id = (String)src.get(AccessTokenFields.IDENTIFIER);
		String timeout = (String)src.get(AccessTokenFields.EXPIRE_ON);
		String type = (String)src.get(AccessTokenFields.TYPE);
		String scope = (String)src.get(AccessTokenFields.SCOPE);
		validate(token, id, timeout, type, scope);
		return new BasicAccessToken(new Token(token, pharse), id, timeout, TokenType.getEnum(type), AccessTokenScope.getEnum(scope));
		
	}
	
	/**
	 * This map the document should have which fields
	 * always it will have _id, parapharse, id, expire, type and scope
	 */
	@Override
	public Map<String, Object> toMap(){
		Map<String, Object> map = new LinkedHashMap<String, Object>(10);
		map.put(AccessTokenFields.ID, this.token.getToken());
		map.put(AccessTokenFields.ENCRYPT_STARTEGY, this.token.getParaphrase());
		map.put(AccessTokenFields.IDENTIFIER, this.id);
		map.put(AccessTokenFields.EXPIRE_ON, this.expireOn);
		map.put(AccessTokenFields.TYPE, this.type.getTokenType());
		map.put(AccessTokenFields.SCOPE, this.scope.getScope());
		return map;
	}

	/**
	 * Validates the map objects to make sure map has proper details to populate the object
	 * @param token
	 * @param id
	 * @param timeout
	 * @param type
	 * @param scope
	 */
	private void validate(String token, String id, String timeout, String type, String scope){
		Assert.hasText(token, ERR_MSG);
		Assert.hasText(id, ERR_MSG);
		Assert.hasText(timeout, ERR_MSG);
		Assert.hasText(type, ERR_MSG);
		Assert.hasText(scope, ERR_MSG);
	}

}

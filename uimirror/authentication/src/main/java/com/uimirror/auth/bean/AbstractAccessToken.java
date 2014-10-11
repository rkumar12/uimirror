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

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.auth.core.AccessTokenFields;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.mongo.feature.BeanBasedDocument;

/**
 * A basic implementation of the accesstoken
 * @author Jay
 */
public abstract class AbstractAccessToken extends BeanBasedDocument implements AccessToken{

	private static final long serialVersionUID = 1758356201287067187L;
	private Token token;
	private String owner;
	private String client;
	private long expire;
	private TokenType type;
	private Scope scope;
	private Map<String, Object> notes;
	private Map<String, Object> instructions;
	
	protected AbstractAccessToken(Token token){
		this.token = token;
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
	public AbstractAccessToken(Token token, String owner, String client, long expire, TokenType type, Scope scope, Map<String, Object> notes, Map<String, Object> instructions) {
		super();
		initialize(token, owner, client, expire, type, scope, notes, instructions);
	}

	/**
	 * @param token
	 * @param owner
	 * @param client
	 * @param expire
	 * @param type
	 * @param scope
	 */
	public AbstractAccessToken(Token token, String owner, String client, long expire, TokenType type, Scope scope) {
		super();
		initialize(token, owner, client, expire, type, scope, null, null);
	}
	
	/**
	 * This will initialize the object state from the map
	 * @param map
	 */
	public AbstractAccessToken(Map<String, Object> map){
		initFromMap(map);
	}
	
	private void initialize(Token token, String owner, String client, long expire, TokenType type, Scope scope, Map<String, Object> notes, Map<String, Object> instructions){
		this.token = token;
		this.owner = owner;
		this.client = client;
		this.expire = expire;
		this.type = type;
		this.scope = scope;
		this.notes = notes == null ? new LinkedHashMap<String, Object>() : notes;
		this.instructions = instructions == null ? new LinkedHashMap<String, Object>() : instructions;
	}
	/**
	 * This will update the instructions and notes iff provided arguments are not empty
	 * @param notes
	 * @param instructions
	 * @return
	 */
	public void updateInstructions(Map<String, Object> notes, Map<String, Object> instructions){
		if(!CollectionUtils.isEmpty(notes))
			this.notes.putAll(notes);
		if(!CollectionUtils.isEmpty(instructions))
			this.instructions.putAll(instructions);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initFromMap(Map<String, Object> src) {
		String token = (String)src.get(AccessTokenFields.ID);
		String pharse = (String)src.get(AccessTokenFields.ENCRYPT_STARTEGY);
		this.token = new Token(token, pharse);
		String type = (String)src.get(AccessTokenFields.TYPE);
		this.type = StringUtils.hasText(type)? TokenType.getEnum(type) : null;
		this.owner = (String)src.get(AccessTokenFields.AUTH_TKN_OWNER);
		this.client = (String)src.get(AccessTokenFields.AUTH_TKN_CLIENT);
		this.expire = (long)src.getOrDefault(AccessTokenFields.AUTH_TKN_EXPIRES, 0l);
		String scope = (String)src.get(AccessTokenFields.SCOPE);
		this.scope = StringUtils.hasText(scope) ? Scope.getEnum(scope):null;
		this.instructions = (Map<String, Object>)src.get(AccessTokenFields.AUTH_TKN_INSTRUCTIONS);
		this.instructions = this.instructions == null ? new LinkedHashMap<String, Object>() : this.instructions ;
		this.notes = (Map<String, Object>)src.get(AccessTokenFields.AUTH_TKN_NOTES);
		this.notes = this.notes == null ? new LinkedHashMap<String, Object>() : this.notes;
	}
	
	/**
	 * This map the document should have which fields
	 * always it will have _id, parapharse, id, expire, type and scope
	 */
	@Override
	public Map<String, Object> toMap(){
		Map<String, Object> map = new LinkedHashMap<String, Object>(10);
		map.put(AccessTokenFields.ID, this.token.getToken());
		if(StringUtils.hasText(this.token.getParaphrase()))
			map.put(AccessTokenFields.ENCRYPT_STARTEGY, this.token.getParaphrase());
		map.put(AccessTokenFields.TYPE, this.type.getTokenType());
		map.put(AccessTokenFields.AUTH_TKN_OWNER, this.owner);
		map.put(AccessTokenFields.AUTH_TKN_CLIENT, this.client);
		map.put(AccessTokenFields.AUTH_TKN_EXPIRES, this.expire);
		if(!CollectionUtils.isEmpty(notes))
			map.put(AccessTokenFields.AUTH_TKN_NOTES, this.notes);
		if(!CollectionUtils.isEmpty(instructions))
			map.put(AccessTokenFields.AUTH_TKN_INSTRUCTIONS, this.instructions);
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
	protected void validate(Token token, String id, long timeout, TokenType type, Scope scope){
		Assert.notNull(token, ERR_MSG);
		validate(id, timeout, type, scope);
	}
	
	/**
	 * Validates the map objects to make sure map has proper details to populate the object
	 * @param id
	 * @param timeout
	 * @param type
	 * @param scope
	 */
	protected void validate(String id, long timeout, TokenType type, Scope scope){
		validate(id);
		Assert.notNull(type, ERR_MSG);
		Assert.notNull(scope, ERR_MSG);
		if(timeout == 0l){
			throw new IllegalArgumentException(ERR_MSG);
		}
	}
	
	/**
	 * Validates the provided string which shouldn't have any non empty text
	 * @param args
	 */
	private void validate(String ... args ){
		for(String arg : args){
			Assert.hasText(arg, ERR_MSG);
		}
	}

	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return this.token.getToken();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AccessToken#getType()
	 */
	@Override
	public TokenType getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AccessToken#getToken()
	 */
	@Override
	public Token getToken() {
		return this.token;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AccessToken#getExpire()
	 */
	@Override
	public long getExpire() {
		return this.expire;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AccessToken#getScope()
	 */
	@Override
	public Scope getScope() {
		return this.scope;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AccessToken#getOwner()
	 */
	@Override
	public String getOwner() {
		return this.owner;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AccessToken#getClient()
	 */
	@Override
	public String getClient() {
		return this.client;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AccessToken#getNotes()
	 */
	@Override
	public Map<String, Object> getNotes() {
		return this.notes;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AccessToken#getInstructions()
	 */
	@Override
	public Map<String, Object> getInstructions() {
		return this.instructions;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AccessToken#toResponseMap()
	 */
	@Override
	public Map<String, Object> toResponseMap() {
		Map<String, Object> rs = new LinkedHashMap<String, Object>(15);
		Token token = this.getToken().getEncrypted();
		rs.put(AccessTokenFields.TOKEN, token.getToken());
		if(StringUtils.hasText(token.getParaphrase()))
			rs.put(AccessTokenFields.ENCRYPT_STARTEGY, token.getParaphrase());
		return rs;
	}

}

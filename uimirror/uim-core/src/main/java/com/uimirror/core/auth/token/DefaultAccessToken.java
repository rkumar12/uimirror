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

import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_CLIENT;
import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_EXPIRES;
import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_INSTRUCTIONS;
import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_NOTES;
import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_OWNER;
import static com.uimirror.core.auth.token.AccessTokenFields.ENCRYPT_STARTEGY;
import static com.uimirror.core.auth.token.AccessTokenFields.SCOPE;
import static com.uimirror.core.auth.token.AccessTokenFields.TYPE;
import static com.uimirror.core.mongo.feature.BasicDBFields.ID;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;

/**
 * An {@linkplain AccessToken} issued to the user
 * @author Jay
 */
public final class DefaultAccessToken extends AbstractAccessToken<DefaultAccessToken>{

	private static final long serialVersionUID = -6156839027050013727L;
	
	/**
	 *Don't Use this until has some special use case
	 */
	public DefaultAccessToken() {
		//Don't Use This ever, untill required
		super();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessToken#eraseEsential()
	 */
	@Override
	public AccessToken eraseEsential() {
		return new TokenBuilder(token.getEncrypted()).
				addOwner(owner).
				addClient(client).
				addExpire(expire).
				addNotes(notes).
				addType(type).build();
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.AbstractAccessToken#init(java.util.Map)
	 */
	@Override
	protected DefaultAccessToken init(Map<String, Object> src) {
		String tokenId = (String)src.get(ID);
		String pharse = (String)src.get(ENCRYPT_STARTEGY);
		Token token = new Token(tokenId, pharse);
		String tokenType = (String)src.get(TYPE);
		TokenType type = StringUtils.hasText(tokenType)? TokenType.getEnum(tokenType) : null;
		String owner = (String)src.get(AUTH_TKN_OWNER);
		String client = (String)src.get(AUTH_TKN_CLIENT);
		long expire = (long)src.getOrDefault(AUTH_TKN_EXPIRES, 0l);
		String tokenScope = (String)src.get(SCOPE);
		Scope scope = StringUtils.hasText(tokenScope) ? Scope.getEnum(tokenScope):null;
		@SuppressWarnings("unchecked")
		Map<String, Object> instructions = (Map<String, Object>)src.get(AUTH_TKN_INSTRUCTIONS);
		@SuppressWarnings("unchecked")
		Map<String, Object> notes = (Map<String, Object>)src.get(AUTH_TKN_NOTES);
		return new TokenBuilder(token).
				addClient(client).
				addExpire(expire).
				addInstructions(instructions).
				addNotes(notes).
				addOwner(owner).
				addScope(scope).
				addType(type).build();
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.token.AbstractAccessToken#serailize()
	 */
	@Override
	protected Map<String, Object> serailize() {
		Map<String, Object> map = new WeakHashMap<String, Object>(10);
		map.put(ID, this.token.getToken());
		if(StringUtils.hasText(this.token.getParaphrase()))
			map.put(ENCRYPT_STARTEGY, this.token.getParaphrase());
		map.put(TYPE, this.type.getTokenType());
		if(StringUtils.hasText(getOwner()))
			map.put(AUTH_TKN_OWNER, this.owner);
		map.put(AUTH_TKN_CLIENT, this.client);
		map.put(AUTH_TKN_EXPIRES, this.expire);
		if(!CollectionUtils.isEmpty(notes))
			map.put(AUTH_TKN_NOTES, this.notes);
		if(!CollectionUtils.isEmpty(instructions))
			map.put(AUTH_TKN_INSTRUCTIONS, this.instructions);
		map.put(SCOPE, this.scope.getScope());
		return map;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessToken#updateInstructions(java.util.Map, boolean)
	 */
	@Override
	public AccessToken updateInstructions(Map<String, Object> instructions, boolean updateFlag) {
		TokenBuilder builder = new TokenBuilder(token).
				addClient(client).
				addExpire(expire).
				addInstructions(this.instructions).
				addNotes(notes).
				addOwner(owner).
				addScope(scope).
				addType(type);
		
		if(updateFlag && !CollectionUtils.isEmpty(instructions)){
			Map<String, Object> ins = getInstructions();
			ins.putAll(instructions);
			builder.addInstructions(ins);
		}else {
			builder.addInstructions(instructions);
		}
		return builder.build();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessToken#updateNotes(java.util.Map, boolean)
	 */
	@Override
	public AccessToken updateNotes(Map<String, Object> notes, boolean updateFlag) {
		TokenBuilder builder = new TokenBuilder(token).
				addClient(client).
				addExpire(expire).
				addInstructions(instructions).
				addNotes(this.notes).
				addOwner(owner).
				addScope(scope).
				addType(type);
		
		if(updateFlag && !CollectionUtils.isEmpty(notes)){
			Map<String, Object> nts = getNotes();
			nts.putAll(notes);
			builder.addNotes(nts);
		}else {
			builder.addInstructions(notes);
		}
		return builder.build();
	}

	public static class TokenBuilder implements Builder<DefaultAccessToken>{
		
		private String tokenId;
		private Token token;
		private String owner;
		private String client;
		private long expire;
		private TokenType type;
		private Scope scope;
		private Map<String, Object> notes;
		private Map<String, Object> instructions;
		
		public TokenBuilder(Token token){
			Assert.notNull(token, "Token Required");
			this.token = token;
			this.tokenId = token.getToken();
		}
		
		public TokenBuilder addOwner(String ownerId){
			this.owner = ownerId;
			return this;
		}
		
		public TokenBuilder addClient(String clientId){
			this.client = clientId;
			return this;
		}
		
		public TokenBuilder addExpire(long expire){
			this.expire = expire;
			return this;
		}
		
		public TokenBuilder addType(TokenType type){
			this.type = type;
			return this;
		}
		
		public TokenBuilder addScope(Scope scope){
			this.scope = scope;
			return this;
		}
		
		public TokenBuilder addNotes(Map<String, Object> notes){
			this.notes = notes;
			return this;
		}
		
		public TokenBuilder addInstructions(Map<String, Object> instructions){
			this.instructions = instructions;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public DefaultAccessToken build() {
			return new DefaultAccessToken(this);
		}
	}
	
	private DefaultAccessToken(TokenBuilder builder){
		setId(builder.tokenId);
		this.client = builder.client;
		this.expire = builder.expire;
		this.instructions = builder.instructions;
		this.notes = builder.notes;
		this.owner = builder.owner;
		this.scope = builder.scope;
		this.token = builder.token;
		this.type = builder.type;
	}

	@Override
	public String toString() {
		StandardToStringStyle style = new StandardToStringStyle();
	    style.setFieldSeparator(", ");
	    style.setUseClassName(false);
	    style.setUseIdentityHashCode(false);
	    return new ReflectionToStringBuilder(this, style).toString();
	}
	
}

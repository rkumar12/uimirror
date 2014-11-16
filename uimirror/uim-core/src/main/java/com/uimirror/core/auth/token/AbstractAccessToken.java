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

import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_MESSAGES;
import static com.uimirror.core.auth.token.AccessTokenFields.ENCRYPT_STARTEGY;
import static com.uimirror.core.auth.token.AccessTokenFields.TOKEN;
import static com.uimirror.core.auth.token.AccessTokenFields.TYPE;

import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.util.DateTimeUtil;

/**
 * A basic implementation of the accesstoken
 * @author Jay
 */
public abstract class AbstractAccessToken<T> extends AbstractBeanBasedDocument<T> implements AccessToken, BeanValidatorService{

	private static final long serialVersionUID = 1758356201287067187L;
	protected Token token;
	protected String owner;
	protected String client;
	protected long expire;
	protected TokenType type;
	protected Scope scope;
	protected Map<String, Object> notes;
	protected Map<String, Object> instructions;
	
	//Don't use this if not special condition
	protected AbstractAccessToken(){
		super();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public T readFromMap(Map<String, Object> src) {
		//Validate the source shouldn't be empty
		isValidSource(src);
		//Initialize the state
		return init(src);
	}
	
	/**
	 * Defines the contract for the de-serializing the persisted source to represent a valid state.
	 * @param src {@link Map} from where state will be presumed
	 * @return {@link AccessToken}
	 */
	protected abstract T init(Map<String, Object> src);

	/**
	 * This map the document should have which fields
	 * always it will have _id, parapharse, id, expire, type and scope
	 */
	@Override
	public Map<String, Object> writeToMap(){
		//First check if it represents a valid state then can be serialized
		if(!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}

	/** 
	 * Checks the necessary fields that needs to be present to demonstrate a state of the client. 
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(!StringUtils.hasText(getId()))
			valid = Boolean.FALSE;
		if(getType() == null)
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getClient()))
			valid = Boolean.FALSE;
		if(getScope() == null)
			valid = Boolean.FALSE;
		return valid;
	}
	
	/**
	 * Creates the {@link Map} that will be serialized over the network
	 * @return a serialized {@link Map} 
	 */
	protected abstract Map<String, Object> serailize();
	
	/**
	 * Validates the map objects to make sure map has proper details to populate the object
	 * @param token token
	 * @param id token id
	 * @param timeout interval to expiry
	 * @param type token
	 * @param scope of the issued token
	 */
	protected void validate(Token token, String id, long timeout, TokenType type, Scope scope){
		Assert.notNull(token, ERR_MSG);
		validate(id, timeout, type, scope);
	}
	
	/**
	 * Validates the map objects to make sure map has proper details to populate the object
	 * @param id token 
	 * @param timeout interval of the token
	 * @param type issued token type
	 * @param scope scope of the token
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
		return this.notes == null ? new WeakHashMap<String, Object>(5) : this.notes;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AccessToken#getInstructions()
	 */
	@Override
	public Map<String, Object> getInstructions() {
		return this.instructions == null ? new WeakHashMap<String, Object>(5) : this.instructions;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AccessToken#toResponseMap()
	 */
	@Override
	public Map<String, Object> toResponseMap() {
		Map<String, Object> rs = new WeakHashMap<String, Object>(15);
		rs.put(TOKEN, token.getToken());
		if(StringUtils.hasText(token.getParaphrase()))
			rs.put(ENCRYPT_STARTEGY, token.getParaphrase());
		rs.put(TYPE, type.getTokenType());
		if(!CollectionUtils.isEmpty(getInstructions())){
			rs.put(AUTH_TKN_MESSAGES, getInstructions());
		}
		return rs;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessToken#isExpired()
	 */
	@Override
	public boolean isActive(){
		boolean active = Boolean.TRUE;
		if(getName() == null)
			throw new IllegalArgumentException("Token is not in valid state.");
		else if(TokenType.TEMPORAL != getType())
			active = !(DateTimeUtil.isExpired(getExpire()));
		return active;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessToken#isRefreshRequired()
	 */
	@Override
	public boolean isRefreshRequired(){
		boolean aboutToExpire = Boolean.TRUE;
		aboutToExpire = DateTimeUtil.isCurrentUTCApproachingBy(getExpire(), 15);
		if(!aboutToExpire){
			if(!isActive())
				throw new IllegalArgumentException("Not a Valid a token.");
		}
		return aboutToExpire;
	}

}

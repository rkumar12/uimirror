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

import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_CLIENT;
import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_EXPIRES;
import static com.uimirror.core.auth.token.AccessTokenFields.AUTH_TKN_OWNER;
import static com.uimirror.core.auth.token.AccessTokenFields.ENCRYPT_STARTEGY;
import static com.uimirror.core.auth.token.AccessTokenFields.SCOPE;
import static com.uimirror.core.auth.token.AccessTokenFields.TYPE;
import static com.uimirror.core.mongo.feature.BasicDBFields.ID;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.WeakHashMap;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;

import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.util.DateTimeUtil;

/**
 * @author Jay
 */
public class DefaultAccessTokenTest {

	private Token tkn;
	private Map<String, Object> map;
	private DefaultAccessToken dtkn;
	
	@Before
	public void setUp(){
		tkn = new Token("123","test");
		
		this.dtkn = new DefaultAccessToken.TokenBuilder(tkn).
				addClient("c1").
				addExpire(1000).
				addOwner("o1").
				addScope(Scope.READ).
				addType(TokenType.ACCESS).
				build();
		map = new WeakHashMap<String, Object>(10);
		map.put(ID, dtkn.getName());
		map.put(ENCRYPT_STARTEGY, "test");
		map.put(TYPE, TokenType.ACCESS.getTokenType());
		map.put(AUTH_TKN_OWNER, dtkn.getOwner());
		map.put(AUTH_TKN_CLIENT, dtkn.getClient());
		map.put(AUTH_TKN_EXPIRES, dtkn.getExpire());
		map.put(SCOPE, dtkn.getScope().getScope());
	}
	
	@Test
	public void testEqulasHashCode() {
		EqualsVerifier.forClass(DefaultAccessToken.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}
	
	@Test
	public void testSerailize(){
		assertThat(dtkn.writeToMap()).isEqualTo(map);
	}
	
	@Test
	public void testDeSerailize(){
		assertThat(dtkn.readFromMap(map).toString()).isEqualTo(dtkn.toString());
	}
	
	@Test
	public void testIsActive(){
		DefaultAccessToken dtkn = new DefaultAccessToken.TokenBuilder(tkn).
				addClient("c1").
				addExpire(DateTimeUtil.getCurrentSystemUTCEpoch()).
				addOwner("o1").
				addScope(Scope.READ).
				addType(TokenType.ACCESS).
				build();
		assertThat(dtkn.isActive()).isFalse();
		
		DefaultAccessToken dtkn1 = new DefaultAccessToken.TokenBuilder(tkn).
				addClient("c1").
				addExpire(DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(2)).
				addOwner("o1").
				addScope(Scope.READ).
				addType(TokenType.ACCESS).
				build();
		assertThat(dtkn1.isActive()).isTrue();

		DefaultAccessToken dtkn2 = new DefaultAccessToken.TokenBuilder(tkn).
				addClient("c1").
				addExpire(0).
				addOwner("o1").
				addScope(Scope.READ).
				addType(TokenType.TEMPORAL).
				build();
		assertThat(dtkn2.isActive()).isTrue();
	}
	
	@Test
	public void testRefreshRequired(){
		DefaultAccessToken dtkn = new DefaultAccessToken.TokenBuilder(tkn).
				addClient("c1").
				addExpire(DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(16)).
				addOwner("o1").
				addScope(Scope.READ).
				addType(TokenType.ACCESS).
				build();
		assertThat(dtkn.isRefreshRequired()).isTrue();

		DefaultAccessToken dtkn1 = new DefaultAccessToken.TokenBuilder(tkn).
				addClient("c1").
				addExpire(DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(20)).
				addOwner("o1").
				addScope(Scope.READ).
				addType(TokenType.ACCESS).
				build();
		assertThat(dtkn1.isRefreshRequired()).isFalse();

		DefaultAccessToken dtkn2 = new DefaultAccessToken.TokenBuilder(tkn).
				addClient("c1").
				addExpire(DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(30)).
				addOwner("o1").
				addScope(Scope.READ).
				addType(TokenType.ACCESS).
				build();
		assertThat(dtkn2.isRefreshRequired()).isFalse();
	}

}

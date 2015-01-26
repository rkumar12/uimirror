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
package com.uimirror.user;

import static com.uimirror.core.mongo.feature.BasicDBFields.ID;
import static com.uimirror.core.shop.UserAuthDBFields.ACCOUNT_STATE;
import static com.uimirror.core.shop.UserAuthDBFields.ACCOUNT_STATUS;
import static com.uimirror.core.shop.UserAuthDBFields.PASSWORD;
import static com.uimirror.core.shop.UserAuthDBFields.SCREEN_PASSWORD;
import static com.uimirror.core.shop.UserAuthDBFields.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.junit.Before;
import org.junit.Test;

import com.uimirror.core.AccountState;
import com.uimirror.core.AccountStatus;

/**
 * @author Jay
 */
public class CredentialsTest {

	private List<String> username = new ArrayList<String>();
	private Credentials c1;
	private Credentials c2;
	
	private Map<String, Object> cm1;
	private Map<String, Object> cm2;
	
	@Before
	public void setUp(){
		username.add("1234");
		username.add("456");
		c1  = new Credentials.CredentialsBuilder("123").
				addPassword("123").addScreenPassword("4520").
				addState(AccountState.NEW).addStatus(AccountStatus.ACTIVE).
				addUserNames(username).build();
		c2 = new Credentials.CredentialsBuilder("123").
				addPassword("123").addScreenPassword("4520").
				addState(AccountState.ENABLED).addStatus(AccountStatus.ACTIVE).
				addUserNames(username).build();
		cm1 = new WeakHashMap<String, Object>();
		cm1.put(ID, c1.getProfileId());
		cm1.put(USER_ID, c1.getUserId());
		cm1.put(PASSWORD, c1.getPassword());
		cm1.put(SCREEN_PASSWORD, c1.getScreenPassword());
		cm1.put(ACCOUNT_STATUS, AccountStatus.ACTIVE.getStatus());
		cm1.put(ACCOUNT_STATE, AccountState.NEW.getState());
		cm2 = new WeakHashMap<String, Object>();
		cm2.put(ID, c2.getProfileId());
		cm2.put(USER_ID, c2.getUserId());
		cm2.put(PASSWORD, c2.getPassword());
		cm2.put(SCREEN_PASSWORD, c2.getScreenPassword());
		cm2.put(ACCOUNT_STATUS, AccountStatus.ACTIVE.getStatus());
		cm2.put(ACCOUNT_STATE, AccountState.ENABLED.getState());
		
	}

	@Test
	public void serializeTest(){
		assertThat(c1.writeToMap()).isEqualTo(cm1);
		assertThat(c1.writeToMap()).isNotEqualTo(cm2);
		assertThat(c2.writeToMap()).isEqualTo(cm2);
	}
}

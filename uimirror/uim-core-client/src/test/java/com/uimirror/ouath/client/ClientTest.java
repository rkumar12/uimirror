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
package com.uimirror.ouath.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.WeakHashMap;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;

import com.uimirror.core.AccountStatus;
import com.uimirror.core.client.Client;
import com.uimirror.core.client.ClientDBFields;
import com.uimirror.core.util.DateTimeUtil;

/**
 * @author Jay
 */
public class ClientTest {

	private Client c1;
	private Client c2;
	private Map<String, Object> cm1;
	private Map<String, Object> cm2;
	
	@Before
	public void setUp(){
		long currentEpoch = DateTimeUtil.getCurrentSystemUTCEpoch();
		c1 = new Client.ClientBuilder("123").
				addApiKey("aseas").
				addAppURL("http://uimirror.com").
				addImagePath("c1.png").
				addName("UIMIRROR").
				addRedirectURI("http://uimirror.com/test").
				addRegisteredBy("123").
				addRegisteredOn(currentEpoch).
				addSecret("azsxe").
				addStatus(AccountStatus.ACTIVE).build();

		c2 = new Client.ClientBuilder("123").
				addApiKey("aseas").
				addAppURL("uimirror.com").
				addImagePath("c1.png").
				addName("UIMIRROR").
				addRedirectURI("uimirror.com/test").
				addRegisteredBy("123").
				addRegisteredOn(currentEpoch).
				addSecret("azsxe").
				addStatus(AccountStatus.BLOCKED).build();
		cm1 = new WeakHashMap<String, Object>();
		cm1.put(ClientDBFields.ID, "123");
		cm1.put(ClientDBFields.NAME, "UIMIRROR");
		cm1.put(ClientDBFields.SECRET, "azsxe");
		cm1.put(ClientDBFields.STATUS, AccountStatus.ACTIVE.getStatus());
		cm1.put(ClientDBFields.REDIRECT_URI, "http://uimirror.com/test");
		cm1.put(ClientDBFields.APP_URL, "http://uimirror.com");
		cm1.put(ClientDBFields.API_KEY, "aseas");
		cm1.put(ClientDBFields.REGISTERED_BY, "123");
		cm1.put(ClientDBFields.REGISTERED_ON, currentEpoch);
		cm1.put(ClientDBFields.IMAGE, "c1.png");
		
		cm2 = new WeakHashMap<String, Object>();
		cm2.put(ClientDBFields.ID, "123");
		cm2.put(ClientDBFields.NAME, "UIMIRROR");
		cm2.put(ClientDBFields.SECRET, "azsxe");
		cm2.put(ClientDBFields.STATUS, AccountStatus.BLOCKED.getStatus());
		cm2.put(ClientDBFields.REDIRECT_URI, "uimirror.com/test");
		cm2.put(ClientDBFields.APP_URL, "uimirror.com");
		cm2.put(ClientDBFields.API_KEY, "aseas");
		cm2.put(ClientDBFields.REGISTERED_BY, "123");
		cm2.put(ClientDBFields.REGISTERED_ON, currentEpoch);
		cm2.put(ClientDBFields.IMAGE, "c1.png");
	}
	
	@Test
	public void testEquals() {
		EqualsVerifier.forClass(Client.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}
	
	@Test
	public void testSerailize(){
		assertThat(c1.writeToMap()).isEqualTo(cm1);
		assertThat(c2.writeToMap()).isEqualTo(cm2);
		cm2.put(ClientDBFields.ID, "456");
		assertThat(c2.updateId("456").writeToMap()).isEqualTo(cm2);
	}
	
	@Test
	public void testDeSerailize(){
		assertThat(c1.readFromMap(cm1).toString()).isEqualTo(c1.toString());
	}

	@Test
	public void testActive(){
		assertThat(c1.isActive()).isTrue();
		assertThat(c2.isActive()).isFalse();
	}
	@Test
	public void testValidUrl(){
		assertThat(c1.isValidAppAndRedirectURL()).isTrue();
		assertThat(c2.isValidAppAndRedirectURL()).isFalse();
	}

}

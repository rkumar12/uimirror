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
package com.uimirror.ws.account.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uimirror.account.conf.AppConfig;
import com.uimirror.account.conf.BeanIntitializer;
import com.uimirror.core.Processor;
import com.uimirror.core.RandomKeyGenerator;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.ouath.client.Client;

/**
 * @author Jay
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={BeanIntitializer.class, AppConfig.class})
public class CreateClientAccountProviderTest {
	
	private Client client;
	
	private @Autowired Processor<Client, Client> createClientAccountProvider;
	
	@Before
	public void setUp(){
		client = new Client.ClientBuilder(null).
				addApiKey(RandomKeyGenerator.randomSecureString(16)).
				addAppURL("http://uimirror.com").
				addImagePath("uim_logo.png").
				addName("UIMIRROR").
				addRedirectURI("http://uimirror.com/test").
				addRegisteredBy("UIM_ADM").
				addRegisteredOn(DateTimeUtil.getCurrentSystemUTCEpoch()).
				addSecret(RandomKeyGenerator.randomSecureString(10)).
				addStatus(AccountStatus.ACTIVE).build();
	}

	@Test
	public void createClientAccount() {
		Client up_client = createClientAccountProvider.invoke(client);
		System.out.println(up_client);
	}

}

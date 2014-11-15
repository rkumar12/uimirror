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
package com.uimirror.ouath.client.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uimirror.core.Processor;
import com.uimirror.core.service.ValidatorService;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.conf.BeanOfClient;
import com.uimirror.ouath.client.conf.ClientTestMissingBean;
import com.uimirror.ouath.client.exception.ClientAlreadyExistException;
import com.uimirror.ouath.client.store.ClientStore;

/**
 * @author Jay
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={BeanOfClient.class, ClientTestMissingBean.class})
public class CreateClientAccountProviderTest {
	
	@Mock
	private ClientStore persistedClientMongoStore;
	
	@InjectMocks
	private @Autowired Processor<Client, Client> createClientAccountProvider;
	
	@InjectMocks
	private @Autowired ValidatorService<Client> clientAccountValidator;
	private Client client;
	private Client clientWithId;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		client = new Client.ClientBuilder(null).
				addApiKey("aseas").
				addAppURL("http://uimirror.com").
				addImagePath("c1.png").
				addName("UIMIRROR").
				addRedirectURI("http://uimirror.com/test").
				addRegisteredBy("123").
				addRegisteredOn(DateTimeUtil.getCurrentSystemUTCEpoch()).
				addSecret("azsxe").
				addStatus(AccountStatus.ACTIVE).build();
		clientWithId = new Client.ClientBuilder("123").
				addApiKey("aseas").
				addAppURL("http://uimirror.com").
				addImagePath("c1.png").
				addName("UIMIRROR").
				addRedirectURI("http://uimirror.com/test").
				addRegisteredBy("123").
				addRegisteredOn(DateTimeUtil.getCurrentSystemUTCEpoch()).
				addSecret("azsxe").
				addStatus(AccountStatus.ACTIVE).build(); 
			
	}
	
	@Test(expected=ClientAlreadyExistException.class)
	public void testClientExists() {
		doReturn(client).when(persistedClientMongoStore).findClientByAppUrl(anyString());
		createClientAccountProvider.invoke(client);
	}

	@Test
	public void testSaveClient() {
		doReturn(null).when(persistedClientMongoStore).findClientByAppUrl(anyString());
		doReturn(clientWithId).when(persistedClientMongoStore).store(any());
		Client updatedClient = createClientAccountProvider.invoke(client);
		assertThat(updatedClient.getClientId()).isEqualTo("123");
		
	}

}

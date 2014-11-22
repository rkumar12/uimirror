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

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uimirror.account.client.ClientRegisterForm;
import com.uimirror.core.service.TransformerService;
import com.uimirror.ouath.client.BeanOfClient;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.conf.ClientTestMissingBean;
import com.uimirror.ouath.client.store.ClientStore;

/**
 * @author Jay
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={BeanOfClient.class, ClientTestMissingBean.class})
//@PrepareForTest(ClientRegisterForm.class)
@Ignore("Enable this once, final class mocking success")
public class ClientRegisterFormToClientTransformerTest{
	
	@InjectMocks
	private @Autowired TransformerService<ClientRegisterForm, Client> clientRegisterFormToClientTransformer;
	
	private ClientRegisterForm clientRegisterForm;
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		//clientRegisterForm = PowerMockito.mock(ClientRegisterForm.class);
	}
	
	@Mock
	private ClientStore persistedClientMongoStore;
	
	
	@Test
	public void test() {
		when(clientRegisterForm.getName()).thenReturn("Test");
		clientRegisterFormToClientTransformer.transform(clientRegisterForm);
	}

}

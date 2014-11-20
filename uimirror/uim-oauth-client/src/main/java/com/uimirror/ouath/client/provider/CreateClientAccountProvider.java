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
package com.uimirror.ouath.client.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.Processor;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.service.ValidatorService;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.store.ClientStore;

/**
 * Processor for the client account creation, it will first check for the client existence
 * if not found will try to create a new else, else stop
 * Steps are as below
 * <ol>
 * <li>Check if previously the client has been registered</li>
 * <li>create client account</li>
 * </ol>
 * 
 * @author Jay
 */
public class CreateClientAccountProvider implements Processor<Client, Client>{

	protected static final Logger LOG = LoggerFactory.getLogger(CreateClientAccountProvider.class);
	private ValidatorService<Client> clientAccountValidator;
	private ClientStore persistedClientMongoStore;

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public Client invoke(Client client) throws ApplicationException {
		LOG.info("[START]- Registering a new Client.");
		//Step -1 Validate provided details are correct or not
		clientAccountValidator.validate(client);
		//Step -2 Finally Save
		client = persistedClientMongoStore.store(client);
		LOG.info("[END]- Registering a new Client.");
		return client;
	}

	public void setClientAccountValidator(ValidatorService<Client> clientAccountValidator) {
		this.clientAccountValidator = clientAccountValidator;
	}

	public void setPersistedClientMongoStore(ClientStore persistedClientMongoStore) {
		this.persistedClientMongoStore = persistedClientMongoStore;
	}

}

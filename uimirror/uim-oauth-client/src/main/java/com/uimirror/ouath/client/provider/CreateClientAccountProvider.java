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
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.service.ValidatorService;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.form.ClientRegisterForm;
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
public class CreateClientAccountProvider implements Processor<ClientRegisterForm, Client>{

	protected static final Logger LOG = LoggerFactory.getLogger(CreateClientAccountProvider.class);
	private @Autowired TransformerService<ClientRegisterForm, Client> clientRegisterFormToClientTransformer;
	private @Autowired ValidatorService<Client> createClientAccountValidator;
	private @Autowired ClientStore persistedClientMongoStore;

	public CreateClientAccountProvider() {
		// NOP
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public Client invoke(ClientRegisterForm param) throws ApplicationException {
		LOG.info("[START]- Registering a new Client.");
		//Step -1 Transform to the desired type
		Client client = transformToClient(param);
		//Step -2 Validate provided details are correct or not
		createClientAccountValidator.validate(client);
		//Step -3 Finally Save
		client = persistedClientMongoStore.store(client);
		LOG.info("[END]- Registering a new Client.");
		return client;
	}
	
	
	/**
	 * Convert from {@link ClientRegisterForm} to {@link Client}
	 * This will not populate the owner and details map
	 * @param param
	 * @return
	 */
	private Client transformToClient(ClientRegisterForm param){
		return clientRegisterFormToClientTransformer.transform(param);
	}

}
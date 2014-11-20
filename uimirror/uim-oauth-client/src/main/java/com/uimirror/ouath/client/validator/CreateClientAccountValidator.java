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
package com.uimirror.ouath.client.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.dao.RecordNotFoundException;
import com.uimirror.core.service.ValidatorService;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.exception.ClientAlreadyExistException;
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
public class CreateClientAccountValidator implements ValidatorService<Client>{

	protected static final Logger LOG = LoggerFactory.getLogger(CreateClientAccountValidator.class);
	private ClientStore persistedClientMongoStore;

	public CreateClientAccountValidator() {
		// NOP
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.ValidatorService#validate(java.lang.Object)
	 */
	@Override
	public boolean validate(Client src) {
		if(isClientExists(src))
			throw new ClientAlreadyExistException();
		return Boolean.FALSE;
	}
	
	/**
	 * @param src
	 * @return
	 */
	private boolean isClientExists(Client src){
		boolean exists = Boolean.FALSE;
		Client client = getClientIfExists(src.getAppURL());
		if(client != null){
			exists = Boolean.TRUE;
		}
		return exists;
	}

	/**
	 * @param appUrl
	 * @return
	 */
	private Client getClientIfExists(String appUrl){
		Client client = null;
		try{
			client = persistedClientMongoStore.findClientByAppUrl(appUrl);
		}catch(RecordNotFoundException e){
			LOG.info("[INFO]- Client details not found");
		}
		return client;
	}

	public void setPersistedClientMongoStore(ClientStore persistedClientMongoStore) {
		this.persistedClientMongoStore = persistedClientMongoStore;
	}

}

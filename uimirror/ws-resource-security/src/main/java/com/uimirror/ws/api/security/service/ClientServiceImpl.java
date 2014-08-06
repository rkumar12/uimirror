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
package com.uimirror.ws.api.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.mongodb.MongoException;
import com.uimirror.ws.api.security.bean.base.Client;
import com.uimirror.ws.api.security.exception.ClientException;
import com.uimirror.ws.api.security.exception.ErrorConstant;
import com.uimirror.ws.api.security.repo.ClientDao;

/**
 * <p>Common Service point for the client details</p>
 * @author Jay
 */
public class ClientServiceImpl implements ClientService {
	
	protected static final Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class);
	private final ClientDao clientDao;
	
	public ClientServiceImpl(ClientDao clientDao) throws IllegalStateException{
		if(clientDao == null)
			throw new IllegalStateException("Dao Instance can't be empty to avail the service");
		this.clientDao = clientDao;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.ClientService#getClientById(java.lang.String)
	 */
	@Override
	public Client getClientById(String clientId) throws ClientException, IllegalArgumentException {
		LOG.info("[START]- Getting the client details by client id");
		Assert.hasText(clientId, "Client ID can't be empty");
		Client client = null;
		try{
			client = this.clientDao.findById(clientId);
			if(client == null)
				throw new ClientException(ErrorConstant.CLIENT_NOT_FOUND, String.format("Client not found for the id %s", clientId));
			
		}catch(MongoException me){
			LOG.error("Getting client details facing some DB issue {}",me);
			throw new ClientException(ErrorConstant.MONGO_ERROR, String.format("Client not found for the id %s", clientId), me);
		}
		LOG.info("[END]- Getting the client details by client id");
		return client;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.ClientService#suspendAClient(java.lang.String)
	 */
	@Override
	public void suspendAClient(String clientId) throws ClientException, IllegalArgumentException {
		// TODO Auto-generated method stub

	}

}

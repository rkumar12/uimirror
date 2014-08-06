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

import com.uimirror.ws.api.security.bean.base.Client;
import com.uimirror.ws.api.security.exception.ClientException;

/**
 * <p>Service for the client related activity such as retrieving details and updating</p>
 * @author Jay
 */
public interface ClientService {
	
	/**
	 * <p>Get the client from the client collection by id</p>
	 * @param clientId
	 * @return {@link Client} if found.
	 * @throws ClientException error code 404 for if no client exist else 500 for Database error
	 * @throws IllegalArgumentException if input parameter is incorrect
	 */
	public Client getClientById(final String clientId) throws ClientException, IllegalArgumentException;

	/**
	 * <p>Make the client temporary suspended and give reason for suspension</p>
	 * @param clientId
	 * @throws ClientException
	 * @throws IllegalArgumentException
	 */
	public void suspendAClient(final String clientId) throws ClientException, IllegalArgumentException;
}

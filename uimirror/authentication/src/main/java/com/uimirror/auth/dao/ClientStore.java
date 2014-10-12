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
package com.uimirror.auth.dao;

import com.uimirror.auth.client.Client;

/**
 * A basic repo for the client 
 * that deals with store and retrieve of the client
 * @author Jay
 */
public interface ClientStore {

	/**
	 * Query a {@link Client} record by the apiKey
	 * 
	 * @param apiKey if invalid will throw {@link IllegalArgumentException}
	 * @return
	 */
	Client findClientByApiKey(String apiKey);
	
	/**
	 * Query a Client By client ID
	 * @param clientId if invalid throw {@link IllegalArgumentException}
	 * @return
	 */
	Client findClientById(String clientId);
	
	/**
	 * Query a client by apiKey
	 * 
	 * @param apiKey if invalid throw {@link IllegalArgumentException}
	 * @return
	 */
	Client findActieveClientByApiKey(String apiKey);
	
	/**
	 * query a active client by client id
	 * @param clientId if invalid throw {@link IllegalArgumentException}
	 * @return
	 */
	Client findActieveClientById(String clientId);
}

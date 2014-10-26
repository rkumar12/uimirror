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
package com.uimirror.ouath.client.store;

import com.uimirror.core.dao.DBException;
import com.uimirror.ouath.client.Client;

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
	Client findClientByApiKey(String apiKey)  throws DBException;
	
	/**
	 * Query a Client By client ID
	 * @param clientId if invalid throw {@link IllegalArgumentException}
	 * @return
	 */
	Client findClientById(String clientId) throws DBException;
	
	/**
	 * Query a Client By client ID
	 * @param clientId if invalid throw {@link IllegalArgumentException}
	 * @param fields
	 * @return
	 */
	Client findClientById(String clientId, String ... fields) throws DBException;
	
	/**
	 * Query a client by apiKey
	 * 
	 * @param apiKey if invalid throw {@link IllegalArgumentException}
	 * @return
	 */
	Client findActieveClientByApiKey(String apiKey) throws DBException;
	
	/**
	 * query a active client by client id
	 * @param clientId if invalid throw {@link IllegalArgumentException}
	 * @return
	 */
	Client findActieveClientById(String clientId) throws DBException;
	
	/**
	 * Finds Client by App URL
	 * Make sure, it only retrieves the app URL and name of the client
	 * @param url
	 * @return
	 * @throws DBException
	 */
	Client findClientByAppUrl(String url) throws DBException;
	
	/**
	 * Stores client in document
	 * @param client
	 * @throws DBException
	 */
	Client store(Client client) throws DBException;
	
}

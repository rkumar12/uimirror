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
	String CLIENT_BASIC_SEQ = "cbs";
	/**
	 * Query a {@link Client} record by the apiKey
	 * 
	 * @param apiKey if invalid will throw {@link IllegalArgumentException}
	 * @return a valid client Object by key
	 */
	Client findClientByApiKey(String apiKey)  throws DBException;
	
	/**
	 * Query a Client By client ID
	 * @param clientId if invalid throw {@link IllegalArgumentException}
	 * @return a valid client using client Id
	 */
	Client findClientById(String clientId) throws DBException;
	
	/**
	 * Query a Client By client ID
	 * @param clientId if invalid throw {@link IllegalArgumentException}
	 * @param clientId as first parameter
	 * @param fields as second parameter 
	 * @return a client using client Id and other parameters
	 */
	Client findClientById(String clientId, String ... fields) throws DBException;
	
	/**
	 * Query a client by apiKey
	 * 
	 * @param apiKey if invalid throw {@link IllegalArgumentException}
	 * @return a valid active client using api key
	 */
	Client findActieveClientByApiKey(String apiKey) throws DBException;
	
	/**
	 * query a active client by client id
	 * @param clientId if invalid throw {@link IllegalArgumentException}
	 * @return a valid active client using client Id
	 */
	Client findActieveClientById(String clientId) throws DBException;
	
	/**
	 * Finds Client by App URL
	 * Make sure, it only retrieves the app URL and name of the client
	 * @param  url as parameters
	 * @return a valid client
	 * @throws DBException when url not found
	 */
	Client findClientByAppUrl(String url) throws DBException;
	
	/**
	 * Stores client in document
	 * @param client passing as parameter
	 * @return a valid client
	 * @throws DBException when valid client not found in DB
	 */
	Client store(Client client) throws DBException;
	
}

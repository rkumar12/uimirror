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
//TODO impement this latter
public interface ClientStore {

	Client findClientByApiKey(String apiKey);
	
	Client findClientById(String clientId);
	
	Client findActieveClientByApiKey(String apiKey);
	
	Client findActieveClientById(String clientId);
}

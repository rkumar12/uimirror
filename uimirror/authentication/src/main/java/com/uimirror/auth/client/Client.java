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
package com.uimirror.auth.client;

import java.util.Map;

import com.uimirror.core.mongo.feature.BeanBasedDocument;

/**
 * A basic client , which will have at minimum client id,
 * API key, client secret, redirect URL  
 * @author Jay
 */
public class Client extends BeanBasedDocument{

	private static final long serialVersionUID = -5074118579759365950L;
	
	private String secret;

	public Client(Map<String, Object> map) {
		super(map);
	}
	
	public String getClientId(){
		return getId();
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public void initFromMap(Map<String, Object> src) {
		// TODO Auto-generated method stub
		
	}

}

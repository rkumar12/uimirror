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
package com.uimirror.ouath.client.processor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.core.exceptions.ApplicationExceptionMapper;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.ClientDBFields;
import com.uimirror.ouath.client.form.ClientRegisterForm;

/**
 * Processor for the client account creation, it will first check for the client existence
 * if not found will try to create a new else, else stop
 * Steps are as below
 * <ol>
 * <li>Validate and get the token details</li>
 * <li>Check if the same owner has already the same account</li>
 * <li>create client account</li>
 * </ol>
 * 
 * @author Jay
 */
public class CreateClientAccountProcessor implements Processor<ClientRegisterForm, String>{

	protected static final Logger LOG = LoggerFactory.getLogger(CreateClientAccountProcessor.class);
	private @Autowired Processor<ClientRegisterForm, Client> createClientAccountProvider;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;

	public CreateClientAccountProcessor() {
		// NOP
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	@MapException(use=ApplicationExceptionMapper.NAME)
	public String invoke(ClientRegisterForm param) throws ApplicationException {
		LOG.info("[START]- Registering a new Client.");
		Client client =createClientAccountProvider.invoke(param);
		LOG.info("[END]- Registering a new Client.");
		return jsonResponseTransFormer.doTransForm(cleanClient(client));
	}
	
	/**
	 * Creates the final response map that will be respond back to the client
	 * @param client
	 * @return
	 */
	private Map<String, Object> cleanClient(Client client){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(ClientDBFields.API_KEY, client.getApiKey());
		map.put(ClientDBFields.SECRET, client.getSecret());
		return map;
	}

}

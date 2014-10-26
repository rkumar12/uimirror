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
package com.uimirror.ouath.client.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DBCollection;
import com.uimirror.core.Processor;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.service.ValidatorService;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.form.ClientRegisterForm;
import com.uimirror.ouath.client.processor.CreateClientAccountProcessor;
import com.uimirror.ouath.client.provider.CreateClientAccountProvider;
import com.uimirror.ouath.client.store.ClientStore;
import com.uimirror.ouath.client.store.PersistedClientMongoStore;
import com.uimirror.ouath.client.transformer.ClientRegisterFormToClientTransformer;
import com.uimirror.ouath.client.validator.CreateClientAccountValidator;

/**
 * Contains the bean definition for all the authentication controller process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfOAuthClient {

	@Bean
	@Autowired
	public ClientStore persistedClientMongoStore(@Qualifier("clientBasicInfoCol") DBCollection collection, @Qualifier("clientBasicInfoSeqCol") DBCollection seqCollection){
		return new PersistedClientMongoStore(collection, seqCollection);
	}
	
	@Bean
	public ValidatorService<Client> createClientAccountValidator() {
		return new CreateClientAccountValidator();
	}
	
	@Bean
	public TransformerService<ClientRegisterForm, Client> clientRegisterFormToClientTransformer(){
		return new ClientRegisterFormToClientTransformer();
	}
	
	@Bean
	public Processor<ClientRegisterForm, String> createClientAccountProcessor() {
		return new CreateClientAccountProcessor();
	}
	
	@Bean
	public Processor<ClientRegisterForm, Client> createClientAccountProvider() {
		return new CreateClientAccountProvider();
	}

}

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
package com.uimirror.account.conf.client;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.uimirror.core.Processor;
import com.uimirror.core.mongo.DBCollectionUtil;
import com.uimirror.core.mongo.MongoDbFactory;
import com.uimirror.core.rest.extra.ResponseTransFormer;
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
public class BeanOfClient {
	
	protected @Value("${client.col.name:basic_info}") String clientBasicInfoCollection;
	protected @Value("${client.seq.col.name:basic_info_seq}") String clientBasicInfoSeqCollection;
	protected @Value("${client.db.name:uim_client}") String clientDb;
	
	@Bean
	@Autowired
	public DB clientDB(Mongo mongo) throws UnknownHostException{
		return MongoDbFactory.getDB(mongo, this.clientDb);
	}
	
	@Bean
	@Autowired
	public DBCollection clientBasicInfoCol(DB clientDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(clientDB, this.clientBasicInfoCollection);
	}
	
	@Bean
	@Autowired
	public DBCollection clientBasicInfoSeqCol(DB clientDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(clientDB, this.clientBasicInfoSeqCollection);
	}
	
	@Bean
	@Autowired
	public ClientStore persistedClientMongoStore(@Qualifier("clientBasicInfoCol") DBCollection collection
			, @Qualifier("clientBasicInfoSeqCol") DBCollection seqCollection){
		return new PersistedClientMongoStore(collection, seqCollection);
	}
	
	@Bean
	public ValidatorService<Client> clientAccountValidator(ClientStore persistedClientMongoStore) {
		CreateClientAccountValidator ccav = new CreateClientAccountValidator();
		ccav.setPersistedClientMongoStore(persistedClientMongoStore);
		return ccav;
	}
	
	@Bean
	public TransformerService<ClientRegisterForm, Client> clientRegisterFormToClientTransformer(){
		return new ClientRegisterFormToClientTransformer();
	}
	
	@Bean
	@Autowired
	public Processor<ClientRegisterForm, String> createClientAccountProcessor(Processor<Client, Client> createClientAccountProvider,
			ResponseTransFormer<String> jsonResponseTransFormer, TransformerService<ClientRegisterForm, Client> clientRegisterFormToClientTransformer) {
		CreateClientAccountProcessor ccap = new CreateClientAccountProcessor();
		ccap.setClientRegisterFormToClientTransformer(clientRegisterFormToClientTransformer);
		ccap.setCreateClientAccountProvider(createClientAccountProvider);
		ccap.setJsonResponseTransFormer(jsonResponseTransFormer);
		return ccap;
	}
	
	@Bean
	public Processor<Client, Client> createClientAccountProvider(ValidatorService<Client> clientAccountValidator,
			ClientStore persistedClientMongoStore) {
		CreateClientAccountProvider ccap = new CreateClientAccountProvider();
		ccap.setClientAccountValidator(clientAccountValidator);
		ccap.setPersistedClientMongoStore(persistedClientMongoStore);
		return ccap;
	}

}

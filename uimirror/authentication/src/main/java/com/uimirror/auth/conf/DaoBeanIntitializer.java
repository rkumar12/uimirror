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
package com.uimirror.auth.conf;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.uimirror.core.mongo.ConnectionFactory;
import com.uimirror.core.mongo.DBCollectionUtil;
import com.uimirror.core.mongo.MongoDbFactory;

/**
 * Configures all the database beans
 * @author Jay
 */
@Configuration
public class DaoBeanIntitializer {
	
	protected @Value("${mongo.host:127.0.0.1}") String host;
	protected @Value("${mongo.token.host:127.0.0.1}") String tokenHost;
	protected @Value("${auth.db.name:uim_ouath}") String authDb;
	protected @Value("${auth.usr.col.name:usr_auth}") String userAuthCollection;
	protected @Value("${client.db.name:uim_client}") String clientDb;
	protected @Value("${client.col.name:basic_info}") String clientBasicInfoCollection;
	protected @Value("${auth.token.db.name:uim_ouath_token}") String tokenDb;
	protected @Value("${auth.token.col.name:token}") String ouathTokenCol;
	
	@Bean
	public Mongo mongo() throws UnknownHostException{
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(host);
		return cf.getMongoClient();
	}
	
	@Bean
	public Mongo tokenMongo() throws UnknownHostException{
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(tokenHost);
		return cf.getMongoClient();
	}
	
	@Bean
	@Autowired
	public DB authDB(Mongo mongo) throws UnknownHostException{
		return MongoDbFactory.getDB(mongo, this.authDb);
	}
	
	@Bean
	@Autowired
	public DBCollection usrAuthCol(DB authDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(authDB, this.userAuthCollection);
	}
	@Bean
	@Autowired
	public DB tokenDB(Mongo tokenMongo) throws UnknownHostException{
		return MongoDbFactory.getDB(tokenMongo, this.tokenDb);
	}
	
	@Bean
	@Autowired
	public DBCollection tokenOuathCol(DB tokenDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(tokenDB, this.ouathTokenCol);
	}

	@Bean
	@Autowired
	public DB clientDB(Mongo mongo) throws UnknownHostException{
		return MongoDbFactory.getDB(mongo, this.authDb);
	}
	
	@Bean
	@Autowired
	public DBCollection clientBasicInfoCol(DB clientDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(clientDB, this.clientBasicInfoCollection);
	}

}

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
package com.uimirror.account.conf;

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
	protected @Value("${auth.db.name:uim_ouath}") String authDb;
	protected @Value("${auth.usr.col.name:usr_auth}") String userAuthCollection;
	protected @Value("${auth.usr.client.col.name:usr_auth_client}") String userAuthorizedClientCollection;
	protected @Value("${client.db.name:uim_client}") String clientDb;
	protected @Value("${client.col.name:basic_info}") String clientBasicInfoCollection;
	
	@Bean
	public Mongo mongo() throws UnknownHostException{
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(host);
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
	public DBCollection userAuthorizedClientCol(DB authDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(authDB, this.userAuthorizedClientCollection);
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

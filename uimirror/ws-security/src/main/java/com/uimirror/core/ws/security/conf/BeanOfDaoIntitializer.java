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
package com.uimirror.core.ws.security.conf;

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
public class BeanOfDaoIntitializer {
	
	protected @Value("${mongo.token.host:127.0.0.1}") String tokenHost;
	protected @Value("${auth.token.db.name:uim_ouath_token}") String tokenDb;
	protected @Value("${auth.token.col.name:token}") String ouathTokenCol;
	
	@Bean
	public Mongo tokenMongo() throws UnknownHostException{
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(tokenHost);
		return cf.getMongoClient();
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

}

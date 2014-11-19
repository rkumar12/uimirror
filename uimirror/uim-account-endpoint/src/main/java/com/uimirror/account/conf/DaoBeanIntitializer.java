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
import org.springframework.context.annotation.Import;

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
@Import({JobStoreBeanIntitializer.class})
public class DaoBeanIntitializer {
	
	protected @Value("${mongo.host:127.0.0.1}") String host;
	protected @Value("${auth.db.name:uim_ouath}") String authDb;
	protected @Value("${auth.usr.col.name:usr_auth}") String userAuthCollection;
	//For User DB
	protected @Value("${user.db.name:uim_usr}") String userDb;
	//Stores the User Temp Store
	protected @Value("${user.temp.col.name:users}") String userTempCollection;
	//Stores the User Temp Store
	protected @Value("${user.temp.token.col.name:ouath_token}") String userTempTokenCollection;
	//Stores the User Logs only
	protected @Value("${user.logs.col.name:logs}") String userLogsCollection;
	//Stores the User Basic Info only
	protected @Value("${user.basic.col.name:basicinfo}") String userBasicInfoCollection;
	//Stores the User Basic Sequence for the profile id
	protected @Value("${user.seq.col.name:basic_info_seq}") String userBasicInfoSeqCollection;
	//Stores the User Basic details
	protected @Value("${user.basic.details.col.name:details}") String userDetailsCollection;
	protected @Value("${auth.usr.client.col.name:usr_auth_client}") String userAuthorizedClientCollection;
	
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
	public DB userDB(Mongo mongo) throws UnknownHostException{
		return MongoDbFactory.getDB(mongo, this.userDb);
	}
	
	@Bean
	@Autowired
	public DBCollection userTempCol(DB userDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(userDB, this.userTempCollection);
	}
	
	@Bean
	@Autowired
	public DBCollection userTempTokenCol(DB userDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(userDB, this.userTempTokenCollection);
	}
	@Bean
	@Autowired
	public DBCollection userLogsCol(DB userDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(userDB, this.userLogsCollection);
	}

	@Bean
	@Autowired
	public DBCollection userBasicInfoCol(DB userDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(userDB, this.userBasicInfoCollection);
	}
	@Bean
	@Autowired
	public DBCollection userDetailsCol(DB userDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(userDB, this.userDetailsCollection);
	}

	@Bean
	@Autowired
	public DBCollection userBasicInfoSeqCol(DB userDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(userDB, this.userBasicInfoSeqCollection);
	}

}

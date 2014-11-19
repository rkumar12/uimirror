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
import com.uimirror.core.job.store.PersistedSimpleJobMongoStore;
import com.uimirror.core.job.store.SimpleJobStore;
import com.uimirror.core.mongo.DBCollectionUtil;
import com.uimirror.core.mongo.MongoDbFactory;

/**
 * Configures all the database beans for the jobs in account
 * @author Jay
 */
@Configuration
public class JobStoreBeanIntitializer {
	
	protected @Value("${jobs.db.name:uim_jobs}") String jobDb;
	protected @Value("${account.job.col.name:account}") String accountJobCollection;
	protected @Value("${account.job.seq.col.name:account_seq}") String accountJobSeqCollection;
	protected @Value("${account.job.seq.name:account_seq}") String accountJobSeqName;
	
	
	@Bean
	@Autowired
	public DB jobDB(Mongo mongo) throws UnknownHostException{
		return MongoDbFactory.getDB(mongo, this.jobDb);
	}
	
	@Bean
	@Autowired
	public DBCollection accountJobCollection(DB jobDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(jobDB, this.accountJobCollection);
	}
	
	@Bean
	@Autowired
	public DBCollection accountJobSeqCollection(DB jobDB) throws UnknownHostException{
		return DBCollectionUtil.getCollection(jobDB, this.accountJobSeqCollection);
	}
	
	@Bean
	@Autowired
	public SimpleJobStore persistedAccountJobMongoStore(DBCollection accountJobCollection, DBCollection accountJobSeqCollection){
		return new PersistedSimpleJobMongoStore(accountJobCollection, accountJobSeqCollection, accountJobSeqName);
	}

}

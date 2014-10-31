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
package com.uimirror.location.conf;

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
 * Configures location database beans
 * @author Jay
 */
@Configuration
public class DaoBeanIntitializer {
	
	protected @Value("${mongo.host:127.0.0.1}") String host;
	protected @Value("${location.db.name:uim_location}") String locationDb;
	protected @Value("${location.country.col.name:country}") String countryColl;
	protected @Value("${location.loc.col.name:location}") String locationColl;
	protected @Value("${location.country.seq.col.name:country_seq}") String countrySeqColl;
	protected @Value("${location.loc.seq.col.name:location_seq}") String locationSeqColl;
	
	@Bean
	public Mongo mongo() throws UnknownHostException{
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(host);
		return cf.getMongoClient();
	}
	
	@Bean
	@Autowired
	public DB locationDb(Mongo mongo) throws UnknownHostException{
		return MongoDbFactory.getDB(mongo, this.locationDb);
	}
	
	@Bean
	@Autowired
	public DBCollection countryColl(DB locationDb) throws UnknownHostException{
		return DBCollectionUtil.getCollection(locationDb, this.countryColl);
	}
	
	@Bean
	@Autowired
	public DBCollection locationColl(DB locationDb) throws UnknownHostException{
		return DBCollectionUtil.getCollection(locationDb, this.locationColl);
	}

	@Bean
	@Autowired
	public DBCollection countrySeqColl(DB locationDb) throws UnknownHostException{
		return DBCollectionUtil.getCollection(locationDb, this.countrySeqColl);
	}

	@Bean
	@Autowired
	public DBCollection locationSeqColl(DB locationDb) throws UnknownHostException{
		return DBCollectionUtil.getCollection(locationDb, this.locationSeqColl);
	}

}

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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DBCollection;
import com.uimirror.location.store.CountryStore;
import com.uimirror.location.store.LocationStore;
import com.uimirror.location.store.PersistedCountryMongoStore;
import com.uimirror.location.store.PersistedLocationMongoStore;

/**
 * A config for the location stores
 * @author Jay
 */
@Configuration
public class BeanOfStore {

	@Bean
	//TODO make the DB and collection intialize first
	public CountryStore persistedCountryMongoStore(DBCollection countryColl, DBCollection countrySeqColl){
		return new PersistedCountryMongoStore(countryColl, countrySeqColl);
	}
	
	@Bean
	//TODO make the DB and collection intialize first
	public LocationStore persistedLocationMongoStore(DBCollection locationColl, DBCollection locationSeqColl){
		return new PersistedLocationMongoStore(locationColl, locationSeqColl);
	}

}

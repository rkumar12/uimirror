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
package com.uimirror.account.conf.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DBCollection;
import com.uimirror.user.store.AccountTokenStore;
import com.uimirror.user.store.DefaultUserStore;
import com.uimirror.user.store.PersistedAccountTokenMongoStore;
import com.uimirror.user.store.PersistedDefaultUserMongoStore;
import com.uimirror.user.store.PersistedUserAccountLogMongoStore;
import com.uimirror.user.store.PersistedUserBasicDetailsMongoStore;
import com.uimirror.user.store.PersistedUserBasicInfoMongoStore;
import com.uimirror.user.store.PersistedUserCredentialMongoStore;
import com.uimirror.user.store.UserAccountLogStore;
import com.uimirror.user.store.UserBasicDetailsStore;
import com.uimirror.user.store.UserBasicInfoStore;
import com.uimirror.user.store.UserCredentialsStore;

/**
 * Contains the store repo details for the user.
 * @author Jay
 */
@Configuration
public class BeanOfUserStore {

	//****Stores for the User****
	@Bean
	@Autowired
	public DefaultUserStore persistedDefaultUserMongoStore(@Qualifier("userTempCol") DBCollection collection, 
			@Qualifier("userBasicInfoSeqCol") DBCollection seqCollection){
		return new PersistedDefaultUserMongoStore(collection, seqCollection);
	}
	
	@Bean
	@Autowired
	public AccountTokenStore persistedAccountTokenMongoStore(@Qualifier("userTempTokenCol") DBCollection collection){
		return new PersistedAccountTokenMongoStore(collection);
	}
	
	@Bean
	@Autowired
	public UserAccountLogStore persistedUserAccountLogMongoStore(@Qualifier("userLogsCol") DBCollection collection){
		return new PersistedUserAccountLogMongoStore(collection);
	}
	@Bean
	@Autowired
	public UserBasicDetailsStore persistedUserBasicDetailsMongoStore(@Qualifier("userDetailsCol") DBCollection collection){
		return new PersistedUserBasicDetailsMongoStore(collection);
	}
	@Bean
	@Autowired
	public UserBasicInfoStore persistedUserBasicInfoMongoStore(@Qualifier("userBasicInfoCol") DBCollection collection){
		return new PersistedUserBasicInfoMongoStore(collection);
	}
	@Bean
	@Autowired
	public UserCredentialsStore persistedUserCredentialMongoStore(@Qualifier("usrAuthCol") DBCollection collection){
		return new PersistedUserCredentialMongoStore(collection);
	}
	//****Stores for the User end****

}

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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.util.thread.BackgroundProcessor;
import com.uimirror.user.processor.BackGroundCreateMissingUserProcessor;
import com.uimirror.user.processor.BackGroundCreateUserProcessor;
import com.uimirror.user.processor.InvalidateAccountTokenProcessor;
import com.uimirror.user.store.AccountTokenStore;
import com.uimirror.user.store.DefaultUserStore;
import com.uimirror.user.store.UserAccountLogStore;
import com.uimirror.user.store.UserBasicDetailsStore;
import com.uimirror.user.store.UserBasicInfoStore;
import com.uimirror.user.store.UserCredentialsStore;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfUserBackGroundProcessor {
	
	@Bean(name=BackGroundCreateUserProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Autowired
	public BackgroundProcessor<DefaultUser, Object> backGroundCreateUserProcessor(DefaultUserStore persistedDefaultUserMongoStore,
			UserBasicInfoStore persistedUserBasicInfoMongoStore, UserCredentialsStore persistedUserCredentialMongoStore,
			UserBasicDetailsStore persistedUserBasicDetailsMongoStore, UserAccountLogStore persistedUserAccountLogMongoStore){
		BackGroundCreateUserProcessor bgcp = new BackGroundCreateUserProcessor();
		bgcp.setPersistedDefaultUserMongoStore(persistedDefaultUserMongoStore);
		bgcp.setPersistedUserAccountLogMongoStore(persistedUserAccountLogMongoStore);
		bgcp.setPersistedUserBasicDetailsMongoStore(persistedUserBasicDetailsMongoStore);
		bgcp.setPersistedUserBasicInfoMongoStore(persistedUserBasicInfoMongoStore);
		bgcp.setPersistedUserCredentialMongoStore(persistedUserCredentialMongoStore);
		return bgcp;
	}

	@Bean(name=BackGroundCreateMissingUserProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Autowired
	public BackgroundProcessor<DefaultUser, Object> backGroundCreateMissingUserProcessor(DefaultUserStore persistedDefaultUserMongoStore,
			UserBasicInfoStore persistedUserBasicInfoMongoStore, UserCredentialsStore persistedUserCredentialMongoStore, UserBasicDetailsStore persistedUserBasicDetailsMongoStore,
			UserAccountLogStore persistedUserAccountLogMongoStore){
		BackGroundCreateMissingUserProcessor bgp = new BackGroundCreateMissingUserProcessor();
		bgp.setPersistedDefaultUserMongoStore(persistedDefaultUserMongoStore);
		bgp.setPersistedUserAccountLogMongoStore(persistedUserAccountLogMongoStore);
		bgp.setPersistedUserBasicDetailsMongoStore(persistedUserBasicDetailsMongoStore);
		bgp.setPersistedUserBasicInfoMongoStore(persistedUserBasicInfoMongoStore);
		bgp.setPersistedUserCredentialMongoStore(persistedUserCredentialMongoStore);
		return bgp;
	}
	
	@Bean(name=InvalidateAccountTokenProcessor.NAME)
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Autowired
	public BackgroundProcessor<String, Object> invalidateAccountTokenProcessor(AccountTokenStore persistedAccountTokenMongoStore){
		InvalidateAccountTokenProcessor ivatp = new InvalidateAccountTokenProcessor();
		ivatp.setPersistedAccountTokenMongoStore(persistedAccountTokenMongoStore);
		return new InvalidateAccountTokenProcessor();
	}

}

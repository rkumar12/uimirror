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
package com.uimirror.user.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.user.processor.BackGroundCreateMissingUserProcessor;
import com.uimirror.core.user.processor.BackGroundCreateUserProcessor;
import com.uimirror.core.user.processor.InvalidateAccountTokenProcessor;
import com.uimirror.core.user.store.AccountTokenStore;
import com.uimirror.core.user.store.DefaultUserStore;
import com.uimirror.core.user.store.UserAccountLogStore;
import com.uimirror.core.user.store.UserBasicDetailsStore;
import com.uimirror.core.user.store.UserBasicInfoStore;
import com.uimirror.core.user.store.UserCredentialsStore;
import com.uimirror.core.util.thread.BackgroundProcessor;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
public class BeanOfBackGroundProcessor {
	
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

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
package com.uimirror.user.schedular;

import static com.uimirror.core.mongo.BasicMongoOperators.LESSTHANEQUEAL;
import static com.uimirror.core.user.UserAccountLogDBFields.CREATED_ON;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.uimirror.core.auth.TokenType;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.sso.token.store.AccessTokenStore;
import com.uimirror.user.store.DefaultUserStore;

/**
 * A Scheduler that will run on every 5 mins to delete all the token that are expired
 * and 30 mins old
 * @author Jay
 */
public class DeleteOrphanUserScheduler {

	private @Autowired AccessTokenStore persistedAccessTokenMongoStore;
	private @Autowired DefaultUserStore persistedDefaultUserMongoStore;
	private static final Logger LOG = LoggerFactory.getLogger(DeleteOrphanUserScheduler.class);
	
	/**
	 * Delete all the tokens that are not of type {@link TokenType#ACCESS} and expired
	 * or {@link TokenType#ACCESS} and 30 mins old of expired.
	 */
	@Scheduled(fixedDelay=600000, initialDelay=200000)
	public void deleteExpired(){
		LOG.info("[SCHEDULED-START]- Access Token Delete Operation started.");
		List<DefaultUser> users = persistedDefaultUserMongoStore.getUnVerifiedAccountBefore(24*60);
		//TODO process the account deletion and token deletion
		int numberOfRecord = persistedAccessTokenMongoStore.deleteByQuery(buildQuery());
		LOG.info("[SCHEDULED-END]- Access Token Delete Operation completed with {} number deleted.", numberOfRecord);
	}
	
	private Map<String, Object> buildQuery(){
		Map<String, Object> lessThanquery = new WeakHashMap<String, Object>(3);
		lessThanquery.put(LESSTHANEQUEAL, DateTimeUtil.minusToCurrentUTCTimeConvertToEpoch(24*60));
		Map<String, Object> query = new WeakHashMap<String, Object>();
		query.put(CREATED_ON, query);
		LOG.debug("Final Query {}",query);
		return query;
	}

}

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
package com.uimirror.account.auth.scedular;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.uimirror.account.auth.dao.AccessTokenStore;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.AccessTokenFields;
import com.uimirror.core.mongo.BasicMongoOperators;
import com.uimirror.core.util.DateTimeUtil;

/**
 * A Scheduler that will run on every 5 mins to delete all the token that are expired
 * and 30 mins old
 * @author Jay
 */
public class TokenDeleteScheduler {

	private @Autowired AccessTokenStore persistedAccessTokenMongoStore;
	private static final Logger LOG = LoggerFactory.getLogger(TokenDeleteScheduler.class);
	
	/**
	 * Delete all the tokens that are not of type {@link TokenType#ACCESS} and expired
	 * or {@link TokenType#ACCESS} and 30 mins old of expired.
	 */
	@Scheduled(fixedDelay=600000, initialDelay=200000)
	public void deleteExpired(){
		LOG.info("[SCHEDULED-START]- Access Token Delete Operation started.");
		int numberOfRecord = persistedAccessTokenMongoStore.deleteByQuery(buildQuery());
		LOG.info("[SCHEDULED-END]- Access Token Delete Operation completed with {} number deleted.", numberOfRecord);
	}
	
	private Map<String, Object> buildQuery(){
		Map<String, Object> exp1 = new LinkedHashMap<String, Object>();
		exp1.put(AccessTokenFields.TYPE, TokenType.ACCESS.getTokenType());
		Map<String, Object> timeFiledQuery = new LinkedHashMap<String, Object>();
		timeFiledQuery.put(BasicMongoOperators.LESSTHANEQUEAL, DateTimeUtil.minusToCurrentUTCTimeConvertToEpoch(30));
		exp1.put(AccessTokenFields.AUTH_TKN_EXPIRES, timeFiledQuery);
		
		Map<String, Object> exp2 = new LinkedHashMap<String, Object>();
		Map<String, Object> exp2NotToken = new LinkedHashMap<String, Object>();
		exp2NotToken.put(BasicMongoOperators.NOT_EQUAL, TokenType.ACCESS.getTokenType());
		exp2.put(AccessTokenFields.TYPE, exp2NotToken);
		Map<String, Object> exp2TimeQuery = new LinkedHashMap<String, Object>();
		exp2TimeQuery.put(BasicMongoOperators.LESSTHANEQUEAL, DateTimeUtil.minusToCurrentUTCTimeConvertToEpoch(5));
		exp2TimeQuery.put(BasicMongoOperators.GREATERTHAN, 0l);
		exp2.put(AccessTokenFields.AUTH_TKN_EXPIRES, exp2TimeQuery);
		
		List<Map<String, Object>> expressions = new ArrayList<Map<String, Object>>(); 
		expressions.add(exp1);
		expressions.add(exp2);
		Map<String, Object> orQuery = new LinkedHashMap<String, Object>();
		orQuery.put(BasicMongoOperators.OR, expressions);
		LOG.debug("Final Query {}",orQuery);
		return orQuery;
	}

}

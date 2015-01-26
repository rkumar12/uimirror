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
package com.uimirror.user;

import static com.uimirror.core.mongo.feature.BasicDBFields.ID;
import static com.uimirror.core.shop.UserAccountLogDBFields.CREATED_ON;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.WeakHashMap;

import org.junit.Before;
import org.junit.Test;

import com.uimirror.core.util.DateTimeUtil;

/**
 * @author Jay
 */
public class AccountLogsTest {
	
	private AccountLogs logs;
	private AccountLogs logsWithProfileId;
	private Map<String, Object> res;
	private Map<String, Object> resWithProfileId;
	
	@Before
	public void setUp(){
		logs = new AccountLogs.LogBuilder(null).updatedCreatedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).build();
		logsWithProfileId = new AccountLogs.LogBuilder("123").updatedCreatedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).build();
		res = new WeakHashMap<String, Object>();
		res.put(CREATED_ON, logs.getCreatedOn());
		resWithProfileId = new WeakHashMap<String, Object>();
		resWithProfileId.put(CREATED_ON, logsWithProfileId.getCreatedOn());
		resWithProfileId.put(ID, logsWithProfileId.getProfileId());
	}

	@Test
	public void testSerailize() {
		assertThat(logs.serailize()).isEqualTo(res);
		assertThat(logsWithProfileId.serailize()).isEqualTo(resWithProfileId);
	}

	@Test
	public void testDeSerailize() {
		assertThat(logs.readFromMap(res).toString()).isEqualTo(logs.toString());
		assertThat(logs.readFromMap(resWithProfileId).toString()).isEqualTo(logsWithProfileId.toString());
	}

}

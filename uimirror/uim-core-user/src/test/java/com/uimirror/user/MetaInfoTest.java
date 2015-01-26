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

import static com.uimirror.core.shop.MetaInfoDBFields.CURRENCY;
import static com.uimirror.core.shop.MetaInfoDBFields.LOCALE;
import static com.uimirror.core.shop.MetaInfoDBFields.TIMEZONE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.WeakHashMap;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jay
 */
public class MetaInfoTest {

	private MetaInfo info;
	private MetaInfo info1;
	private MetaInfo info2;
	
	private Map<String, Object> rs;
	private Map<String, Object> rs1;
	private Map<String, Object> rs2;
	
	@Before
	public void setUp(){
		info = new MetaInfo.MetaBuilder("America/Los_Angeles").addCountryCode("US").addLang("en").build();
		info1 = new MetaInfo.MetaBuilder("Asia/Calcutta").addCountryCode("IN").addLang("en").build();
		info2 = new MetaInfo.MetaBuilder("Asia/Test").addCountryCode("IN").addLang("en").build();
		rs = new WeakHashMap<String, Object>(8);
		rs.put(LOCALE, info.getLocale());
		rs.put(CURRENCY, info.getCurrency());
		rs.put(TIMEZONE, info.getTimeZone());
		rs1 = new WeakHashMap<String, Object>();
		rs1.put(LOCALE, "en_IN");
		rs1.put(CURRENCY, "INR");
		rs1.put(TIMEZONE, info1.getTimeZone());
		rs2 = new WeakHashMap<String, Object>();
		rs2.put(LOCALE, "en_IN");
		rs2.put(CURRENCY, "INR");
		rs2.put(TIMEZONE, info2.getTimeZone());
	}

	@Test
	public void serializeTest(){
		assertThat(info.toMap()).isEqualTo(rs);
		assertThat(info1.toMap()).isEqualTo(rs1);
		assertThat(info2.toMap()).isEqualTo(rs2);
	}

	@Test
	public void deSerializeTest(){
		assertThat(MetaInfo.initFromMap(rs).toString()).isEqualTo(info.toString());
		assertThat(MetaInfo.initFromMap(rs1).toString()).isEqualTo(info1.toString());
		assertThat(MetaInfo.initFromMap(rs2).toString()).isEqualTo(info2.toString());
	}
	
	
}

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
package com.uimirror.core;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import java.util.WeakHashMap;

import org.junit.Test;

import com.uimirror.core.user.MetaInfo;
import com.uimirror.core.user.UserDBFields;

/**
 * @author Jay
 */
public class MetaInfoTest {

	@Test
	public void serailizeTest() {
		MetaInfo info = new MetaInfo.MetaBuilder("en_UK").addCurrency("GB").addTimeZone("UTC").build();
		Map<String, Object> rs = new WeakHashMap<String, Object>();
		rs.put(UserDBFields.LOCALE, "en_UK");
		rs.put(UserDBFields.CURRENCY, "GB");
		rs.put(UserDBFields.TIMEZONE, "UTC");
		assertThat(info.toMap()).isEqualTo(rs);
	}
	
	@Test
	public void deSerailizeTest() {
		Map<String, Object> rs = new WeakHashMap<String, Object>();
		rs.put(UserDBFields.LOCALE, "en_UK");
		rs.put(UserDBFields.CURRENCY, "GB");
		rs.put(UserDBFields.TIMEZONE, "UTC");
		Map<String, Object> input = new WeakHashMap<String, Object>();
		input.put(UserDBFields.META_INFO, rs);
		MetaInfo info = MetaInfo.initFromMap(input);
		assertThat(info.toMap()).isEqualTo(rs);
	}

}

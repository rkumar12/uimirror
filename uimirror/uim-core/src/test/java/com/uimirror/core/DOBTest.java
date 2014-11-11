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

import com.uimirror.core.user.UserDBFields;

/**
 * @author Jay
 */
public class DOBTest {

	@Test
	public void serailizeTest() {
		DOB dob = new DOB.DOBBuilder("1988-03-12").addFormat("MMM dd uuuu").addLocale("en_US").build();
		Map<String, Object> res = new WeakHashMap<String, Object>();
		res.put(UserDBFields.DATE_OF_BIRTH, dob.getDob());
		res.put(UserDBFields.DOB_FORMAT, dob.getFormat());
		assertThat(dob.toMap()).isInstanceOf(Map.class).isEqualTo(res);
		
	}
	
	@Test
	public void deserailizeTest() {
		Map<String, Object> meta = new WeakHashMap<String, Object>();
		meta.put(UserDBFields.LOCALE, "en_UK");
		Map<String, Object> dobMap = new WeakHashMap<String, Object>();
		dobMap.put(UserDBFields.DATE_OF_BIRTH, "1988-03-18");
		dobMap.put(UserDBFields.DOB_FORMAT, "dd-MMM-uuuu");
		Map<String, Object> input = new WeakHashMap<String, Object>();
		input.put(UserDBFields.DATE_OF_BIRTH, dobMap);
		input.put(UserDBFields.META_INFO, meta);
		DOB dob = DOB.initFromMap(input);
		Map<String, Object> res = new WeakHashMap<String, Object>();
		res.put(UserDBFields.DATE_OF_BIRTH, dob.getDob());
		res.put(UserDBFields.DOB_FORMAT, dob.getFormat());
		assertThat(dob).isNotNull();
		assertThat(dob.toMap()).isInstanceOf(Map.class).isEqualTo(res);
		assertThat(dob.getDate()).isEqualTo(18);
		assertThat(dob.getMonth()).isEqualTo(03);
		assertThat(dob.getYear()).isEqualTo(1988);
		assertThat(dob.getFormatedDob()).isEqualTo("18-Mar-1988");
		
	}

}

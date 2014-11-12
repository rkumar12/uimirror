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

import static com.uimirror.core.user.DOBDBFields.DOB_FORMAT;
import static com.uimirror.core.user.MetaInfoDBFields.LOCALE;
import static com.uimirror.core.user.UserDBFields.DATE_OF_BIRTH;
import static com.uimirror.core.user.UserDBFields.META_INFO;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.WeakHashMap;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jay
 */
public class DOBTest {
	private DOB dob;
	private Map<String, Object> res;
	private Map<String, Object> meta;
	private Map<String, Object> dobMap = new WeakHashMap<String, Object>();
	private Map<String, Object> input = new WeakHashMap<String, Object>();
	
	@Before
	public void setUp(){
		dob = new DOB.DOBBuilder("1988-03-12").addFormat("MMM dd uuuu").addLocale("en_US").build();
		res = new WeakHashMap<String, Object>();
		res.put(DATE_OF_BIRTH, dob.getDob());
		res.put(DOB_FORMAT, dob.getFormat());
		meta = new WeakHashMap<String, Object>();
		meta.put(LOCALE, "en_UK");
	}

	@Test
	public void serailizeTest() {
		assertThat(dob.toMap()).isInstanceOf(Map.class).isEqualTo(res);
	}
	
	@Test
	public void deserailizeTest() {
		dobMap.put(DATE_OF_BIRTH, "1988-03-18");
		dobMap.put(DOB_FORMAT, "dd-MMM-uuuu");
		input.put(DATE_OF_BIRTH, dobMap);
		input.put(META_INFO, meta);
		DOB dob = DOB.initFromMap(input);
		Map<String, Object> res = new WeakHashMap<String, Object>();
		res.put(DATE_OF_BIRTH, dob.getDob());
		res.put(DOB_FORMAT, dob.getFormat());
		assertThat(dob).isNotNull();
		assertThat(dob.toMap()).isInstanceOf(Map.class).isEqualTo(res);
		assertThat(dob.getDate()).isEqualTo(18);
		assertThat(dob.getMonth()).isEqualTo(03);
		assertThat(dob.getYear()).isEqualTo(1988);
		assertThat(dob.getFormatedDob()).isEqualTo("18-Mar-1988");
	}
	
	@Test
	public void eighteenAgeTest(){
		DOB dob = new DOB.DOBBuilder("1988-03-12").addFormat("MMM dd uuuu").addLocale("en_US").build();
		DOB dob1 = new DOB.DOBBuilder("2010-03-12").addFormat("MMM dd uuuu").addLocale("en_US").build();
		assertThat(dob.isMoreThanighteen()).isTrue();
		assertThat(dob1.isMoreThanighteen()).isFalse();
	}

}

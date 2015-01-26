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

import static com.uimirror.core.shop.UserDBFields.DATE_OF_BIRTH;
import static com.uimirror.core.shop.UserDBFields.META_INFO;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.WeakHashMap;

import org.junit.Before;
import org.junit.Test;

import com.uimirror.core.DOB;

/**
 * @author Jay
 */
public class BasicDetailsTest {
	
	private MetaInfo info = new MetaInfo.MetaBuilder("America/Los_Angeles").addCountryCode("US").addLang("en").build();
	private DOB dob = new DOB.DOBBuilder("1988-03-18").build();
	private BasicDetails dt = new BasicDetails.BasicDetailsBuilder(null).updateMetaInfo(info).build();
	private Map<String, Object> op = new WeakHashMap<String, Object>();
	
	@Before
	public void setUp(){
		op.put(DATE_OF_BIRTH, dob.toMap());
		op.put(META_INFO, info.toMap());
	}
	@Test
	public void seralizeTest() {
		assertThat(dt.writeToMap()).isEqualTo(op);
	}
	@Test
	public void deSeralizeTest() {
		assertThat(dt.readFromMap(op).toString()).isEqualTo(dt.toString());
	}
	
	

}

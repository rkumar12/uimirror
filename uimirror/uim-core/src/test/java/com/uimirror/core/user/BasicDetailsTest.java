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
package com.uimirror.core.user;

import static com.uimirror.core.user.UserDBFields.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import java.util.WeakHashMap;

import org.junit.Before;
import org.junit.Test;

import com.uimirror.core.DOB;

/**
 * @author Jay
 */
public class BasicDetailsTest {

	private MetaInfo info = new MetaInfo.MetaBuilder("en_UK").addCurrency("GB").addTimeZone("UTC").build();
	private DOB dob = new DOB.DOBBuilder("1988-03-18").build();
	private BasicDetails dt = new BasicDetails.BasicDetailsBuilder(null).updateDOB(dob).updateMetaInfo(info).build();
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

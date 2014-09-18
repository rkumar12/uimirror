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
package com.uimirror.core.util;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jay
 */
public class BeanToMapTest {
	static final String INPUT = "in";
	static final String OUTPUT = "out";

	@SuppressWarnings("rawtypes")
	@Test
	public void testToMap() {
		
		final String ISVALID = "isValid";
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataSets = (List<Map<String, Object>>)LoadExternalJson.loadData("com/uimirror/core/util/bean_to_map_util_test_data_set_1.json", List.class);
		TestBean test = null;
		for(Map<String, Object> data : dataSets){
			test = new TestBean();
			BeanToMap.fromMap((Map)data.get(INPUT), test);
			
			if((boolean)data.getOrDefault(ISVALID, Boolean.FALSE)){
				Assert.assertEquals(data.get(OUTPUT), BeanToMap.toMap(test));
			}else{
				Assert.assertNotSame(data.get(OUTPUT), BeanToMap.toMap(test));
			}
		}
	}
	
	public class TestBean{
		private String name;
		private String age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
		
	}

}

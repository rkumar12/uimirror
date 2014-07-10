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
package com.uimirror.util.web;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.uimirror.util.resource.LoadExternalGson;

/**
 * <p>Test Cases for all the web util api
 * @author Jayaram
 */
public class WebUtilTest {
	static final String INPUT = "in";
	static final String OUTPUT = "out";

	/**
	 * <p>A test case for {@link WebUtil#getUrl(String)} based on the input from web_util_test_data_set_1.json
	 */
	@Test
	public void testGetUrl() {
		final String ISVALID = "isValid";
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataSets = (List<Map<String, Object>>)LoadExternalGson.loadData("com/uimirror/util/web/web_util_test_data_set_1.json", List.class);
		for(Map<String, Object> data : dataSets){
			if((boolean)data.getOrDefault(ISVALID, Boolean.FALSE)){
				Assert.assertEquals(data.get(OUTPUT).toString(), WebUtil.getUrl(data.get(INPUT) != null ? data.get(INPUT).toString() : null).toString());
			}else{
				try{
					WebUtil.getUrl(data.get(INPUT) != null ? data.get(INPUT).toString() : null);
				}catch(IllegalArgumentException e){
					Assert.assertEquals(data.get(OUTPUT).toString(), e.getMessage());
				}
			}
		}
	}
	
	@Test
	public void testIsValidUrl() {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataSets = (List<Map<String, Object>>)LoadExternalGson.loadData("com/uimirror/util/web/web_util_test_data_set_2.json", List.class);
		for(Map<String, Object> data : dataSets){
			Assert.assertEquals((boolean)data.get(OUTPUT), WebUtil.isValidUrl(data.get(INPUT) != null ? data.get(INPUT).toString() : null));
		}
	}

}

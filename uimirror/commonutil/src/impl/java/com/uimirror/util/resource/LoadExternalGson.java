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
package com.uimirror.util.resource;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.uimirror.util.Constants;

/**
 * <p>Utility class to deal with all the external loading of resource
 * 
 * @author Jayaram
 *
 */
public class LoadExternalGson{

	/**
	 * <p>Load test data for unit testing.
	 * @param fullPath
	 * @param clazz
	 * @return
	 */
	public static Object loadData(String fullPath, Class<? extends Object> clazz){
    	Gson gson = new Gson();
		return gson.fromJson(loadFileFromClassPath(fullPath), clazz);
	}

	/**
	 * <p>Loads file from class path.
	 * @param fullPath
	 * @return
	 */
	public static String loadFileFromClassPath(String fullPath){
		StringWriter wrtr = new StringWriter();
    	try {
			IOUtils.copy(LoadExternalGson.class.getClassLoader().getResourceAsStream(fullPath), wrtr, Constants.UTF_8);
		} catch (IOException e) {
			throw new IllegalArgumentException("File Not Found");
		}
    	return wrtr.toString();
	}
}

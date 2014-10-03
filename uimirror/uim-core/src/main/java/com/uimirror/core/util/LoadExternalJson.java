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

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.owlike.genson.Genson;
import com.uimirror.core.Constants;

/**
 * <p>Utility class to deal with all the external loading of resource
 * 
 * @author Jayaram
 *
 */
public class LoadExternalJson{

	private static final Logger LOG = LoggerFactory.getLogger(LoadExternalJson.class);
	/**
	 * <p>Load test data for unit testing.
	 * @param fullPath
	 * @param clazz
	 * @return
	 */
	public static Object loadData(String fullPath, Class<? extends Object> clazz){
		return new Genson().deserialize(loadFileFromClassPath(fullPath), clazz);
	}

	/**
	 * <p>Loads file from class path.
	 * @param fullPath
	 * @return
	 */
	public static String loadFileFromClassPath(String fullPath){
		StringWriter wrtr = new StringWriter();
    	try {
			IOUtils.copy(LoadExternalJson.class.getClassLoader().getResourceAsStream(fullPath)	, wrtr, Constants.UTF_8);
		} catch (IOException e) {
			LOG.error("[ERROR]- File Not found {}", e);
			throw new IllegalArgumentException("File Not Found");
		}
    	return wrtr.toString();
	}
}

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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * <p>This has all the web/url releated utils
 * @author Jayaram
 */
public class WebUtil {

    /**
     * <p>Converts a String into URL.</p>
     *
     * <pre>
     * WebUtil.getUrl(null)      = <code>IllegalArgumentException("URL can't be formed, Correct the input")</code>
     * </pre>
     *
     * @param spec the String to convert to java.net.URL
     * @return <code> {@link URL}</code> if the String is a valid url
     * @throws <code> {@link IllegalArgumentException}</code> in case string is empty, null, blank or malformed
     * @since 1.0
     */
	public static URL getUrl(String spec){
		if(StringUtils.isBlank(spec)){
			throw new IllegalArgumentException("URL can't be formed, Correct the input");
		}
		try {
	        return new URL(spec);
	    } catch (MalformedURLException e) {
	        throw new IllegalArgumentException("URL can't be formed, url string is mailformed");
	    }
	}
	
	/**
	 * <p>This will test if the provided string is a valid url or not</p>
	 * Check Out <code> {@link WebUtil#getUrl(String)}</code> for the url checking rules
	 * @param spec
	 * @return <code>true</code> if the string is a valid url else <code>false</code>
	 */
	public static boolean isValidUrl(String spec){
		try{
			getUrl(spec);
			return Boolean.TRUE;
		}catch(IllegalArgumentException e){
			return Boolean.FALSE;
		}
	}
}

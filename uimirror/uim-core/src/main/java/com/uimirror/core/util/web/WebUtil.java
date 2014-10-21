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
package com.uimirror.core.util.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * <p>This has all the web/url releated utils
 * @author Jayaram
 */
public class WebUtil {

	private static final Pattern URL_PATTERN = Pattern.compile("(htt[p|ps])(://)(.[^/]+)");
	private static final Pattern LOCAL_HOST_PATTERN = Pattern.compile("(localhost|127.0.0.1)");
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
		if(!StringUtils.hasText(spec)){
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
	
	/**
	 * Find a Domain name from the URL
	 * i.e for http://account.uimirror.com/abc it will extract http://account.uimirror.com
	 * @param url
	 * @return
	 */
	public static String getURLDomain(String url){
		Matcher m = URL_PATTERN.matcher(url);
		if(m.find())
			return m.group(0);
		return null;
	}
	
	/**
	 * Checks if the URL is a local host
	 * @param url
	 * @return
	 */
	public static boolean isLoaclHostURL(String url){
		Matcher m = LOCAL_HOST_PATTERN.matcher(url);
		return m.find();
	}
	
	/**
	 * Checks if the app URL and redirect URL are same or not
	 * @param appURL
	 * @param redirectURL
	 * @return
	 */
	public static boolean isValidAppAndRedirectURL(String appURL, String redirectURL){
		if(!StringUtils.hasText(appURL))
			return Boolean.FALSE;
		String url1 = getURLDomain(appURL);
		if(url1 != null && !isLoaclHostURL(url1)){
			return url1.equals(getURLDomain(redirectURL));
		}
		return Boolean.FALSE;
	}
}

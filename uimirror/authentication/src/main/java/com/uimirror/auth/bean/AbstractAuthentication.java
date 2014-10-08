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
package com.uimirror.auth.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import com.uimirror.core.Parameters;

/**
 * Base class for <code>Authentication</code> objects.
 * <p>
 * Implementations which use this class should be immutable.
 * @author Jay
 */
public abstract class AbstractAuthentication implements Authentication{

	private static final long serialVersionUID = 2022436764391298527L;

	private Object details;
	private boolean authenticated;
	
	/**
	 * Initialize the basic ip and user Agent map
	 * @param ip
	 * @param userAgent
	 */
	public AbstractAuthentication(String ip, String userAgent) {
		initClientMetaDetails(ip, userAgent);
	}
	public AbstractAuthentication(){
		//NOOP
	}
	
	private void initClientMetaDetails(String ip, String userAgent){
		Map<String, Object> details = new LinkedHashMap<String, Object>(5);
		details.put(Parameters.IP, ip);
		details.put(Parameters.USER_AGENT, userAgent);
		setDetails(details);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.Authentication#getDetails()
	 */
	@Override
	public Object getDetails() {
		return details == null ? new LinkedHashMap<String, Object>(5) : details;
	}
	
	public void setDetails(Object details) {
        this.details = details;
    }

	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.Authentication#isAuthenticated()
	 */
	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.Authentication#setAuthenticated(boolean)
	 */
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}


}

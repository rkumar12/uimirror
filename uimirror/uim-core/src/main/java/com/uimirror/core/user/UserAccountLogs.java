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

import java.util.Map;

/**
 * @author Jay
 */
public interface UserAccountLogs {

	/**
	 * @param raw
	 * @return
	 */
	UserAccountLogs initFromMap(Map<String, Object> raw);
	
	/**
	 * @return
	 */
	Map<String, Object> toMap();
	
	/**
	 * @return
	 */
	long getCreatedOn();
	
	/**
	 * @return
	 */
	long getModifiedOn();
	
	/**
	 * @return
	 */
	Map<String, Object> getDetails();
	
	/**
	 * @return
	 */
	String getProfileId();
	
}

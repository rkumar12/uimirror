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
package com.uimirror.core.service;

/**
 * Common Matcher service which will check whether the source is === with destination or not
 * @author Jay
 */
public interface MatcherService<S, D> {
	
	/**
	 * <p>This will match with source and destination</p>
	 * @param src which will be matched
	 * @param des with which matching will happen
	 * @return <code>true</code> if matched else <code>false</code>
	 */
	boolean isMatching(S src, D des);
}

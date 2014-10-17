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
package com.uimirror.core.util.thread;

/**
 * The Service locator factory pattern implementation of the
 * back ground jobs.
 * 
 * Please note this bean and managed bean should be a prototype
 * bean
 * @author Jay
 */
public interface BackgroundProcessorFactory<P,R> {

	/**
	 * Factory container for the background processors
	 * @param name
	 * @return
	 */
	BackgroundProcessor<P, R> getProcessor(String name);
}

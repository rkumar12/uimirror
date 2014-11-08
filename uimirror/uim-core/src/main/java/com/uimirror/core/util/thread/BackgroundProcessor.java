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
 * Bridge between the service end point and application service response 
 * @author Jay
 */
public interface BackgroundProcessor<P, R> {

	/**
	 * This will process the defined type of parameters and run the business logic 
	 * This will not return any response to the caller. in background
	 * @param param which will be invoked
	 */
	void invoke(P param);
	
	/**
	 * This will get the result of the submitted tasks
	 * @return the result
	 * @throws IllegalThreadStateException if no result found
	 */
	R getResult() throws IllegalThreadStateException;

}

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
package com.uimirror.core;

import com.uimirror.core.rest.extra.ApplicationException;

/**
 * Bridge between the service end point and application service response 
 * @author Jay
 */
public interface Processor<P, R> {

	/**
	 * This will process the defined type of parameters and run the business logic to 
	 * generate a response that will be send to the user
	 * @param param which will be processed
	 * @return R response from the processed parameters
	 * @throws ApplicationException in case of any exception during process
	 */
	R invoke(P param) throws ApplicationException;

}

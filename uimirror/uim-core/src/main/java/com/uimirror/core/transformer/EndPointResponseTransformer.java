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
package com.uimirror.core.transformer;

/**
 * Common contract which defines way from which response needs to be convert and send back to the caller.
 * @author Jay
 */
public interface EndPointResponseTransformer<R, P> {
	
	/**
	 * <p>This transforms the given type into the desired type</p>
	 * If this operation ends up with any exception should return the default exception message  
	 * @param result
	 * @return
	 */
	R doTransform(P result);

}

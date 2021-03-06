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

/**
 * Builder for the constructor to builder pattern.
 * Strategic pattern implementation, every class which needs to provide the builder
 * pattern needs to implement this.
 * 
 * @author Jay
 */
public interface Builder<T> {

	public T build();
}

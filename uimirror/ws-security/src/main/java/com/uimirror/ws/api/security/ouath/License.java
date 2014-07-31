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
package com.uimirror.ws.api.security.ouath;


/**
 * Represents the common interface for the license.
 * @author Jay
 *
 */
public interface License {

	/**
	 * Gets the license Id of this license
	 * @return a int id for this license.
	 */
	public int getLicenseId();
	
	/**
     * Compares this License to the specified object.  Returns true
     * if the object passed in matches the license represented by
     * the implementation of this interface.
     *
     * @param another License to compare with.
     *
     * @return true if the license passed in is the same as that
     * encapsulated by this license, and false otherwise.
     */
    public boolean equals(Object another);

    /**
     * Returns a string representation of this license.
     *
     * @return a string representation of this license.
     */
    public String toString();

    /**
     * Returns a hashcode for this license.
     *
     * @return a hashcode for this license.
     */
    public int hashCode();
}

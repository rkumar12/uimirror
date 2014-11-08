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
package com.uimirror.core.crypto;

/**
 * Matches the specified encrypted string with the raw string
 * first it it gets the crypto definition, using the defination it encrypts the raw string 
 * and tries to match with the original.
 * 
 * @author Jay
 */
public interface CryptoMatcherService {

	/**
	 * Matches the provided parameters using the crypto definition mentioned in the strategy.
	 * @param raw which be encrypted
	 * @param encrypted which will be decrypted
	 * @param startegy policy
	 * @return <code>true</code> if matches
	 */
	public boolean matchByEncrypting(final String raw, final String encrypted, final String startegy);
}

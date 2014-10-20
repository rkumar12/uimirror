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
package com.uimirror.account.auth.core;

import org.springframework.util.Assert;

import com.uimirror.core.crypto.CryptoMatcherService;

/**
 * <p>This will take the user entered password and user's original password </p>
 * Tries to de-crypt and matches both, if it matches then return <code>true</code> else <code>false</code> 
 * 
 * @author Jay
 */
public class PasswordMatcher {
	
	private final CryptoMatcherService cryptoMatcherService;
	
	public PasswordMatcher(CryptoMatcherService matcherService) {
		Assert.notNull(matcherService, "Matcher Service Can't be Empty");
		this.cryptoMatcherService = matcherService;
	}

	/**
	 * <p>This will match the raw password with the password saved in the data base
	 * with the strategy specified</p>
	 * 
	 * @param rawPassword
	 * @param originalPassword
	 * @param encryptionStartegy
	 * @return
	 */
	public boolean match(final String rawPassword, final String originalPassword, final String encryptionStartegy){
		Assert.hasText(rawPassword, "Password to match is invalid");
		Assert.hasText(originalPassword, "Password with match is invalid");
		return cryptoMatcherService.matchByEncrypting(rawPassword, originalPassword, encryptionStartegy);
	}
}

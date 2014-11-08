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

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * implement the logic of matching raw key with the encrypted string
 * 
 * @author Jay
 */
public class MatcherServiceImpl implements CryptoMatcherService{
	
	private static final Logger LOG = LoggerFactory.getLogger(MatcherServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.uimirror.core.crypto.MatcherService#match(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean matchByEncrypting(String raw, String encrypted, String startegy) {
		CryptoDefination cryptoDefination = getCryptoDefination(startegy);
		return match(cryptoDefination, raw, encrypted);
	}
	
	/**
	 * If strategy is specified then it get instance using the key else with default key
	 * 
	 * @param startegy
	 * @return {@link CryptoDefination} from the startegy
	 */
	private CryptoDefination getCryptoDefination(final String startegy){
		CryptoDefination cryptoDefination = null;
		if(StringUtils.hasText(startegy)){
			cryptoDefination = new PBEWithMD5AndDESCrypto(startegy);
		}else{
			cryptoDefination = new PBEWithMD5AndDESCrypto();
		}
		return cryptoDefination;
	}
	
	/**
	 * Encrypts the raw with the crypto definition and matches with the original.
	 * 
	 * @param cryptoDefination
	 * @param raw
	 * @param encrypted
	 * @return <code>true</code> if matches
	 */
	private boolean match(final CryptoDefination cryptoDefination , final String raw, final String encrypted){
		boolean matched = Boolean.FALSE;
		try {
			if(cryptoDefination.encrypt(raw).equals(encrypted)){
				matched = Boolean.TRUE;
			}
		} catch (UnsupportedEncodingException | GeneralSecurityException | NullPointerException e) {
			LOG.error("[URGENT-ERROR]- encrypting raw and matching with the original caused some issue {}", e);
		}
		return matched;
	}

}

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
package com.uimirror.ws.account.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.uimirror.core.RandomKeyGenerator;
import com.uimirror.core.auth.Token;
import com.uimirror.core.crypto.CryptoDefination;
import com.uimirror.core.crypto.PBEWithMD5AndDESCrypto;

/**
 * A password Filed which might have different para phrase for the different user.
 * 
 * @author Jay
 */
public class Password {

	private final String password;
	private final String paraphrase;
	private static final Logger LOG = LoggerFactory.getLogger(Token.class);

	/**
	 * if the paraphrase specified in the constructor is null, it will generate a new one
	 * @param password
	 * @param paraphrase
	 */
	public Password(String password, String paraphrase) {
		this.password = password;
		this.paraphrase = paraphrase == null ? RandomKeyGenerator.randomString(5) : paraphrase;
	}
	
	public Password(String password){
		this.paraphrase = null;
		this.password = password;
	}
	
	/**
	 * Tries to encrypt the password, if in case of any issues, returns the original
	 * @return
	 */
	public Password getEncrypted(){
		try {
			return new Password(getCryptoDefination().encrypt(this.password), this.paraphrase);
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			LOG.error("[LOW-PASSWORD-ENCRYPT]- Encrypting Password has some issues.");
		}
		return this;
	}
	
	/**
	 * Tries to decrypt the password, if in case of any issues, return the original
	 * @return
	 */
	public Password getDecrypted(){
		try {
			return new Password(getCryptoDefination().decrypt(password), this.paraphrase);
		} catch (GeneralSecurityException | IOException e) {
			LOG.error("[LOW-PASSWORD-ENCRYPT]- Decrypting Password has some issues.");
		}
		return this;
	}
	
	/**
	 * Gets the Crypto Definitions
	 * @return
	 */
	private CryptoDefination getCryptoDefination(){
		CryptoDefination cd = null;
		if(StringUtils.hasText(paraphrase)){
			cd = new PBEWithMD5AndDESCrypto(paraphrase);
		}else{
			cd = new PBEWithMD5AndDESCrypto();
		}
		return cd;
	}

	public String getPassword() {
		return password;
	}

	public String getParaphrase() {
		return paraphrase;
	}

}

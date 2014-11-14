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
package com.uimirror.core.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.uimirror.core.crypto.CryptoDefination;
import com.uimirror.core.crypto.PBEWithMD5AndDESCrypto;

/**
 * Represents the token and its encryption/decyption strategy
 * 
 * @author Jay
 */
public class Token {

	private final String token;
	private final String paraphrase;
	
	private static final Logger LOG = LoggerFactory.getLogger(Token.class);
	
	public Token(String token, String paraphrase) {
		Assert.hasText(token, "AccessToken Can't be empty");
		this.token = token;
		this.paraphrase = paraphrase;
	}
	
	/**
	 * Tries to encrypt the token, if in case of any issues, returns the original
	 * @return {@link Token}
	 */
	public Token getEncrypted(){
		try {
			return new Token(getCryptoDefination().encrypt(this.token), this.paraphrase);
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			LOG.error("[LOW-TOKEN]- Encrypting token has some issues {}", e);
		}
		return this;
	}
	
	/**
	 * Tries to decrypt the token, if in case of any issues, return the original
	 * @return {@link Token}
	 */
	public Token getDecrypted(){
		try {
			return new Token(getCryptoDefination().decrypt(token), this.paraphrase);
		} catch (GeneralSecurityException | IOException e) {
			LOG.error("[LOW-TOKEN]- Decrypting token has some issues {}", e);
		}
		return this;
	}
	
	/**
	 * Gets the Crypto Definitions
	 * @return {@link CryptoDefination}
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

	public String getToken() {
		return token;
	}

	public String getParaphrase() {
		return paraphrase;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((paraphrase == null) ? 0 : paraphrase.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (paraphrase == null) {
			if (other.paraphrase != null)
				return false;
		} else if (!paraphrase.equals(other.paraphrase))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Token [token=" + token + ", paraphrase=" + paraphrase + "]";
	}
	
}

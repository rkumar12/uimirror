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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.springframework.util.StringUtils;

import com.uimirror.core.Constants;

/**
 * <p>This is a implem</p>
 * @author Jay
 */
public final class PBEWithMD5AndDESCrypto implements CryptoDefination{

	private final char[] PASSWORD;
	private static final char[] DEFAULT_PASSWORD = "enfldsgbnlsngdlksdsgm".toCharArray();
	private static final String ALGO = "PBEWithMD5AndDES";

	public PBEWithMD5AndDESCrypto(String passwordToUse) {
		this.PASSWORD = (StringUtils.hasText(passwordToUse) ? passwordToUse.toCharArray() : DEFAULT_PASSWORD);
	}
	
	public PBEWithMD5AndDESCrypto() {
		this.PASSWORD = DEFAULT_PASSWORD;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.crypto.CryptoDefination#encrypt(java.lang.String)
	 */
	@Override
	public String encrypt(String raw) throws GeneralSecurityException, UnsupportedEncodingException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGO);
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(this.PASSWORD));
        Cipher pbeCipher = Cipher.getInstance(ALGO);
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return BASE64EncoderHelper.base64Encode(pbeCipher.doFinal(raw.getBytes(Constants.UTF_8)));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.crypto.CryptoDefination#decrypt(java.lang.String)
	 */
	@Override
	public String decrypt(String encryptedMsg) throws GeneralSecurityException, IOException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGO);
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance(ALGO);
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(BASE64DecoderHelper.base64Decode(encryptedMsg)), Constants.UTF_8);
	}

}

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

/**
 * Defination for the crypto logic that will be used for encryption
 * and decryption of the password or any field.
 * 
 * @author Jay
 */
public interface CryptoDefination {
	
    public static final byte[] SALT = {
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
    };
   
    /**
     * <p>This will be doing the encryption of the raw string provided</p>
     * @param raw
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String encrypt(String raw) throws GeneralSecurityException, UnsupportedEncodingException;
    
    /**
     * This will be doing the decrypt of the encrypted message.
     * @param encryptedMsg
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public String decrypt(String encryptedMsg) throws GeneralSecurityException, IOException;

}

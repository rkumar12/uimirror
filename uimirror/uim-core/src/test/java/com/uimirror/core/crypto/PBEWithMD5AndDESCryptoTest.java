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
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.uimirror.core.util.LoadExternalGson;

/**
 * @author Jay
 */
public class PBEWithMD5AndDESCryptoTest {
	
	private PBEWithMD5AndDESCrypto pbeWithMD5AndDESCrypto;
	static final String INPUT = "in";
	static final String OUTPUT = "out";
	static final String ISVALID = "isValid";
	
	@Before
	public void setUp(){
		pbeWithMD5AndDESCrypto = new PBEWithMD5AndDESCrypto();
	}
	/**
	 * Test method for {@link com.uimirror.core.crypto.PBEWithMD5AndDESCrypto#encrypt(java.lang.String)}.
	 * @throws GeneralSecurityException 
	 * @throws IOException 
	 */
	@Test
	public void testEncrypt() throws GeneralSecurityException, IOException {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataSets = (List<Map<String, Object>>)LoadExternalGson.loadData("com/uimirror/core/crypto/pbe_with_md5_encryption_data_set_1.json", List.class);
		for(Map<String, Object> data : dataSets){
			if((boolean)data.getOrDefault(ISVALID, Boolean.FALSE)){
				Assert.assertEquals(data.get(OUTPUT).toString(), pbeWithMD5AndDESCrypto.encrypt(data.get(INPUT) != null ? data.get(INPUT).toString() : null));
			}
		}
	}

	/**
	 * Test method for {@link com.uimirror.core.crypto.PBEWithMD5AndDESCrypto#decrypt(java.lang.String)}.
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	@Test
	public void testDecrypt() throws GeneralSecurityException, IOException {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataSets = (List<Map<String, Object>>)LoadExternalGson.loadData("com/uimirror/core/crypto/pbe_with_md5_encryption_data_set_1.json", List.class);
		for(Map<String, Object> data : dataSets){
			if((boolean)data.getOrDefault(ISVALID, Boolean.FALSE)){
				Assert.assertEquals(data.get(INPUT).toString(), pbeWithMD5AndDESCrypto.decrypt(data.get(OUTPUT) != null ? data.get(OUTPUT).toString() : null));
			}
		}
	}

}

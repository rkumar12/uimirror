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

import org.apache.commons.codec.binary.Base64;

/**
 * <p>This will decrypt the base64 string into the raw string</p>
 * @author Jay
 */
public final class BASE64DecoderHelper {

	/**
	 * <p>This will decode the base64 string into the raw string</p>
	 * @param property
	 * @return
	 * @throws IOException
	 */
	public static byte[] base64Decode(String property) throws IOException {
        // NB: This class is internal, and you probably should use another impl
        return Base64.decodeBase64(property);
    }
}

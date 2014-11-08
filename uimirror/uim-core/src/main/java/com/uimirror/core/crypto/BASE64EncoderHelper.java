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

import org.apache.commons.codec.binary.Base64;

/**
 * <p>This will encode the string into the base64 string which should be a url safe</p>
 * @author Jay
 */
public final class BASE64EncoderHelper {

	/**
	 * It encodes the given string into the base64 string
	 * @param bytes which will be encoded
	 * @return encoded version of the string
	 */
	public static String base64Encode(byte[] bytes) {
        // NB: This class is internal, and you probably should use another impl
        return Base64.encodeBase64URLSafeString(bytes);
    }

}

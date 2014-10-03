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

import java.util.UUID;

import com.uimirror.core.RandomKeyGenerator;
import com.uimirror.core.auth.bean.Token;

/**
 * Generates a token using {@link UUID}
 * @author Jay
 */
public class TokenGenerator {

	/**
	 * Generates a new {@link Token} using new random paraphrase and {@link UUID}
	 * @return
	 */
	public static Token getNewOne(){
		String paraphrase = RandomKeyGenerator.randomString(6);
		return new Token(getUUId(), paraphrase);
	}
	
	/**
	 * Generates a token without parapharse
	 * @return
	 */
	public static Token getNewOneWithOutPharse(){
		return new Token(getUUId(), null);
	}
	
	/**
	 * Generates the {@linkplain UUID}
	 * @return
	 */
	private static String getUUId(){
		return UUID.randomUUID().toString();
	}
	

}

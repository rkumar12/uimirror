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
package com.uimirror.core;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility to generate a random alphabets 
 * @author Jay
 */
public class RandomKeyGenerator {
	static final String NUM = "0123456789";
	static final String CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static final String SMALL = "abcdefghijklmnopqrstuvwxz";
	static final String SYMB = "._-";
	static final String AB = NUM+CAPS+SMALL+SYMB;
	
	static Random rnd = new Random();
	static SecureRandom srnd = new SecureRandom();
	
	/**
	 * Generates a non secured random string from all the alphabets
	 * @param len
	 * @return
	 */
	public static String randomString( int len ) {
		if (len < 1)
		      throw new IllegalArgumentException("length < 1: ");
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
	/**
	 * Generates a secure random string from all the alphabets
	 * @param len
	 * @return
	 */
	public static String randomSecureString( int len ) {
		if (len < 1)
		      throw new IllegalArgumentException("length < 1: ");
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
			sb.append( AB.charAt( srnd.nextInt(AB.length()) ) );
		return sb.toString();
	}

}

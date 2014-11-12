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

import java.util.regex.Pattern;

/**
 * Common Constans for the uimirror applcations
 * @author Jayaram
 */
public class Constants {
	private Constants(){
		//STOP the user to create instance
	}

	public static final String UTF_8 = "utf-8";
	public static final String EMPTY = "";
	public static final String SINGLE_SPACE = " ";
	public static final String ERROR = "error";
	public static final String FIELDS = "fields";
	public static final String MESSAGE = "message";
	public static final String NAME_REGEX_PATTERN = "(?u)^[A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð,.'-]+$";
	/**
	 * REGEX ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&amp;+=])(?=\S+$).{8,}$
	 * ^                 # start-of-string
	 * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&amp;+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{8,}             # anything, at least eight places though
     * $  
	 */
	public static final String PASSWORD_REGEX_PATTERN = "^(?=.*[a-zA-Z])(?=\\S+$).{6,}$";
	
	public static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX_PATTERN);
	public static final Pattern PASSWORD_POLICY_PATTERN = Pattern.compile(PASSWORD_REGEX_PATTERN);
	
	public static final String USER_AGENT = "user-agent";
	public static final String IP = "host";
}

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
 * @author Jayaram
 *
 */
public interface Constants {

	public static final String UTF_8 = "utf-8";
	public static final String EMPTY = "";
	public static final String SINGLE_SPACE = " ";
	String ERROR = "error";
	String FIELDS = "fields";
	String MESSAGE = "message";
	String NAME_REGEX_PATTERN = "(?u)^[A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð,.'-]+$";
	/**
	 * REGEX ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$
	 * ^                 # start-of-string
	 * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{8,}             # anything, at least eight places though
     * $  
	 */
	String PASSWORD_REGEX_PATTERN = "^(?=.*[a-zA-Z])(?=\\S+$).{6,}$";
	
	Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX_PATTERN);
	Pattern PASSWORD_POLICY_PATTERN = Pattern.compile(PASSWORD_REGEX_PATTERN);
}

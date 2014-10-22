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
package com.uimirror.core.util;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Test Case for the {@link StringRegexUtil}
 * @author Jay
 */
public class StringUtilRegexTest {

	/**
	 * Checks a various name range
	 */
	@Test
	public void testValidName() {
		String name = "Jayaram";
		String name1 = "D'Souza";
		String name2 = "1234Test";
		String name3 = "@#$Test";
		String name4 = "~Test";
		
		Assert.assertEquals(true, StringRegexUtil.isValidName(name));
		Assert.assertEquals(true, StringRegexUtil.isValidName(name1));
		Assert.assertEquals(false, StringRegexUtil.isValidName(name2));
		Assert.assertEquals(false, StringRegexUtil.isValidName(name3));
		Assert.assertEquals(false, StringRegexUtil.isValidName(name4));
	}
	/**
	 * Checks a various password range
	 */
	@Test
	public void testValidPassword() {
		String password = "Jayaram";
		String password1 = "D'Souza";
		String password2 = "1234   Test";
		String password3 = "      ";
		String password4 = "";
		
		Assert.assertEquals(true, StringRegexUtil.isValidName(password));
		Assert.assertEquals(true, StringRegexUtil.isValidName(password1));
		Assert.assertEquals(false, StringRegexUtil.isValidName(password2));
		Assert.assertEquals(false, StringRegexUtil.isValidName(password3));
		Assert.assertEquals(false, StringRegexUtil.isValidName(password4));
	}

}

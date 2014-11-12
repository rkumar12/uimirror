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

import static org.assertj.core.api.Assertions.assertThat;

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
		assertThat(StringRegexUtil.isValidName(name)).isTrue();
		assertThat(StringRegexUtil.isValidName(name1)).isTrue();
		assertThat(StringRegexUtil.isValidName(name2)).isFalse();
		assertThat(StringRegexUtil.isValidName(name3)).isFalse();
		assertThat(StringRegexUtil.isValidName(name4)).isFalse();
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
		assertThat(StringRegexUtil.isValidName(password)).isTrue();
		assertThat(StringRegexUtil.isValidName(password1)).isTrue();
		assertThat(StringRegexUtil.isValidName(password2)).isFalse();
		assertThat(StringRegexUtil.isValidName(password3)).isFalse();
		assertThat(StringRegexUtil.isValidName(password4)).isFalse();
	}

}

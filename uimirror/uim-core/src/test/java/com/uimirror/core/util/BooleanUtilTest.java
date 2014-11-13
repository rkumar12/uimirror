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

import com.uimirror.core.BooleanUtil;

/**
 * @author Jay
 */
public class BooleanUtilTest {

	@Test
	public void test() {
		assertThat(BooleanUtil.parseBoolean("Y")).isTrue();
		assertThat(BooleanUtil.parseBoolean("Y")).isTrue();
		assertThat(BooleanUtil.parseBoolean("Yes")).isTrue();
		assertThat(BooleanUtil.parseBoolean("YES")).isTrue();
		assertThat(BooleanUtil.parseBoolean("YEs")).isTrue();
		assertThat(BooleanUtil.parseBoolean("NO")).isFalse();
		assertThat(BooleanUtil.parseBoolean("No")).isFalse();
		assertThat(BooleanUtil.parseBoolean("no")).isFalse();
		assertThat(BooleanUtil.parseBoolean("xyx")).isFalse();
		
	}

}

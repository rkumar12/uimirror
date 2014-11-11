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
package com.uimirror.core.user;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

/**
 * @author Jay
 */
public class BasicInfoHashCodeTest {

	@Test
	public void testHashCode() {
		EqualsVerifier.forClass(BasicInfo.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

}

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
package com.uimirror.core.job;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jay
 */
public class JobTest {

	private SimpleJob job;
	
	@Before
	public void setUp(){
	}
	@Test
	public void testEquals() {
		EqualsVerifier.forClass(SimpleJob.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}
	
}

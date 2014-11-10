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

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Jay
 */
public class BasicInfoHashCodeTest {

	@Test
	public void testHashCode() {
		BasicInfo i1 = new BasicInfo.BasicInfoBuilder("12").addEmail("jay").build();
		BasicInfo i1e = new BasicInfo.BasicInfoBuilder("12").addEmail("jay").addFirstName("Jayaram").build();
		BasicInfo i1te = new BasicInfo.BasicInfoBuilder("12").addEmail("jay").addFirstName("Jayaram").addLastName("Pradhan").build();
		BasicInfo i1ne = new BasicInfo.BasicInfoBuilder("12").addFirstName("Jayaram").addLastName("Pradhan").build();
		
		assertThat(Boolean.TRUE, is(i1.equals(i1)));
		assertThat(Boolean.TRUE, allOf(is(i1.equals(i1e)), is(i1e.equals(i1))));
		assertThat(i1.equals(i1te), allOf(is(i1.equals(i1e)), is(i1e.equals(i1te))));
		assertTrue( i1.equals(i1e) == i1.equals(i1e) );
		assertFalse( i1.equals(null) );
		
		assertThat(i1.hashCode(), is(i1e.hashCode()));
		assertThat(Boolean.FALSE, is(i1.equals(i1ne)));
		assertThat(i1.hashCode(), not(i1ne.hashCode()));
		
		BasicInfo i2 = new BasicInfo.BasicInfoBuilder("12").build();
		BasicInfo i2e = new BasicInfo.BasicInfoBuilder("12").addFirstName("Jayaram").build();
		BasicInfo i2te = new BasicInfo.BasicInfoBuilder("12").addFirstName("Jayaram").addLastName("Pradhan").build();
		BasicInfo i2ne = new BasicInfo.BasicInfoBuilder(null).addEmail("Test").addFirstName("Jayaram").addLastName("Pradhan").build();
		
		assertThat(Boolean.TRUE, is(i2.equals(i2)));
		assertThat(Boolean.TRUE, allOf(is(i2.equals(i2e)), is(i2e.equals(i2))));
		assertThat(i2.equals(i2te), allOf(is(i2.equals(i2e)), is(i2e.equals(i2te))));
		assertTrue( i2.equals(i2e) == i2.equals(i2e) );
		assertFalse( i2.equals(null) );
		
		assertThat(i2.hashCode(), is(i2e.hashCode()));
		assertThat(Boolean.FALSE, is(i2.equals(i2ne)));
		assertThat(i2.hashCode(), not(i2ne.hashCode()));
		
		
		BasicInfo i3 = new BasicInfo.BasicInfoBuilder(null).addEmail("jay").build();
		BasicInfo i3e = new BasicInfo.BasicInfoBuilder(null).addEmail("jay").addFirstName("Jayaram").build();
		BasicInfo i3te = new BasicInfo.BasicInfoBuilder(null).addEmail("jay").addFirstName("Jayaram").addLastName("Pradhan").build();
		BasicInfo i3ne = new BasicInfo.BasicInfoBuilder(null).addFirstName("Jayaram").addLastName("Pradhan").build();
		
		assertThat(Boolean.TRUE, is(i3.equals(i3)));
		assertThat(Boolean.TRUE, allOf(is(i3.equals(i3e)), is(i3e.equals(i3))));
		assertThat(i3.equals(i3te), allOf(is(i3.equals(i3e)), is(i3e.equals(i3te))));
		assertTrue( i3.equals(i3e) == i3.equals(i3e) );
		assertFalse( i3.equals(null) );
		
		assertThat(i3.hashCode(), is(i3e.hashCode()));
		assertThat(Boolean.FALSE, is(i3.equals(i3ne)));
		assertThat(i3.hashCode(), not(i3ne.hashCode()));
		
	}

}

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
package com.uimirror.location;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the {@link Country} hashCode, equals and toString 
 * @author Jay
 */
public class CountryHashCodeTest {

	@Test
	public void testHashCode() {
		Country c1 = new Country.CountryBuilder(null).updateName("India").updateShortName("IN").build();
		Country c1e = new Country.CountryBuilder(null).updateName("India").updateShortName("IN").build();
		Country c1te = new Country.CountryBuilder(null).updateName("India").updateShortName("IN").build();
		Country c1ne = new Country.CountryBuilder(null).updateName("India").updateShortName("IN").updateCode(91).build();
		
		assertThat(Boolean.TRUE, is(c1.equals(c1)));
		assertThat(Boolean.TRUE, allOf(is(c1.equals(c1e)), is(c1e.equals(c1))));
		assertThat(c1.equals(c1te), allOf(is(c1.equals(c1e)), is(c1e.equals(c1te))));
		assertTrue( c1.equals(c1e) == c1.equals(c1e) );
		assertFalse( c1.equals(null) );
		
		assertThat(c1.hashCode(), is(c1e.hashCode()));
		assertThat(Boolean.FALSE, is(c1.equals(c1ne)));
		assertThat(c1.hashCode(), not(c1ne.hashCode()));
		
		Country c2 = new Country.CountryBuilder("1").updateName("India").updateShortName("IN").updateCode(91).build();
		Country c2e = new Country.CountryBuilder("1").updateName("India").updateShortName("IN").updateCode(91).build();
		Country c2te = new Country.CountryBuilder("1").updateName("India").updateShortName("IN").updateCode(91).build();
		Country c2ne = new Country.CountryBuilder("1").updateName("India").updateShortName("IN").updateCode(92).build();
		
		assertThat(Boolean.TRUE, is(c2.equals(c2e)));
		assertThat(Boolean.TRUE, is(c2.equals(c2te)));
		assertThat(Boolean.TRUE, is(c2.equals(c2te)));
		assertThat(c2.hashCode(), is(c2e.hashCode()));
		assertThat(Boolean.FALSE, is(c2.equals(c2ne)));
		assertThat(c2.hashCode(), not(c2ne.hashCode()));
		assertThat(c2.toString(), is(c2e.toString()));
		assertThat(c2.toString(), not(c2ne.toString()));
		
		Country c3 = new Country.CountryBuilder("1").updateShortName("IN").build();
		Country c3e = new Country.CountryBuilder("1").updateShortName("US").build();
		assertThat(c3.hashCode(), is(c3e.hashCode()));
		assertThat(Boolean.TRUE, is(c3.equals(c3e)));
	}

}

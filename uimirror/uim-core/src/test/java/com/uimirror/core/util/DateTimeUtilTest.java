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

import java.time.LocalDate;

import org.junit.Test;

/**
 * @author Jay
 */
public class DateTimeUtilTest {

	@Test
	public void testValidDOB(){
		String dbv1 = "1988-03-13";//YYYY-MM-DD
		String dbin2 = "12/03/1988";//DD/MM/YYYY
		String dbin3 = "03/13/1988";//MM/DD/YYYY
		String dbin4 = "03131988";//MMDDYYYY
		String dbin5 = "03-13-1988";//MM-dd-yyyy
		
		assertThat(Boolean.TRUE).isEqualTo(DateTimeUtil.isAValidDate(dbv1));
		assertThat(Boolean.FALSE).isEqualTo(DateTimeUtil.isAValidDate(dbin2));
		assertThat(Boolean.FALSE).isEqualTo(DateTimeUtil.isAValidDate(dbin3));
		assertThat(Boolean.FALSE).isEqualTo(DateTimeUtil.isAValidDate(dbin4));
		assertThat(Boolean.FALSE).isEqualTo(DateTimeUtil.isAValidDate(dbin5));
	}
	
	@Test
	public void testValidWithFormatDOB(){
		String dbv1 = "13-03-1988";//DD-MM-YYYY
		String dbv2 = "13/03/1988";//DD/MM/YYYY
		String dbin3 = "03/13/1988";//MM/DD/YYYY
		
		assertThat(Boolean.TRUE).isEqualTo(DateTimeUtil.isAValidDate(dbv1 , "dd-MM-yyyy"));
		assertThat(Boolean.FALSE).isEqualTo(DateTimeUtil.isAValidDate(dbv1 , "MM-dd-yyyy"));
		assertThat(Boolean.TRUE).isEqualTo(DateTimeUtil.isAValidDate(dbv2, "dd/MM/yyyy"));
		assertThat(Boolean.FALSE).isEqualTo(DateTimeUtil.isAValidDate(dbv2, "MM/dd/yyyy"));
		assertThat(Boolean.FALSE).isEqualTo(DateTimeUtil.isAValidDate(dbin3, "dd/MM/yyyy"));
	}
	
	@Test
	public void stringToDateTest(){
		String dt1 = "1988-02-01";
		LocalDate ldt = DateTimeUtil.stringToDate(dt1);
		assertThat(01).isEqualTo(ldt.getDayOfMonth());
		assertThat(02).isEqualTo(ldt.getMonthValue());
		assertThat(1988).isEqualTo(ldt.getYear());
	}

	@Test
	public void stringToDateWithFormatTest(){
		String dt1 = "2011-12-13";
		LocalDate ldt = DateTimeUtil.stringToDate(dt1, "dd MMM uuuu");
		
		testDate(ldt, 13, 12, 2011);
		
		LocalDate ldt1 = DateTimeUtil.stringToDate(dt1, "MMM dd uuuu");
		testDate(ldt1, 13, 12, 2011);
		
	}
	
	private void testDate(LocalDate dt, int month, int date, int year){
		assertThat(month).isEqualTo(dt.getDayOfMonth());
		assertThat(date).isEqualTo(dt.getMonthValue());
		assertThat(year).isEqualTo(dt.getYear());
	}
	
	@Test
	public void avoveEigthenTest(){
		String dt1 = "1988-03-13";
		String dt2 = "2011-03-13";
		
		assertThat(Boolean.TRUE).isEqualTo(DateTimeUtil.isAgeAboveEighteen(dt1, null));
		assertThat(Boolean.FALSE).isEqualTo(DateTimeUtil.isAgeAboveEighteen(dt2, null));
	}
}

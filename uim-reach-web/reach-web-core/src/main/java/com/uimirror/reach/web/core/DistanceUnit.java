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
package com.uimirror.reach.web.core;


public enum DistanceUnit {
	
	METER("M"),
	KILOMETRE("KM"),
	MILE("MI");

	private final String symbol;
	private final double ONE_MILE_IN_METER = 1609.34;
	private final double ONE_KM_IN_METER = 1000;
	private final double ONE_KM_IN_MILE = 0.621371;
	
	private DistanceUnit(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}
	
	public double convertMilesToMeter(double miles){
		return ONE_MILE_IN_METER*miles;
	}
	
	public double convertKiloMeterToMeter(double kilometer){
		return ONE_KM_IN_METER*kilometer;
	}
	
	public double convertMeterToMile(double meter){
		return (meter / ONE_MILE_IN_METER);
	}
	
	public double convertKiloMeterToMile(double kilometer){
		return ONE_KM_IN_MILE*kilometer;
	}

	@Override
    public String toString() {
    	return getSymbol();
    } 
	
	public static DistanceUnit getEnum(String symbol) {
    	for(DistanceUnit v : values())
    		if(symbol == v.getSymbol()) return v;
    	return null;
    }

}

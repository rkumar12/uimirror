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

import java.util.TimeZone;

/**
 * @author Jay
 */
public enum TimeZones {

	DEFAULT(TimeZone.getDefault()),
    ACT(TimeZone.getTimeZone("ACT")),
    AET(TimeZone.getTimeZone("AET")),
    AFRICA_ABIDJAN(TimeZone.getTimeZone("Africa/Abidjan")),
    AFRICA_ACCRA(TimeZone.getTimeZone("Africa/Accra")),
    AFRICA_ADDIS_ABABA(TimeZone.getTimeZone("Africa/Addis_Ababa")),
    AFRICA_ALGIERS(TimeZone.getTimeZone("Africa/Algiers")),
    AFRICA_ASMERA(TimeZone.getTimeZone("Africa/Asmera")),
    AFRICA_BAMAKO(TimeZone.getTimeZone("Africa/Bamako")),
    AFRICA_BANGUI(TimeZone.getTimeZone("Africa/Bangui")),
    AFRICA_BANJUL(TimeZone.getTimeZone("Africa/Banjul")),
    AFRICA_BISSAU(TimeZone.getTimeZone("Africa/Bissau"));
	
	private final TimeZone tz;
	
	private TimeZones(final TimeZone tz)
    {
        this.tz = tz;
    }
 
    public final TimeZone getTimeZone()
    {
        return tz;
    }
    
    @Override
	public String toString(){
		return tz.getID();
	}
    
    public static TimeZones getEnum(String timeZone){
		 if(timeZone == null)
			 return null;
		 for(TimeZones tz : TimeZones.values()){
			 if(timeZone.equalsIgnoreCase(tz.getTimeZone().getID()))
				 return tz;
		 }
		 return null;
	 }
	
}

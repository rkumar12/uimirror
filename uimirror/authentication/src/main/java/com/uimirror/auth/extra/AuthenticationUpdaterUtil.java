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
package com.uimirror.auth.extra;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.uimirror.auth.bean.AuthenticatedDetails;
import com.uimirror.auth.bean.Authentication;
import com.uimirror.auth.bean.CredentialType;
import com.uimirror.auth.core.AccessTokenFields;

/**
 * An utility that helps to update the refresh token to the authenticated details
 * 
 * @author Jay
 */
public class AuthenticationUpdaterUtil{

	protected static final Logger LOG = LoggerFactory.getLogger(AuthenticationUpdaterUtil.class);
	
	/**
	 * Tries to update the refresh period if authenticated details don't have any 
	 * @param authentication
	 * @param authDetails
	 * @return
	 */
	public static AuthenticatedDetails updateRefreshPeriodIfNecessary(Authentication authentication, AuthenticatedDetails authDetails){
		
		if(authDetails.getRefreshTokenInterval() > 0l)
			return authDetails;
		
		int refPeriod = decideRefreshPeriod(authentication, authDetails);
		return authDetails.updateRefreshTokenInterval(refPeriod);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.AuthRefreshPeriodProvider#decideRefreshPeriod(com.uimirror.core.auth.bean.Authentication, com.uimirror.core.auth.bean.AuthenticatedDetails)
	 */
	private static int decideRefreshPeriod(Authentication auth, AuthenticatedDetails details) {
		return getExpiresInt(auth, details);
	}
	
	/**
	 * gets the expires interval for the token to be issued.
	 * If user has opted for the keep me loged in then token validity will be 24*60 mins
	 * else it will try to find from the refresh token interval else default to 15 mins.
	 * 
	 * @param auth
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static int getExpiresInt(Authentication auth, AuthenticatedDetails details){
		int minuteToAdd = 0;
		minuteToAdd = findPeriodFromInstructions((Map<String, Object>)details.getInstructions());
		if(minuteToAdd == 0){
			//find the number based on the credential type
			minuteToAdd = decideByCredentialType(auth);
		}
		return minuteToAdd;
	}
	
	/**
	 * Tries to find the refresh period if its present in the earlier instructions
	 * 
	 * @param instructions
	 * @return
	 */
	private static int findPeriodFromInstructions(Map<String, Object> instructions){
		int intv = 0;
		try{
			String intreval = (String)instructions.get(AccessTokenFields.REFRESH_TOKEN_INTERVAL);
			intv =  StringUtils.hasText(intreval) ? Integer.parseInt(intreval) : 0;
		}catch(NumberFormatException | NullPointerException ne){
			LOG.error("[IGNORE]- Not able to get the refresh token period");
		}
		return intv;
	}
	/**
	 * Gets the refresh period based on the {@linkplain CredentialType}
	 * default to 15 minutes, if no match found
	 * @param details
	 * @return
	 */
	private static int decideByCredentialType(Authentication auth){
		CredentialType type = auth.getCredentialType();
		int rfp = 0;
		if( type != null){
			switch(type){
			case LOGINFORM:
				if(auth.keepMeLogin())
					rfp += 24*60;
				else
					rfp += 30;
				break;
			case SECRETKEY:
			case APIKEY:
			//case CLIENTSECRECTKEY:
			case SCREENLOCK:
			//case _2FA:
				rfp += 15;
				break;
			default:
				rfp += 15;
				break;
			
			}
		}
		rfp = (rfp == 0) ? 15 : rfp; 
		return rfp;
	}

}

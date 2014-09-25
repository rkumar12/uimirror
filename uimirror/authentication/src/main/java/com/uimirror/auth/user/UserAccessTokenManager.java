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
package com.uimirror.auth.user;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.uimirror.auth.AuthParamExtractor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AccessTokenGenerator;
import com.uimirror.core.auth.AccessTokenManager;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.CredentialType;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;

/**
 * Specifies the logic specific to the accesstoken should be managed
 * and stored
 * @author Jay
 */
public class UserAccessTokenManager implements AccessTokenManager{

	protected static final Logger LOG = LoggerFactory.getLogger(UserAccessTokenManager.class);

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessTokenManager#generateToken(com.uimirror.core.auth.Authentication, java.lang.String, java.lang.Object)
	 */
	@Override
	public AccessToken generateToken(Authentication auth, String id) {
		LOG.info("[START]- Generating token for the user accesstoken");
		LOG.info("[END]- Generating token for the user accesstoken");
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessTokenManager#isValidToken(com.uimirror.core.auth.Token)
	 */
	@Override
	public boolean isValidToken(AccessToken token) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessTokenManager#markAsExpired(com.uimirror.core.auth.Token)
	 */
	@Override
	public void markAsExpired(Token token) {
		// TODO Auto-generated method stub
		
	}
	
	private AccessToken getNewAccessToken(Authentication auth, String id){
		Token token = AccessTokenGenerator.getNewOne();
		long expiresOn = getExpiresOn(auth);
		TokenType tokenType = determineTokenType(auth.getCredentialType());
		String profileId = id;
		return null;
	}
	
	/**
	 * Gets the expires on based on the interval,
	 * if it has keepmeloged in then refresh period will be 30 minutes else 10 
	 * until and unless mentioned
	 * @param auth
	 * @return
	 */
	private long getExpiresOn(Authentication auth){
		long minuteToAdd = getExpiresInt(auth);
		Instant ldt = LocalDateTime.now(Clock.systemUTC()).plusMinutes(minuteToAdd).toInstant(ZoneOffset.UTC);
		return ldt.getEpochSecond();
	}
	
	/**
	 * gets the expires interval for the token to be issued.
	 * @param auth
	 * @return
	 */
	private int getExpiresInt(Authentication auth){
		int minuteToAdd = 0;
		if(auth.keepMeLogin()){
			minuteToAdd += 30;
		}else{
			Map<String, Object> details = (Map<String, Object>)auth.getDetails();
			minuteToAdd += getRefreshPeriod(details);
		}
		return minuteToAdd;
	}
	/**
	 * Gets the refresh period from the passed arguments
	 * @param details
	 * @return
	 */
	private int getRefreshPeriod(Map<String, Object> details){
		int rfp = 0;
		if(!CollectionUtils.isEmpty(details)){
			String refreshInt = (String)details.get(AuthParamExtractor.REFRESH_TOKEN_INTERVAL);
			try{
				rfp = Integer.parseInt(refreshInt);
			}catch(NumberFormatException e){
				LOG.debug("No Refresh Token Interval found default to 10 mins");
			}
		}
		rfp = (rfp == 0) ? 10 : rfp; 
		return rfp;
	}
	/**
	 * Determines whether this token will be used as access key or secret key
	 * @param cType
	 * @return
	 */
	private TokenType determineTokenType(CredentialType cType){
		if(CredentialType.APIKEY.equals(cType))
			return TokenType.SECRET;
		else
			return TokenType.ACCESS;
					
	}

}

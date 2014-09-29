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
package com.uimirror.core.auth;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.bean.DefaultToken;
import com.uimirror.core.auth.bean.Scope;
import com.uimirror.core.auth.bean.TokenType;
import com.uimirror.core.auth.controller.AuthParamExtractor;
import com.uimirror.core.auth.dao.AccessTokenStoreFactory;

/**
 * @author Jay
 */
public abstract class AccessTokenGeneratorManager extends AccessTokenExpirerManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(AccessTokenGeneratorManager.class);
	
	public AccessTokenGeneratorManager(AccessTokenStoreFactory accessTokenStoreFactory) {
		super(accessTokenStoreFactory);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AccessTokenManager#generateToken(com.uimirror.core.auth.Authentication, java.lang.String, java.lang.Object)
	 */
	@Override
	public AccessToken generateToken(Authentication auth, String id, boolean temporal) {
		LOG.info("[START]- Generating token for the user accesstoken");
		LOG.info("[END]- Generating token for the user accesstoken");
		return null;
	}
	
	
	private AccessToken getNewAccessToken(Authentication auth, String id, boolean temporal){
		long expiresOn = getExpiresOn(auth);
		TokenType tokenType = determineTokenType(auth.getCredentialType(), temporal);
		return issueAToken(id, String.valueOf(expiresOn), tokenType, auth.getAuthenticationScope());
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
	 * If user has opted for the keep me loged in then token validity will be 30 mins
	 * else it will try to find from the refresh token interval else default to 10 mins.
	 * 
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
	 * @param temporal
	 * @return
	 */
	private TokenType determineTokenType(CredentialType cType, boolean temporal){
		if(temporal)
			return TokenType.TEMPORAL;
		if(CredentialType.APIKEY.equals(cType))
			return TokenType.SECRET;
		else
			return TokenType.ACCESS;
					
	}
	
	/**
	 * Issues a new token based on the argument specified
	 * 
	 * @param profileId
	 * @param expiresOn
	 * @param tokenType
	 * @param scope
	 * @return
	 */
	private AccessToken issueAToken(String profileId, String expiresOn, TokenType tokenType, Scope scope){
		return new DefaultToken(profileId, expiresOn, tokenType, scope);
	}
	
	

}

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

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.uimirror.auth.AuthParamExtractor;
import com.uimirror.core.Constants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.CredentialType;
import com.uimirror.core.auth.DeviceType;

/**
 * Extracts the user id/ token and password related to the user authentication details.
 * <p>if authentication is of type {@link CredentialType#LOGINFORM}, then 
 * {@link UserAuthenticationForm#getUserId()} will be the user id and
 * {@link UserAuthenticationForm#getPassword()} will be the password. Ip and user agent will be required.
 * </p>
 * <p>
 * if authentication is of type {@link CredentialType#COOKIE}, then
 * {@link UserAuthenticationForm#getAccessToken()} will be the user id.
 * </p>
 * <p>
 * if authentication is of type {@link CredentialType#SCREENLOCK}, then
 * {@link UserAuthenticationForm#getAccessToken()} will be the user id and 
 * {@link UserAuthenticationForm#getPassword()} will be screen lock password
 * </p>
 * @author Jay
 */
@Service
public class UserAuthParamExtractor implements AuthParamExtractor<UserAuthenticationForm> {

	private static final Logger LOG = LoggerFactory.getLogger(UserAuthParamExtractor.class);

	/* (non-Javadoc)
	 * @see com.uimirror.auth.AuthParamExtractor#extractAuthParam(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	public Authentication extractAuthParam(UserAuthenticationForm form) {
		
		LOG.info("[START]- Extracting the user authentication parameter");
		//Get the credential type and device
		ExtractOtherDetails extractOtherDetails = new ExtractOtherDetails(form);
		Authentication auth = null;
		
		if(CredentialType.LOGINFORM.equals(extractOtherDetails.getcType())){
			auth = getLoginFormDetails(form, extractOtherDetails);
		}else if(CredentialType.COOKIE.equals(extractOtherDetails.getcType())){
			auth = getCookieDetails(form, extractOtherDetails);
		}else if(CredentialType.SCREENLOCK.equals(extractOtherDetails.getcType())){
			auth = getScreenLockDetails(form, extractOtherDetails);
		}
		LOG.info("[END]- Extracting the user authentication parameter");
		return auth;
	}
	
	/**
	 * This extracts the user login information for the form parameters.
	 * {@link UserAuthenticationForm#getUserId()} will be the user id and
	 * {@link UserAuthenticationForm#getPassword()} will be the password.
	 * It requires IP and userAgnet for the keeping track purpose.
	 * @param param
	 * @param cType
	 * @param device
	 * @return
	 */
	private Authentication getLoginFormDetails(final UserAuthenticationForm param, ExtractOtherDetails extractOtherDetails){
		boolean keepMeLogin = Boolean.parseBoolean(param.getKeepMeLogedIn());
		keepMeLogin = (keepMeLogin == Boolean.FALSE && 
				("y".equalsIgnoreCase(param.getKeepMeLogedIn()) || "yes".equalsIgnoreCase(param.getKeepMeLogedIn()))) ?  Boolean.TRUE : Boolean.FALSE;
		//Get the additional info such as ip, user agent
		String ip = param.getIp();
		Assert.hasText(ip, "IP can't be empty");
		String userAgent = param.getUserAgent();
		Assert.hasText(userAgent, "User Agent can't be empty");
		Map<String, String> details = new LinkedHashMap<String, String>(4);
		details.put(Constants.IP, ip);
		details.put(Constants.USER_AGENT, userAgent);
		return new UserAuthentication(param.getUserId(), param.getPassword(), keepMeLogin, 
				extractOtherDetails.getcType(), details, extractOtherDetails.getdType());
	}
	
	/**
	 * This extracts the cookie authentication parameter details.
	 * {@link UserAuthenticationForm#getAccessToken()} will be the user id.
	 * @param param
	 * @param extractOtherDetails
	 * @return
	 */
	private Authentication getCookieDetails(final UserAuthenticationForm param, ExtractOtherDetails extractOtherDetails){
		Map<String, String> details = new LinkedHashMap<String, String>(4);
		details.put(REFRESH_TOKEN_INTERVAL, extractOtherDetails.getRefreshTokenPeriod());
		return new UserAuthentication(param.getAccessToken(), null, Boolean.FALSE, 
				extractOtherDetails.getcType(), details, extractOtherDetails.getdType());
	}
	
	/**
	 * This will extract the screen lock authentication details from the parameters.
	 * {@link UserAuthenticationForm#getAccessToken()} will be the user id and 
	 * {@link UserAuthenticationForm#getPassword()} will be screen lock password
	 * 
	 * @param param
	 * @param extractOtherDetails
	 * @return
	 */
	private Authentication getScreenLockDetails(final UserAuthenticationForm param, ExtractOtherDetails extractOtherDetails){
		Map<String, String> details = new LinkedHashMap<String, String>(4);
		details.put(REFRESH_TOKEN_INTERVAL, extractOtherDetails.getRefreshTokenPeriod());
		return new UserAuthentication(param.getAccessToken(), param.getPassword(), Boolean.FALSE, 
				extractOtherDetails.getcType(), details, extractOtherDetails.getdType());
	}

	/**
	 * This will extract the device and credential type from the request parameters
	 * 
	 * @author Jay
	 */
	private class ExtractOtherDetails{
		private final CredentialType cType;
		private final DeviceType dType;
		private final String refreshTokenPeriod;
		
		public ExtractOtherDetails(final UserAuthenticationForm param) {
			this.cType = StringUtils.hasText(param.getCredentialType()) ? CredentialType.getEnum(param.getCredentialType()) : CredentialType.LOGINFORM;
			this.dType = StringUtils.hasText(param.getDeviceType()) ? DeviceType.getEnum(param.getDeviceType()) : DeviceType.BROWSER;
			this.refreshTokenPeriod = StringUtils.hasText(param.getRefreshTokenInterval()) ? param.getRefreshTokenInterval() : DEFAULT_REFRESH_INTERVAL;
		}

		public CredentialType getcType() {
			return cType;
		}

		public DeviceType getdType() {
			return dType;
		}

		public String getRefreshTokenPeriod() {
			return refreshTokenPeriod;
		}
		
	}
}

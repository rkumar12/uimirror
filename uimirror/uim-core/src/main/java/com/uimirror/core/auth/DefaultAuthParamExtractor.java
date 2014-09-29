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

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.uimirror.core.BooleanUtil;
import com.uimirror.core.Constants;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.bean.DeviceType;
import com.uimirror.core.auth.bean.form.AuthenticationForm;
import com.uimirror.core.auth.bean.form.BasicAuthenticationForm;
import com.uimirror.core.auth.controller.AuthParamExtractor;

/**
 * Extracts the user id/ token and password related to the user authentication details.
 * <p>if authentication is of type {@link CredentialType#LOGINFORM}, then 
 * {@link AuthenticationForm#getUserId()} will be the user id and
 * {@link AuthenticationForm#getPassword()} will be the password. Ip and user agent will be required.
 * </p>
 * <p>
 * if authentication is of type {@link CredentialType#ACCESSKEY}, then
 * {@link AuthenticationForm#getAccessToken()} will be the user id.
 * </p>
 * <p>
 * if authentication is of type {@link CredentialType#SCREENLOCK}, then
 * {@link AuthenticationForm#getAccessToken()} will be the user id and 
 * {@link AuthenticationForm#getPassword()} will be screen lock password
 * </p>
 * @author Jay
 */
public class DefaultAuthParamExtractor implements AuthParamExtractor {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthParamExtractor.class);

	/* (non-Javadoc)
	 * @see com.uimirror.auth.AuthParamExtractor#extractAuthParam(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	public Authentication extractAuthParam(BasicAuthenticationForm form) {
		
		LOG.info("[START]- Extracting the user authentication parameter");
		//Get the credential type and device
		ExtractOtherDetails extractOtherDetails = new ExtractOtherDetails(form);
		Authentication auth = null;
		
		if(CredentialType.LOGINFORM.equals(extractOtherDetails.getcType())){
			auth = getLoginFormDetails(form, extractOtherDetails);
		}else if(CredentialType.ACCESSKEY.equals(extractOtherDetails.getcType())){
			auth = getCookieDetails(form, extractOtherDetails);
		}else if(CredentialType.SCREENLOCK.equals(extractOtherDetails.getcType())){
			auth = getScreenLockDetails(form, extractOtherDetails);
		}
		LOG.info("[END]- Extracting the user authentication parameter");
		return auth;
	}
	
	/**
	 * This extracts the user login information for the form parameters.
	 * {@link AuthenticationForm#getUserId()} will be the user id and
	 * {@link AuthenticationForm#getPassword()} will be the password.
	 * It requires IP and userAgnet for the keeping track purpose.
	 * @param param
	 * @param cType
	 * @param device
	 * @return
	 */
	private Authentication getLoginFormDetails(final BasicAuthenticationForm param, ExtractOtherDetails extractOtherDetails){
		boolean keepMeLogin = BooleanUtil.parseBoolean(param.getKeepMeLogedIn());
		//Get the additional info such as ip, user agent
		String ip = param.getIp();
		Assert.hasText(ip, "IP can't be empty");
		String userAgent = param.getUserAgent();
		Assert.hasText(userAgent, "User Agent can't be empty");
		Map<String, String> details = new LinkedHashMap<String, String>(4);
		details.put(Constants.IP, ip);
		details.put(Constants.USER_AGENT, userAgent);
//		return new UserAuthentication(param.getUserId(), param.getPassword(), keepMeLogin, 
//				extractOtherDetails.getcType(), details, extractOtherDetails.getdType());
		return null;
	}
	
	/**
	 * This extracts the cookie authentication parameter details.
	 * {@link AuthenticationForm#getAccessToken()} will be the user id.
	 * @param param
	 * @param extractOtherDetails
	 * @return
	 */
	private Authentication getCookieDetails(final BasicAuthenticationForm param, ExtractOtherDetails extractOtherDetails){
		Map<String, String> details = new LinkedHashMap<String, String>(4);
		details.put(REFRESH_TOKEN_INTERVAL, extractOtherDetails.getRefreshTokenPeriod());
//		return new UserAuthentication(param.getAccessToken(), null, Boolean.FALSE, 
//				extractOtherDetails.getcType(), details, extractOtherDetails.getdType());
		return null;
	}
	
	/**
	 * This will extract the screen lock authentication details from the parameters.
	 * {@link AuthenticationForm#getAccessToken()} will be the user id and 
	 * {@link AuthenticationForm#getPassword()} will be screen lock password
	 * 
	 * @param param
	 * @param extractOtherDetails
	 * @return
	 */
	private Authentication getScreenLockDetails(final BasicAuthenticationForm param, ExtractOtherDetails extractOtherDetails){
		Map<String, String> details = new LinkedHashMap<String, String>(4);
		details.put(REFRESH_TOKEN_INTERVAL, extractOtherDetails.getRefreshTokenPeriod());
//		return new UserAuthentication(param.getAccessToken(), param.getPassword(), Boolean.FALSE, 
//				extractOtherDetails.getcType(), details, extractOtherDetails.getdType());
		return null;
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
		
		public ExtractOtherDetails(final BasicAuthenticationForm param) {
			this.cType = StringUtils.hasText(param.getCredentialType().getDescription()) ? CredentialType.getEnum(param.getCredentialType().getDescription()) : CredentialType.LOGINFORM;
			this.dType = DeviceType.BROWSER;
			this.refreshTokenPeriod = DEFAULT_REFRESH_INTERVAL;
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

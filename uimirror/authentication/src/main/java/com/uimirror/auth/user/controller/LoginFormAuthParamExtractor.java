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
package com.uimirror.auth.user.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.uimirror.auth.user.bean.FormBasedAuthentication;
import com.uimirror.core.BooleanUtil;
import com.uimirror.core.Constants;
import com.uimirror.core.ValidatorService;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.bean.form.BasicAuthenticationForm;
import com.uimirror.core.auth.controller.DefaultAuthParamextractor;

/**
 * Extracts the required details and form the {@link Authentication} object 
 * using {@link FormBasedAuthentication}
 * 
 * {@link LoginFormAuthParamExtractor#extractAuthParam(BasicAuthenticationForm)} will extract the
 * {@link Authentication} object performing {@link ValidatorService#validate(Object)}
 * 
 * @author Jay
 */
public class LoginFormAuthParamExtractor extends DefaultAuthParamextractor{
	
	private static final Logger LOG = LoggerFactory.getLogger(LoginFormAuthParamExtractor.class);

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthParamExtractor#extractAuthParam(com.uimirror.core.auth.bean.form.BasicAuthenticationForm)
	 */
	@Override
	public Authentication extractAuthParam(BasicAuthenticationForm param) {
		LOG.info("[START]- Extracting the login form authentication parameter");
		Authentication auth = null;
		if(isValid(param))
			auth = getLoginFormDetails(param);
		LOG.info("[END]- Extracting the login form authentication parameter");
		return auth;
	}

	private boolean isValid(BasicAuthenticationForm param){
		return validate(param);
	}
	/* (non-Javadoc)
	 * @see com.uimirror.core.ValidatorService#validate(java.lang.Object)
	 */
	@Override
	public boolean validate(Object src) {
		boolean valid = Boolean.TRUE;
		BasicAuthenticationForm param = (BasicAuthenticationForm) src;
		//If Header's are valid
		valid = headerValidator(param);
		//Should have proper credential type
		if(!valid && !CredentialType.LOGINFORM.equals(param.getCredentialType()))
			valid =  Boolean.FALSE;
		//User Id must present
		if(!valid && !StringUtils.hasText(param.getUserId()))
			valid =  Boolean.FALSE;
		//password must present
		if(!valid && !StringUtils.hasText(param.getPassword()))
			valid = Boolean.FALSE;
		
		return valid;
	}
	
	/**
	 * This extracts the user login information for the form parameters.
	 * {@link BasicAuthenticationForm#getUserId()} will be the user id and
	 * {@link BasicAuthenticationForm#getPassword()} will be the password.
	 * It requires IP and userAgnet for the keeping track purpose.
	 * @param param
	 * @return populated {@linkplain Authentication} principal
	 */
	private Authentication getLoginFormDetails(final BasicAuthenticationForm param){
		boolean keepMeLogin = BooleanUtil.parseBoolean(param.getKeepMeLogedIn());
		//Get the additional info such as ip, user agent
		String ip = param.getIp();
		String userAgent = param.getUserAgent();
		Map<String, Object> details = new LinkedHashMap<String, Object>(4);
		details.put(Constants.IP, ip);
		details.put(Constants.USER_AGENT, userAgent);
		return new FormBasedAuthentication(param.getUserId(), param.getPassword(), details, keepMeLogin, ip, userAgent);
	}

}

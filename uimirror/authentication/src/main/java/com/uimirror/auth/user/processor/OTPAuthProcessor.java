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
package com.uimirror.auth.user.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.exception.AuthToApplicationExceptionMapper;
import com.uimirror.auth.user.bean.OTPAuthentication;
import com.uimirror.auth.user.bean.form.OTPAuthenticationForm;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.service.TransformerService;

/**
 * Extracts the field, interact with the {@link AuthenticationManager}
 * and respond back to the caller with the valid response.
 * This authentication process involves in 2FA OTP validation.
 * 
 * Its expected user should have given the earlier accessToken along with
 * the OTP received in the mail
 * @author Jay
 */
public class OTPAuthProcessor implements Processor<OTPAuthenticationForm>{

	protected static final Logger LOG = LoggerFactory.getLogger(OTPAuthProcessor.class);
	
	private @Autowired TransformerService<OTPAuthenticationForm, OTPAuthentication> otpFormToAuthTransformer;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired AuthenticationProvider otpAuthProvider;
	
	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public Object invoke(OTPAuthenticationForm param) throws ApplicationException{
		LOG.debug("[START]- Generating a new accesstoken based on the previous accesstoken and OTP for the 2FA");
		//Step 1- Transform the bean to Authentication
		Authentication auth = getTransformedObject(param);
		//Let GC take this ASAP
		param = null;
		//Remove Unnecessary information from the accessToken Before Sending to the user
		Authentication authToken = authenticateAndIssueToken(auth);
		AccessToken token = (AccessToken)authToken.getPrincipal();
		LOG.debug("[END]- Generating a new accesstoken based on the previous accesstoken and OTP for the 2FA {}", auth);
		return jsonResponseTransFormer.doTransForm(token.toResponseMap());
	}
	
	/**
	 * converts the form bean object into a {@link Authentication} object
	 * @param param
	 * @return
	 */
	private Authentication getTransformedObject(OTPAuthenticationForm param) {
		return otpFormToAuthTransformer.transform(param);
	}

	/**
	 * On basics of {@link CredentialType}, it will simply validate or generate 
	 * the access token {@link AccessToken}
	 * @param auth
	 * @return
	 */
	private Authentication authenticateAndIssueToken(Authentication auth){
		return otpAuthProvider.authenticate(auth);
	}

}

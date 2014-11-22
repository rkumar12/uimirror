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
package com.uimirror.ws.auth.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.form.AuthenticatedHeaderForm;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.sso.exception.AuthToApplicationExceptionMapper;
import com.uimirror.sso.token.store.AccessTokenStore;

/**
 * The step of operations for this processor is defined as below:
 * <ol>
 * <li>Extract the Token provided in the request</li>
 * <li>Mark as expire to the given token</li>
 * <li>Transform the generated response into json</li>
 * </ol>
 * 
 * @author Jay
 */
public class LogOutAuthProcessor implements Processor<AuthenticatedHeaderForm, String>{

	protected static final Logger LOG = LoggerFactory.getLogger(LogOutAuthProcessor.class);
	private @Autowired AccessTokenStore persistedAccessTokenMongoStore;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public String invoke(AuthenticatedHeaderForm param) throws ApplicationException{
		LOG.debug("[START]- Expiring the access token.");
		//Step 1- Get the access token from the request
		String prevToken = param.getToken();
		//Invalidate the previous Token
		persistedAccessTokenMongoStore.markAsExpired(prevToken);
		LOG.debug("[END]- Expiring the access token");
		return jsonResponseTransFormer.doTransForm("Sucess");
	}

}

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
package com.uimirror.account.create.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.user.form.RegisterForm;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.user.BasicInfo;

/**
 * @author Jay
 */
//TODO update java doc 
public class UserRegistrationProcessor implements Processor<RegisterForm,Object>{

	protected static final Logger LOG = LoggerFactory.getLogger(UserRegistrationProcessor.class);
	private @Autowired TransformerService<RegisterForm, BasicInfo> userRegisterFormToRegisterTransformer;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	
	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.BackgroundProcessor#invoke(java.lang.Object)
	 */
	@Override
	public Object invoke(RegisterForm param){
		LOG.debug("[START]- Regestering a User");
		//Step 1- Transform the bean to Authentication
		BasicInfo basicUser = getTransformedObject(param);
		//Let GC take this ASAP
		param = null;
		//Step 2- Validate against DB Create a basic Profile
		//TODO: 
		//Step 3- Send Mail in background
		
		//TODO: sending email logic
		//Step 4- Generate a temporarily access token if validation is success
		//TODO:check the return type
		LOG.debug("[END]- Regestering a user");
		//
		return jsonResponseTransFormer.doTransForm(generateToken());
	}

	/**
	 * @param param
	 * @return
	 */
	private BasicInfo getTransformedObject(RegisterForm param) {
		return userRegisterFormToRegisterTransformer.transform(param);
	}
	
	//TODO method should have a input as previous profile id
	private AccessToken generateToken(){
		return null;
	}

}

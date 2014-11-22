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
package com.uimirror.account.client;

import org.springframework.util.Assert;

import com.uimirror.core.RandomKeyGenerator;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.Client.ClientBuilder;
import com.uimirror.sso.auth.OTPAuthentication;

/**
 * A transformer implementation which will transform the {@linkplain OTPAuthenticationForm}
 * to {@linkplain OTPAuthentication}
 * @author Jay
 */
public class ClientRegisterFormToClientTransformer implements TransformerService<ClientRegisterForm, Client>{

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public Client transform(ClientRegisterForm form) {
		Assert.notNull(form, "Source Can't be empty");
		//Validate the form
		form.isValid();
		
		return new ClientBuilder(null).
				addApiKey(getApiKey()).
				addAppURL(form.getAppURL()).
				addImagePath(form.getImage()).
				addName(form.getName()).
				addRedirectURI(form.getRedirectURL()).
				addRegisteredBy(form.getOwner()).
				addRegisteredOn(DateTimeUtil.getCurrentSystemUTCEpoch()).
				addSecret(getSecretKey()).
				addStatus(AccountStatus.ACTIVE).build();
	}
	
	/**
	 * @return
	 */
	private String getApiKey(){
		return RandomKeyGenerator.randomSecureString(8);
	}
	
	/**
	 * @return
	 */
	private String getSecretKey(){
		return RandomKeyGenerator.randomSecureString(6);
	}

}

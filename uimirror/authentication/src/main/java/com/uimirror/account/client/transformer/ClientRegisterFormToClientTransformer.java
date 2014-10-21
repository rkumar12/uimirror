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
package com.uimirror.account.client.transformer;

import org.springframework.util.Assert;

import com.uimirror.account.auth.user.bean.OTPAuthentication;
import com.uimirror.account.auth.user.form.OTPAuthenticationForm;
import com.uimirror.account.client.bean.Client;
import com.uimirror.account.client.form.RegisterForm;
import com.uimirror.core.RandomKeyGenerator;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.core.util.DateTimeUtil;

/**
 * A transformer implementation which will transform the {@linkplain OTPAuthenticationForm}
 * to {@linkplain OTPAuthentication}
 * @author Jay
 */
public class ClientRegisterFormToClientTransformer implements TransformerService<RegisterForm, Client>{

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public Client transform(RegisterForm form) {
		Assert.notNull(form, "Source Can't be empty");
		//Validate the form
		form.isValid();
		
		return new Client(null, form.getName(), getSecretKey()
				, form.getRedirectURL(), form.getAppURL(), AccountStatus.ACTIVE, getApiKey()
				, DateTimeUtil.getCurrentSystemUTCEpoch(), form.getOwner(), null);
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

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
package com.uimirror.ws.auth.transformer;

import org.springframework.util.Assert;

import com.uimirror.core.service.TransformerService;
import com.uimirror.sso.auth.LoginAuthentication;
import com.uimirror.ws.auth.form.LoginForm;

/**
 * A transformer implementation which will transform the {@linkplain ClientRegisterForm}
 * to {@linkplain LoginAuthentication}
 * @author Jay
 */
public class LoginFormToAuthTransformer implements TransformerService<LoginForm, LoginAuthentication>{


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public LoginAuthentication transform(LoginForm src) {
		Assert.notNull(src, "Source Can't be empty");
		//Validate the form
		src.isValid();
		//return new LoginAuthentication(src.getToken(),src.getUserId(), src.getPassword(), src.getKeepMeLogedIn(), src.getIp(), src.getUserAgent());
		return new LoginAuthentication.LoginAuthBuilder(src.getToken()).addUserId(src.getUserId()).
				addPassword(src.getPassword()).
				addKeepMeLoggedIn().
				addIp(src.getIp()).
				addAgent(src.getUserAgent()).build();
	}

}

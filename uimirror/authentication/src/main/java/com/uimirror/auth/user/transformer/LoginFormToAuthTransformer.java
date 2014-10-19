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
package com.uimirror.auth.user.transformer;

import org.springframework.util.Assert;

import com.uimirror.auth.user.bean.LoginAuthentication;
import com.uimirror.auth.user.bean.form.LoginForm;
import com.uimirror.core.service.TransformerService;

/**
 * A transformer implementation which will transform the {@linkplain LoginForm}
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
		return new LoginAuthentication(src.getToken(),src.getUserId(), src.getPassword(), src.getKeepMeLogedIn(), src.getIp(), src.getUserAgent());
	}

}

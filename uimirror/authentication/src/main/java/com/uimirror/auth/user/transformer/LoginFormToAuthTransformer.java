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

import com.uimirror.auth.user.bean.LoginFormAuthentication;
import com.uimirror.auth.user.bean.form.LoginFormAuthenticationForm;
import com.uimirror.core.service.TransformerService;

/**
 * A transformer implementation which will transform the {@linkplain LoginFormAuthenticationForm}
 * to {@linkplain LoginFormAuthentication}
 * @author Jay
 */
public class LoginFormToAuthTransformer implements TransformerService<LoginFormAuthenticationForm, LoginFormAuthentication>{


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public LoginFormAuthentication transform(LoginFormAuthenticationForm src) {
		Assert.notNull(src, "Source Can't be empty");
		//Validate the form
		src.isValid();
		return new LoginFormAuthentication(src.getToken(),src.getUserId(), src.getPassword(), src.getKeepMeLogedIn(), src.getIp(), src.getUserAgent());
	}

}

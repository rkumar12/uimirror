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
package com.uimirror.account.auth.user.transformer;

import org.springframework.util.Assert;

import com.uimirror.account.auth.user.form.ScreenLockAuthenticationForm;
import com.uimirror.core.service.TransformerService;
import com.uimirror.sso.auth.ScreenLockAuthentication;

/**
 * A transformer implementation which will transform the {@linkplain ScreenLockAuthenticationForm}
 * to {@linkplain ScreenLockAuthentication}
 * @author Jay
 */
public class ScreenLockFormToAuthTransformer implements TransformerService<ScreenLockAuthenticationForm, ScreenLockAuthentication>{


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public ScreenLockAuthentication transform(ScreenLockAuthenticationForm src) {
		Assert.notNull(src, "Source Can't be empty");
		//Validate the form
		src.isValid();
		return new ScreenLockAuthentication(src.getToken(),src.getPassword(), src.getIp(), src.getUserAgent());
	}

}

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

import com.uimirror.account.auth.user.bean.OTPAuthentication;
import com.uimirror.account.auth.user.bean.form.OTPAuthenticationForm;
import com.uimirror.core.service.TransformerService;

/**
 * A transformer implementation which will transform the {@linkplain OTPAuthenticationForm}
 * to {@linkplain OTPAuthentication}
 * @author Jay
 */
public class OTPFormToAuthTransformer implements TransformerService<OTPAuthenticationForm, OTPAuthentication>{


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public OTPAuthentication transform(OTPAuthenticationForm src) {
		Assert.notNull(src, "Source Can't be empty");
		//Validate the form
		src.isValid();
		return new OTPAuthentication(src.getToken(),src.getOtp(), src.getIp(), src.getUserAgent());
	}

}

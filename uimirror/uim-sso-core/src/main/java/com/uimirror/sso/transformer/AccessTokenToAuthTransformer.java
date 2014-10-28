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
package com.uimirror.sso.transformer;

import org.springframework.util.Assert;

import com.uimirror.core.form.AuthenticatedHeaderForm;
import com.uimirror.core.service.TransformerService;
import com.uimirror.sso.auth.OAuth2Authentication;

/**
 * A transformer implementation which will transform the {@linkplain AuthenticatedHeaderForm}
 * to {@linkplain OAuth2Authentication}
 * @author Jay
 */
public class AccessTokenToAuthTransformer implements TransformerService<AuthenticatedHeaderForm, OAuth2Authentication>{


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public OAuth2Authentication transform(AuthenticatedHeaderForm src) {
		Assert.notNull(src, "Source Can't be empty");
		//Validate the form
		src.isValid();
		return new OAuth2Authentication(src.getToken(), src.getIp(), src.getUserAgent());
	}

}

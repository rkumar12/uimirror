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

import com.uimirror.core.service.TransformerService;
import com.uimirror.sso.auth.APIKeyAuthentication;
import com.uimirror.sso.form.ClientAPIForm;

/**
 * A transformer implementation which will transform the {@linkplain ClientAPIForm}
 * to {@linkplain APIKeyAuthentication}
 * @author Jay
 */
public class APIKeyToAuthTransformer implements TransformerService<ClientAPIForm, APIKeyAuthentication>{


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public APIKeyAuthentication transform(ClientAPIForm src) {
		Assert.notNull(src, "Source Can't be empty");
		//Validate the form
		src.isValid();
		return new APIKeyAuthentication.APIKeyBuilder(src.getClientId()).
				addRedirectURI(src.getRedirectURI()).
				addScope(src.getScope()).
				addIP(src.getIp()).
				addUserAgent(src.getUserAgent()).build();
	}

}

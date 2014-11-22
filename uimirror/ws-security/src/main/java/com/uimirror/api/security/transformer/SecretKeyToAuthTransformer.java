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
package com.uimirror.api.security.transformer;

import org.springframework.util.Assert;

import com.uimirror.core.service.TransformerService;
import com.uimirror.sso.auth.OAuth2SecretKeyAuthentication;
import com.uimirror.sso.form.ClientSecretKeyForm;

/**
 * A transformer implementation which will transform the {@linkplain ClientSecretKeyForm}
 * to {@linkplain OAuth2SecretKeyAuthentication}
 * @author Jay
 */
public class SecretKeyToAuthTransformer implements TransformerService<ClientSecretKeyForm, OAuth2SecretKeyAuthentication>{


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public OAuth2SecretKeyAuthentication transform(ClientSecretKeyForm src) {
		Assert.notNull(src, "Source Can't be empty");
		//Validate the form
		src.isValid();
		return new OAuth2SecretKeyAuthentication.OAuth2SecretKeyBuilder(src.getSecretCode()).
				addRedirectURI(src.getRedirectURI()).
				addApiKey(src.getClientId()).
				addClientSecret(src.getClientSecret()).
				addIp(src.getIp()).
				addAgent(src.getUserAgent()).
				build();
				
	}

}

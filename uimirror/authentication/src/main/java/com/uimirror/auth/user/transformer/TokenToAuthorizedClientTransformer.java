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

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.uimirror.auth.user.ClientAuthorizedScope;
import com.uimirror.auth.user.UserAuthorizedClient;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.service.TransformerService;

/**
 * A transformer implementation which will transform the {@linkplain AccessToken}
 * to {@linkplain UserAuthorizedClient}
 * @author Jay
 */
public class TokenToAuthorizedClientTransformer implements TransformerService<AccessToken, UserAuthorizedClient>{


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public UserAuthorizedClient transform(AccessToken src) {
		Assert.notNull(src, "Source Can't be empty");
		String profileId = src.getOwner();
		String clientId = src.getClient();
		return new UserAuthorizedClient(profileId, buildAuthorizedClient(clientId, getAuthorizedScope(src)));
	}

	/**
	 * Builds the scope that user has accepted for this client
	 * 
	 * @param token
	 * @return
	 */
	private Scope getAuthorizedScope(AccessToken token){
		if(TokenType.SECRET.equals(token.getType()))
			return token.getScope();
		return null;
	}
	
	/**
	 * This will build one client that user has authorized
	 * @param clientId
	 * @param scope
	 * @return
	 */
	private List<ClientAuthorizedScope> buildAuthorizedClient(String clientId, Scope scope){
		List<ClientAuthorizedScope> clients = new ArrayList<ClientAuthorizedScope>(3);
		ClientAuthorizedScope client = new ClientAuthorizedScope(clientId, scope);
		clients.add(client);
		return clients;
	}

}

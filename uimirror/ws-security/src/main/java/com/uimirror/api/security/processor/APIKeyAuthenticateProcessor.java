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
package com.uimirror.api.security.processor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.Processor;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.service.MatcherService;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.store.ClientStore;
import com.uimirror.sso.exception.AuthToApplicationExceptionMapper;
import com.uimirror.sso.exception.BadCredentialsException;
import com.uimirror.sso.exception.LockedException;

/**
 * The step of operations for this processor is defined as below:
 * <ol>
 * <li>Authenticate the provided client details and return</li>
 * </ol>
 * 
 * @author Jay
 */
public class APIKeyAuthenticateProcessor implements Processor<Authentication, Client>, MatcherService<Authentication, Client>{

	protected static final Logger LOG = LoggerFactory.getLogger(APIKeyAuthenticateProcessor.class);
	
	private ClientStore persistedClientStore;
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	@MapException(use=AuthToApplicationExceptionMapper.NAME)
	public Client invoke(Authentication param) throws ApplicationException{
		LOG.debug("[START]- Authenticating the client API KEY");
		//Step 1-Get the Client Details
		Client client = getClientDetails(param);
		//Step 2-Check the client for its account state and status
		doStateCheck(client);
		//Step 3-Match the given details with the retrieved details
		isMatching(param, client);
		return client;
	}
	
	/**
	 * Gets the {@link Client} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	private Client getClientDetails(Authentication authentication){
		LOG.debug("[START]- Reteriving active Client By api Key");
		Client client = reteriveClient(authentication.getName());
		LOG.debug("[END]- Reteriving active Client By api Key");
		return client;
	}
	
	/**
	 * Retrieves the client based on the client apiKey
	 * @param apiKey
	 * @return
	 */
	private Client reteriveClient(String apiKey){
		return persistedClientStore.findClientByApiKey(apiKey);
	}
	
	/**
	 * Check the current client status
	 * @param client
	 */
	private void doStateCheck(Client client) {
		if(AccountStatus.BLOCKED.equals(client.getStatus()))
			throw new LockedException();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.MatcherService#isMatching(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean isMatching(Authentication src, Client des) {
		boolean matching = Boolean.TRUE;
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)src.getDetails();
		String redirectUri = (String)map.get(AuthConstants.REDIRECT_URI);
		if(redirectUri == null || !redirectUri.equalsIgnoreCase(des.getRedirectURI()))
			throw new BadCredentialsException();
		return matching;
	}

	public void setPersistedClientStore(ClientStore persistedClientStore) {
		this.persistedClientStore = persistedClientStore;
	}

}

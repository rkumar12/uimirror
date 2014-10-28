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
package com.uimirror.ws.account.processor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.sso.token.AccessTokenProvider;
import com.uimirror.sso.token.InvalidateTokenProcessor;
import com.uimirror.sso.token.TokenGenerator;
import com.uimirror.user.store.UserCredentialsStore;
import com.uimirror.ws.account.form.VerifyForm;
import com.uimirror.ws.account.form.VerifySource;
import com.uimirror.ws.api.security.exception.InvalidTokenException;

/**
 * Processor That will be responsible for the Account Verifications purpose
 * 
 * @author Jay
 */
public class VerifyActivateUserAccountProcessor implements Processor<VerifyForm, String> {

	protected static final Logger LOG = LoggerFactory.getLogger(VerifyActivateUserAccountProcessor.class);
	
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;
	
	private @Autowired BackgroundProcessorFactory<String, Object> backgroundProcessorFactory;
	
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	
	private @Autowired UserCredentialsStore userCredentialStore;

	/**
	 *  Verify the provided details, validate the token,
	 *  if all are correct enable his account.
	 *  generate a new token of type {@link TokenType#ACCESS}
	 *  
	 *  (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public String invoke(VerifyForm param) throws ApplicationException {

		LOG.info("[START]- Verifying the Newly created user.");
		param.isValid();
		VerifySource accountActivationSource = param.getSource();
		String codeFromForm = param.getCode();
		String prevToken = param.getToken();
		AccessToken newAccessToken = null;
		AccessToken prevAccessToken = null;
		if (VerifySource.MAIL == accountActivationSource) {
			prevAccessToken = persistedAccessTokenProvider.get(prevToken);
		} else {
			prevAccessToken = persistedAccessTokenProvider.getValid(prevToken);
			Map<String, Object> instructionsMap = prevAccessToken.getInstructions();
			//TODO to update exact field name that during registration its updated
			String codeFromAccessToken = (String) instructionsMap.get(codeFromForm);
			if (!codeFromAccessToken.equals(codeFromForm)) {
				throw new InvalidTokenException();
			}
		}

		//Enable Account Processor should enable one in forground and others in background
		//SO take into a diffrent processor, where it make sense to call back ground processor
		userCredentialStore.enableAccount(prevAccessToken.getOwner());
		
		newAccessToken = getNewToken(prevAccessToken);
		persistedAccessTokenProvider.store(newAccessToken);
		// Invalidate the previous Token
		backgroundProcessorFactory.getProcessor(InvalidateTokenProcessor.NAME).invoke(prevToken);
		LOG.info("[END]- Verifying the Newly created user.");
		return jsonResponseTransFormer.doTransForm(newAccessToken.eraseEsential().toResponseMap());
	}

	/**
	 * Generate a new Access Token by extending its expire.
	 * 
	 * @param prev_token
	 * @return
	 */
	private AccessToken getNewToken(AccessToken prev_token) {
		Map<String, Object> instructions = prev_token.getInstructions();
		Map<String, Object> notes = prev_token.getNotes();
		Token token = TokenGenerator.getNewOneWithOutPharse();
		TokenType type = TokenType.ACCESS;
		Scope scope = prev_token.getScope();
		String requestor = prev_token.getClient();
		String owner = prev_token.getOwner();
		// Get Expires On
		long expiresOn = getExpiresOn(instructions);
		return new DefaultAccessToken(token, owner, requestor, expiresOn, type,
				scope, notes, instructions);
	}

	/**
	 * Decides the expires interval of the token
	 * 
	 * @param instructions
	 * @param type
	 * @return
	 */
	private long getExpiresOn(Map<String, Object> instructions) {
		return DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(getExpiresInterval(instructions));
	}

	/**
	 * Gets the expires period of the token
	 * 
	 * @param instructions
	 * @return
	 */
	private long getExpiresInterval(Map<String, Object> instructions) {
		long expires = AuthConstants.DEFAULT_EXPIRY_INTERVAL;
		if (instructions.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL) != null) {
			expires = (long) instructions
					.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL);
		} else {
			// Put back the expire period for future
			instructions.put(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, expires);
		}
		return expires;
	}

}

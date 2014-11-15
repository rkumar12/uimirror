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
package com.uimirror.account.processor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.form.RegisterForm;
import com.uimirror.core.Processor;
import com.uimirror.core.RandomKeyGenerator;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.exceptions.ApplicationExceptionMapper;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.mail.BackgroundMailService;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.service.ValidatorService;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.ouath.client.Client;
import com.uimirror.sso.auth.APIKeyAuthentication;
import com.uimirror.sso.client.AllowAuthorizationClientProcessor;
import com.uimirror.sso.form.ClientAPIForm;
import com.uimirror.sso.token.TokenGenerator;
import com.uimirror.sso.token.store.AccessTokenStore;

/**
 * Processor for the user account creation, it will first check for the user existence
 * if not found will try to create a new else, else stop
 * Steps are as below
 * <ol>
 * <li>Validate and get the token details</li>
 * <li>Check if provided details are valid</li>
 * <li>Transform to the {@link DefaultUser}</li>
 * <li>Send verification mail in background</li>
 * </ol>
 * 
 * @author Jay
 */
public class UserRegistrationProcessor implements Processor<RegisterForm, String>{

	protected static final Logger LOG = LoggerFactory.getLogger(UserRegistrationProcessor.class);
	private @Autowired Processor<Authentication, Client> apiKeyAuthenticateProcessor;
	private @Autowired Processor<RegisterForm, DefaultUser> createUserProcessor;
	private @Autowired TransformerService<ClientAPIForm, APIKeyAuthentication> apiKeyToAuthTransformer;
	private @Autowired AccessTokenStore persistedAccessTokenMongoStore;
	private @Autowired BackgroundProcessorFactory<Map<String, Object>, Object> backgroundMailService;
	private @Autowired BackgroundProcessorFactory<AccessToken, Object> allowAuthorizationClientProcessor;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired ValidatorService<String> userRegistrationValidationService;

	public UserRegistrationProcessor() {
		// NOP
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	@MapException(use=ApplicationExceptionMapper.NAME)
	public String invoke(RegisterForm param) throws ApplicationException {
		LOG.info("[START]- Registering a new User.");
		//Step -1 Create Client
		Client client = authenticateAndGetClient(param);
		//Step -2 Validate User, If necessary, pull out the previous token and send back to the verify page
		//userRegistrationValidationService.validate(param.getEmail());
		DefaultUser user = createUserProcessor.invoke(param);
		AccessToken token = getNewToken(client, user, param);
		LOG.info("[END]- Registering a new User.");
		return jsonResponseTransFormer.doTransForm(token.eraseEsential().toResponseMap());
	}
	
	/**
	 * @param param
	 * @return
	 */
	private Client authenticateAndGetClient(RegisterForm param){
		Authentication auth = apiKeyToAuthTransformer.transform(param);
		//Client client = apiKeyAuthenticateProcessor.invoke(auth);
		//TODO fix this delete this once testing over
		Client client = new Client("12", null, null, null, null, null, null, 0l, null, null);
		return client;
	}
	
	private AccessToken getNewToken(Client client, DefaultUser user, RegisterForm form){
		String clientId = client.getClientId();
		String owner = user.getUserInfo().getProfileId();
		String ip = form.getIp();
		String userAgent = form.getUserAgent();
		grantEmailToken(clientId, owner, ip, userAgent);
		AccessToken token = grantWebToken(clientId, owner, ip, userAgent);
		return token;
	}
	
	/**
	 * @param clientId
	 * @param proifleId
	 * @param ip
	 * @param userAgent
	 * @return
	 */
	private AccessToken grantWebToken(String clientId, String proifleId, String ip, String userAgent){
		String token = RandomKeyGenerator.randomString(5);
		AccessToken access_token = grantToken(clientId, proifleId, ip, userAgent, 0l, token);
		persistedAccessTokenMongoStore.store(access_token);
		return access_token;
	}
	/**
	 * @param clientId
	 * @param proifleId
	 * @param ip
	 * @param userAgent
	 * @return
	 */
	private void grantEmailToken(String clientId, String proifleId, String ip, String userAgent){
		AccessToken token =  grantToken(clientId, proifleId, ip, userAgent, AuthConstants.DEFAULT_EXPIRY_INTERVAL, null);
		persistedAccessTokenMongoStore.store(token);
		//TODO process mail i.e for latter
		backgroundMailService.getProcessor(BackgroundMailService.NAME).invoke(null);
		allowAuthorizationClientProcessor.getProcessor(AllowAuthorizationClientProcessor.NAME).invoke(token);
	}
	
	/**
	 * @param clientId
	 * @param profileId
	 * @param ip
	 * @param userAgent
	 * @param expiresOn
	 * @param verifyToken
	 * @return
	 */
	private AccessToken grantToken(String clientId, String profileId, String ip, String userAgent, long expiresOn, String verifyToken){
		Map<String, Object> instructions = new LinkedHashMap<String, Object>(3);
		instructions.put(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, AuthConstants.DEFAULT_EXPIRY_INTERVAL);
		instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_ACCOUNT_VERIFY);
		if(verifyToken != null)
			instructions.put(AuthConstants.WEB_VERIFY_TOKEN, verifyToken);
		Map<String, Object> notes = new LinkedHashMap<String, Object>(5);
		notes.put(AuthConstants.IP, ip);
		notes.put(AuthConstants.USER_AGENT, userAgent);
		Token token = TokenGenerator.getNewOneWithOutPharse();
		TokenType type = TokenType.SECRET;
		Scope scope = Scope.READWRITE;
		String requestor = clientId;
		String owner = profileId;
		return new DefaultAccessToken(token, owner, requestor, expiresOn, type, scope, notes, instructions);
	}

}

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

import static com.uimirror.core.Constants.IP;
import static com.uimirror.core.Constants.USER_AGENT;
import static com.uimirror.core.auth.AuthConstants.DEFAULT_EMAIL_LINK_EXPIRY_INTERVAL;
import static com.uimirror.core.auth.AuthConstants.INST_NEXT_ACCOUNT_VERIFY;

import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.form.RegisterForm;
import com.uimirror.core.Processor;
import com.uimirror.core.RandomKeyGenerator;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.auth.token.DefaultAccessToken.TokenBuilder;
import com.uimirror.core.dao.RecordNotFoundException;
import com.uimirror.core.exceptions.ApplicationExceptionMapper;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.mail.BackgroundMailService;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.service.ValidatorService;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.ouath.client.Client;
import com.uimirror.ouath.client.store.ClientStore;
import com.uimirror.sso.token.TokenGenerator;
import com.uimirror.user.store.AccountTokenStore;

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
	private @Autowired ClientStore persistedClientMongoStore;
	private @Autowired Processor<RegisterForm, DefaultUser> createUserProcessor;
	private @Autowired BackgroundProcessorFactory<Map<String, Object>, Object> backgroundMailService;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired ValidatorService<String> userRegistrationValidationService;
	private @Autowired AccountTokenStore persistedAccountTokenMongoStore;

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
		//Step -1 Get the client such as mobile app or web app of uimirror
		Client client = authenticateAndGetClient(param);
		//Step -2 Validate User, If necessary, pull out the previous token and send back to the verify page
		userRegistrationValidationService.validate(param.getEmail());
		DefaultUser user = createUserProcessor.invoke(param);
		AccessToken token = issueToken(client, user, param);
		LOG.info("[END]- Registering a new User.");
		//TODO fix me, delete below line before code release
		LOG.debug("[INTERNAL]- token.eraseEsential()"+token.eraseEsential());
		return jsonResponseTransFormer.doTransForm(token.eraseEsential().toResponseMap());
	}
	
	/**
	 * @param param
	 * @return
	 */
	private Client authenticateAndGetClient(RegisterForm param){
		Client client = null;
		try{
			client = persistedClientMongoStore.findClientByApiKey(param.getClientId());
		}catch(RecordNotFoundException e){
			LOG.error("[MINOR-ERROR]- Recevied Request for user registeration from a invalid client");
			//TODO fix this delete this once testing over
			client = new Client.ClientBuilder("12").build();
		}
		return client;
	}
	
	private AccessToken issueToken(Client client, DefaultUser user, RegisterForm form){
		String clientId = client.getClientId();
		String owner = user.getUserInfo().getProfileId();
		String ip = form.getIp();
		String userAgent = form.getUserAgent();
		String verifyCode = RandomKeyGenerator.randomString(5);
		AccessToken token = grantWebToken(clientId, owner, ip, userAgent, verifyCode);
		grantEmailToken(clientId, owner, ip, userAgent, verifyCode);
		return token;
	}
	
	/**
	 * Grants a new Token to be entered in the web test box
	 * @param clientId from which source request came from
	 * @param proifleId newly created user
	 * @param ip user's source
	 * @param userAgent user's device agent
	 * @param verifyCode 
	 * @return new Token
	 */
	private AccessToken grantWebToken(String clientId, String proifleId, String ip, String userAgent, String verifyCode){
		AccessToken access_token = grantToken(clientId, proifleId, ip, userAgent, 0l, verifyCode);
		persistedAccountTokenMongoStore.store(access_token);
		return access_token;
	}
	/**
	 * Grants a email link token and send the mail to the user.
	 * @param clientId from which source request came from
	 * @param proifleId newly created user
	 * @param ip user's source
	 * @param userAgent user's device agent
	 * @param verifyCode which will be sent to the user's email
	 */
	private void grantEmailToken(String clientId, String proifleId, String ip, String userAgent, String verifyCode){
		AccessToken token =  grantToken(clientId, proifleId, ip, userAgent, DEFAULT_EMAIL_LINK_EXPIRY_INTERVAL, null);
		persistedAccountTokenMongoStore.store(token);
		//TODO process mail i.e for latter
		backgroundMailService.getProcessor(BackgroundMailService.NAME).invoke(null);
	}
	
	/**
	 * Creates a new {@link TokenType#TEMPORAL} with the provided details and store that token for the further reference
	 * @param clientId from which source request came from
	 * @param proifleId newly created user
	 * @param ip user's source
	 * @param userAgent user's device agent
	 * @param expiresOn interval for the token to get expiry
	 * @param verifyToken issued token
	 * @return new Access Token
	 */
	private AccessToken grantToken(String clientId, String profileId, String ip, String userAgent, long expiresOn, String verifyToken){
		Token token = TokenGenerator.getNewOneWithOutPharse();
		TokenBuilder tokenBuilder = new DefaultAccessToken.TokenBuilder(token);
		
		Map<String, Object> instructions = new WeakHashMap<String, Object>(3);
		instructions.put(AuthConstants.INST_NEXT_STEP, INST_NEXT_ACCOUNT_VERIFY);
		
		if(verifyToken != null)
			instructions.put(AuthConstants.WEB_VERIFY_TOKEN, verifyToken);
		tokenBuilder.addInstructions(instructions);
		Map<String, Object> notes = new WeakHashMap<String, Object>(5);
		notes.put(IP, ip);
		notes.put(USER_AGENT, userAgent);
		tokenBuilder.addNotes(notes);
		tokenBuilder.addType(TokenType.SECRET);
		tokenBuilder.addScope(Scope.READWRITE);
		tokenBuilder.addOwner(profileId);
		tokenBuilder.addClient(clientId);
		if(expiresOn > 0l){
			expiresOn = DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(expiresOn);
			tokenBuilder.addExpire(expiresOn);
		}
		return tokenBuilder.build();
	}

}

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
import static com.uimirror.core.auth.AuthConstants.DEFAULT_EXPIRY_INTERVAL;
import static com.uimirror.core.auth.AuthConstants.WEB_VERIFY_TOKEN;

import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.uimirror.account.form.VerifyForm;
import com.uimirror.account.form.VerifySource;
import com.uimirror.api.security.processor.AllowAuthorizationClientProcessor;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.auth.token.DefaultAccessToken.TokenBuilder;
import com.uimirror.core.dao.RecordNotFoundException;
import com.uimirror.core.exceptions.ApplicationExceptionMapper;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.rest.extra.UnAuthorizedException;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.sso.auth.provider.AccessTokenProvider;
import com.uimirror.sso.exception.InvalidTokenException;
import com.uimirror.sso.token.TokenGenerator;
import com.uimirror.user.processor.BackGroundCreateUserProcessor;
import com.uimirror.user.processor.InvalidateAccountTokenProcessor;
import com.uimirror.user.store.AccountTokenStore;
import com.uimirror.user.store.DefaultUserStore;

/**
 * Processor That will be responsible for the Account Verifications purpose
 * 
 * @author Jay
 */
public class VerifyUserAccountProcessor implements Processor<VerifyForm, String> {

	protected static final Logger LOG = LoggerFactory.getLogger(VerifyUserAccountProcessor.class);
	
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;
	private @Autowired AccountTokenStore persistedAccountTokenMongoStore;
	private @Autowired BackgroundProcessorFactory<String, Object> backgroundProcessorFactory;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	private @Autowired BackgroundProcessorFactory<AccessToken, Object> allowAuthorizationClientProcessor;
	private @Autowired DefaultUserStore persistedDefaultUserMongoStore;
	@Qualifier("backgroundProcessorFactory")
	private @Autowired BackgroundProcessorFactory<DefaultUser, Object> backgroundCreateUserProcessorFactory;

	/**
	 *  Verify the provided details, validate the token,
	 *  if all are correct enable his account.
	 *  generate a new token of type {@link TokenType#ACCESS}
	 *  
	 *  (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	@MapException(use=ApplicationExceptionMapper.NAME)
	public String invoke(VerifyForm param) throws ApplicationException {
		LOG.info("[START]- Verifying the Newly created user.");
		param.isValid();
		String prevToken = param.getToken();
		//Step 1- Get the previous token 
		AccessToken prevAccessToken = validateAndGetToken(param.getSource(), param.getCode(), prevToken);
		//Step 2- Get the default enabled user
		DefaultUser user = getEnabledUser(prevAccessToken.getOwner());
		//Enable or restore account in background
		backgroundCreateUserProcessorFactory.getProcessor(BackGroundCreateUserProcessor.NAME).invoke(user);
		AccessToken newAccessToken = grantClientAndissueNewAccessToken(prevAccessToken, param);
		// Invalidate the previous Token
		backgroundProcessorFactory.getProcessor(InvalidateAccountTokenProcessor.NAME).invoke(user.getProfileId());
		LOG.info("[END]- Verifying the Newly created user.");
		return jsonResponseTransFormer.doTransForm(newAccessToken.eraseEsential().toResponseMap());
	}

	/**
	 * Validates the provided token and code 
	 * @param source from where request generated
	 * @param verifyCode form code to verify
	 * @param prevToken last issued token
	 * @return earlier token
	 */
	private AccessToken validateAndGetToken(VerifySource source, String verifyCode, String prevToken) {
		AccessToken prevAccessToken = null;
		try{
			if (VerifySource.MAIL == source) {
				prevAccessToken = persistedAccountTokenMongoStore.getValid(prevToken);
			}else {
				prevAccessToken = persistedAccountTokenMongoStore.get(prevToken);
				Map<String, Object> instructionsMap = prevAccessToken.getInstructions();
				String codeFromAccessToken = (String) instructionsMap.get(WEB_VERIFY_TOKEN);
				if (!verifyCode.equals(codeFromAccessToken)) {
					throw new InvalidTokenException();
				}
			}
		}catch(RecordNotFoundException e){
			throw new UnAuthorizedException();
		}
		return prevAccessToken;
	}
	
	private DefaultUser getEnabledUser(String profileId){
		DefaultUser user = persistedDefaultUserMongoStore.enableAccount(profileId);
		if(user == null)
			throw new IllegalArgumentException("User Already verified");
		return user;
	}
	

	/**
	 * Generate a new Access Token by extending its expire. and store.
	 * 
	 * @param prev_token eariller token
	 * @param param for source and user agent
	 * @return new access token
	 */
	private AccessToken grantClientAndissueNewAccessToken(AccessToken prev_token, VerifyForm param) {
		
		Token token = TokenGenerator.getNewOneWithOutPharse();
		TokenBuilder tokenBuilder = new DefaultAccessToken.TokenBuilder(token);
		Map<String, Object> notes = new WeakHashMap<String, Object>(5);
		notes.put(USER_AGENT, param.getUserAgent());
		notes.put(IP, param.getIp());
		tokenBuilder.addNotes(notes);
		tokenBuilder.addType(TokenType.ACCESS);
		tokenBuilder.addScope(Scope.READWRITE);
		tokenBuilder.addClient(prev_token.getClient());
		tokenBuilder.addOwner(prev_token.getOwner());
		tokenBuilder.addExpire(DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(DEFAULT_EXPIRY_INTERVAL));
		AccessToken new_token =  tokenBuilder.build();
		persistedAccessTokenProvider.store(new_token);
		allowAuthorizationClientProcessor.getProcessor(AllowAuthorizationClientProcessor.NAME).invoke(prev_token);
		return new_token;
	}

}

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

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.exceptions.ApplicationExceptionMapper;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.ws.api.security.DefaultSecurityContext;

/**
 * A Manager which will validate the incoming request and try to authenticate
 * if authenticated and approaching expire will try to refresh the token and add it to the 
 * {@linkplain Authentication} as part of principal  
 * 
 * @author Jay
 */
public class AccessTokenProcessor implements Processor<ContainerRequestContext, AccessToken>{

	protected static final Logger LOG = LoggerFactory.getLogger(AccessTokenProcessor.class);
	
//	private @Autowired TransformerService<AuthenticatedHeaderForm, OAuth2Authentication> requestToAuthTransformer;
//	private @Autowired Processor<AuthenticatedHeaderForm, Authentication> authenticateAccessTokenProcessor;
//	private @Autowired AccessTokenProvider persistedAccessTokenProvider;
//	private @Autowired BackgroundProcessorFactory<String, Object> backgroundProcessorFactory;
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use=ApplicationExceptionMapper.NAME)
	public AccessToken invoke(ContainerRequestContext param) throws ApplicationException{
		LOG.debug("[START]- Authenticating the user and trying to to get the authentication details");
		//Step 1- Authenticate the provided details
		//Authentication auth = authenticateAccessTokenProcessor.invoke(param);
		//Let GC take this ASAP
		//param = null;
		//Step 2- Authenticate and issue a token if required and store the same
		//AccessToken authPrincipal = renewIfRequired((AccessToken)auth.getPrincipal());
		LOG.debug("[END]- Authenticating the user and trying to to get the authentication details.");
		AccessToken token = new DefaultAccessToken(new Token("123",null), "12", "123", 123, TokenType.ACCESS, Scope.READ);;
		token = token.eraseEsential();
		SecurityContext context = new DefaultSecurityContext(token);
		param.setSecurityContext(context);
		return token;
	}
	
	

//	/**
//	 * This will check if the {@link AccessToken} expire is below 5 mins, it will try to renew a new one,
//	 * after renewing it will store and invalidate the previous token.
//	 * 
//	 * @param auth
//	 * @return
//	 */
//	private AccessToken renewIfRequired(AccessToken token){
//		if(isRenewRequired(token)){
//			//get new one, store it and invalidate the previous one
//			AccessToken new_token = getNewToken(token);
//			persistedAccessTokenProvider.store(new_token);
//			//Invalidate the previous Token
//			backgroundProcessorFactory.getProcessor(BackGroundCreateUserProcessor.NAME).invoke(token.getToken().getToken());
//			token = new_token;
//		}
//		return token;
//	}
//	
//	/**
//	 * Checks if the access token expire is less than or equal to 5 minutes from now, then it will return <code>true</code>
//	 * else <code>false</code>
//	 * @param token
//	 * @return
//	 */
//	private boolean isRenewRequired(AccessToken token){
//		boolean renewFlag = Boolean.FALSE;
//		if(token.getExpire() <= DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(5)){
//			renewFlag = Boolean.TRUE;
//		}
//		return renewFlag; 
//	}
//	
//	/**
//	 * Generate a new Access Token by extending its expire.
//	 * @param prev_token
//	 * @return
//	 */
//	private AccessToken getNewToken(AccessToken prev_token){
//		Map<String, Object> instructions = prev_token.getInstructions();
//		Map<String, Object> notes = prev_token.getNotes();
//		Token token = TokenGenerator.getNewOneWithOutPharse();
//		TokenType type = TokenType.ACCESS;
//		Scope scope = prev_token.getScope();
//		String requestor = prev_token.getClient();
//		String owner = prev_token.getOwner();
//		//Get Expires On
//		long expiresOn = getExpiresOn(instructions);
//		return new DefaultAccessToken(token, owner, requestor, expiresOn, type, scope, notes, instructions);
//	}
//	
//	/**
//	 * Decides the expires interval of the token
//	 * @param instructions
//	 * @param type
//	 * @return
//	 */
//	private long getExpiresOn(Map<String, Object> instructions){
//		return DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(getExpiresInterval(instructions));
//	}
//	
//	/**
//	 * Gets the expires period of the token
//	 * @param instructions
//	 * @return
//	 */
//	private long getExpiresInterval(Map<String, Object> instructions){
//		long expires = AuthConstants.DEFAULT_EXPIRY_INTERVAL;
//		if(instructions.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL) != null){
//			expires = (long)instructions.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL);
//		}else{
//			//Put back the expire period for future
//			instructions.put(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, expires);
//		}
//		return expires;
//	}
//
//	/**
//	 * @param authPrincipal
//	 * @return
//	 */
//	private AccessToken clean(AccessToken token) {
//		return token.eraseEsential();
//	}
}

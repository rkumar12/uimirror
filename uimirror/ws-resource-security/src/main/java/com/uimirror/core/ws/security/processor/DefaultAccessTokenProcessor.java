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
package com.uimirror.core.ws.security.processor;

import static com.uimirror.core.Constants.IP;
import static com.uimirror.core.Constants.USER_AGENT;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.exceptions.ApplicationExceptionMapper;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.ws.security.AccessTokenProcessor;
import com.uimirror.core.ws.security.BearerTokenExtractorUtil;
import com.uimirror.core.ws.security.DefaultSecurityContext;

/**
 * A Manager which will validate the incoming request and try to authenticate
 * if authenticated and approaching expire will try to refresh the token and add it to the 
 * {@linkplain Authentication} as part of principal  
 * 
 * @author Jay
 */
public class DefaultAccessTokenProcessor implements AccessTokenProcessor{

	protected static final Logger LOG = LoggerFactory.getLogger(DefaultAccessTokenProcessor.class);
	
	//This service url is configured in properties file to point to the 
	//Authentication server
	private String serviceUrl;
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationController#getAccessToken(javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	@MapException(use=ApplicationExceptionMapper.NAME)
	public AccessToken invoke(ContainerRequestContext param) throws ApplicationException{
		LOG.debug("[START]- Authenticating the user and trying to to get the authentication details");
		//Get the token details, ip and user agent to make the web service call
		ClientCallParam clientParam = ClientCallParam.buildNewCallParam(param);
		System.out.println(clientParam);
		//TODO
		//DO validate via web service
		LOG.debug("[END]- Authenticating the user and trying to to get the authentication details.");
		//AccessToken token = new DefaultAccessToken(new Token("123",null), "12", "123", 123, TokenType.ACCESS, Scope.READ);;
		AccessToken token = new DefaultAccessToken.TokenBuilder(new Token("123",null)).
				addClient("12").addOwner("123").addExpire(123).addType(TokenType.ACCESS).addScope(Scope.READ).build();
		token = token.eraseEsential();
		SecurityContext context = new DefaultSecurityContext(token);
		param.setSecurityContext(context);
		return token;
	}
	
	/**
	 * For making the new service call for the token validation this is work like a 
	 * adaptor for the ip, userAgent and token
	 * @author Jayaram
	 */
	public static class ClientCallParam{
		private String ip;
		private String host;
		private String token;

		private ClientCallParam(String ip, String host, String token) {
			this.ip = ip;
			this.host = host;
			this.token = token;
		}

		public static ClientCallParam buildNewCallParam(ContainerRequestContext param){
			String ip = param.getHeaderString(IP);
			String host = param.getHeaderString(USER_AGENT);
			String token = BearerTokenExtractorUtil.extract(param);
			return new ClientCallParam(ip, host, token);
		}

		@Override
		public String toString() {
			StandardToStringStyle style = new StandardToStringStyle();
		    style.setFieldSeparator(", ");
		    style.setUseClassName(false);
		    style.setUseIdentityHashCode(false);
		    return new ReflectionToStringBuilder(this, style).toString();
		}
		
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

}

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
package com.uimirror.ws.api.security.util;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.uimirror.ws.api.security.bean.base.SecurityFieldConstants;

/**
 * <p>This class works as a bridge between java bean and mongo serialization</p>
 * @author Jay
 */
public class OAuth2AccessTokenUtil {

	/**
	 * <p>This will convert the <code>{@link OAuth2AccessToken}</code> to <code>{@link DBObject}</code></p>
	 * @param token
	 * @return
	 */
	public static DBObject convertToDoc(OAuth2AccessToken token){
		Assert.notNull(token, "Token Instance can't be null");
		DBObject accessToken = new BasicDBObject(10);
		accessToken.put(SecurityFieldConstants._ACCESS_TOKEN_VALUE, token.getValue());
		accessToken.put(SecurityFieldConstants._ACCESS_TOKEN_TOKENTYPE, token.getTokenType());
		if(token.getExpiration() != null){
			accessToken.put(SecurityFieldConstants._ACCESS_TOKEN_EXPIRATION, token.getExpiration());
		}
		if(token.getRefreshToken() != null && token.getRefreshToken().getValue() != null){
			accessToken.put(SecurityFieldConstants._ACCESS_TOKEN_OAUTH2REFRESH_TOKEN, token.getRefreshToken().getValue());
		}
		if(!CollectionUtils.isEmpty(token.getScope())){
			accessToken.put(SecurityFieldConstants._ACCESS_TOKEN_SCOPE, token.getScope());
		}
		if(!CollectionUtils.isEmpty(token.getAdditionalInformation())){
			accessToken.put(SecurityFieldConstants._ACCESS_TOKEN_ADD_INFO, token.getAdditionalInformation());
		}
		return accessToken;
	}
	
	/**
	 * <p>This will help to de-sereailize the <code>{@link DBObject}</code> to <code>{@link OAuth2AccessToken}</code></p>
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static OAuth2AccessToken deseralizeToBean(DBObject object){
		Assert.notNull(object, "DBObject can't be null for deserailizing");
		String value = (String)object.get(SecurityFieldConstants._ACCESS_TOKEN_VALUE);
		DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(value);
		token.setTokenType((String)object.get(SecurityFieldConstants._ACCESS_TOKEN_TOKENTYPE));
		if(object.get(SecurityFieldConstants._ACCESS_TOKEN_EXPIRATION) != null){
			token.setExpiration((Date) object.get(SecurityFieldConstants._ACCESS_TOKEN_EXPIRATION));
		}
		if(object.get(SecurityFieldConstants._ACCESS_TOKEN_SCOPE) != null){
			token.setScope((Set<String>) object.get(SecurityFieldConstants._ACCESS_TOKEN_SCOPE));
		}
		Object additionalInfo = object.get(SecurityFieldConstants._ACCESS_TOKEN_ADD_INFO);
		if (additionalInfo != null) {
			Map<String, Object> additionalInformation = ((BasicDBObject)additionalInfo).toMap();
			token.setAdditionalInformation(additionalInformation);
		}
		DefaultOAuth2RefreshToken refeshToken = new DefaultOAuth2RefreshToken((String)object.get(SecurityFieldConstants._ACCESS_TOKEN_OAUTH2REFRESH_TOKEN));
		token.setRefreshToken(refeshToken);
		return token;
		
	}
}

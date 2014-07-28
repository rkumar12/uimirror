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
package com.uimirror.ws.api.security.provider.token;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;

import com.mongodb.DBCollection;

/**
 * <p>Implementation of token services that stores tokens in a mongo database.</p>
 * @author Jay
 */
public class MongoTokenStore implements TokenStore{

	protected static final Logger LOG = LoggerFactory.getLogger(MongoTokenStore.class);
	
	private final DBCollection uimTokenCollection;
	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
	
	public MongoTokenStore(DBCollection uimTokenCollection) {
		super();
		Assert.notNull(uimTokenCollection, "Data Base Token Collection Can't be null");
		this.uimTokenCollection = uimTokenCollection;
	}
	
	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
		this.authenticationKeyGenerator = authenticationKeyGenerator;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#getAccessToken(org.springframework.security.oauth2.provider.OAuth2Authentication)
	 */
	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		OAuth2AccessToken accessToken = null;

		String key = authenticationKeyGenerator.extractKey(authentication);
		
//		try {
//			DBObject query = new BasicDBObject(SecurityFieldConstants._ID, new ObjectId(key));
//			DBObject result = uimTokenCollection.findOne();
//			accessToken = jdbcTemplate.queryForObject(selectAccessTokenFromAuthenticationSql,
//					new RowMapper<OAuth2AccessToken>() {
//						public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
//							return deserializeAccessToken(rs.getBytes(2));
//						}
//					}, key);
//		}
//		catch (EmptyResultDataAccessException e) {
//			if (LOG.isInfoEnabled()) {
//				LOG.debug("Failed to find access token for authentication " + authentication);
//			}
//		}
//		catch (IllegalArgumentException e) {
//			LOG.error("Could not extract access token for authentication " + authentication, e);
//		}
//
//		if (accessToken != null
//				&& !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
//			removeAccessToken(accessToken.getValue());
//			// Keep the store consistent (maybe the same user is represented by this authentication but the details have
//			// changed)
//			storeAccessToken(accessToken, authentication);
//		}
		return accessToken;
	}

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token,
			OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken,
			OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(
			OAuth2RefreshToken token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(
			OAuth2RefreshToken refreshToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(
			String clientId, String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		// TODO Auto-generated method stub
		return null;
	}

}

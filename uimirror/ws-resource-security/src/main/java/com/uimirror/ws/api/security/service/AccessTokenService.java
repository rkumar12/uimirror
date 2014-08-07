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
package com.uimirror.ws.api.security.service;

import com.uimirror.ws.api.security.bean.base.AccessToken;
import com.uimirror.ws.api.security.exception.AccessTokenException;
import com.uimirror.ws.api.security.exception.ClientException;


/**
 * <p>Service for the AccessToken resource related activity such as finding token, expiring etc</p>
 * @author Jayaram
 * @since 1.0
 */
public interface AccessTokenService {

	/**
	 * <p>This will load the Access Key, client and user info 
	 * from the cache or the storage
	 * system, based on the availability by access_key</p>
	 * @param apiKey
	 * @return
	 * @throws AccessTokenException if no token found, error code 404 for if no token exist else 500 for Database error
	 * @throws IllegalArgumentException if input_parameter is not correct
	 */
	public AccessToken getAccessTokenDetailsByTokenId(final String token) throws AccessTokenException, IllegalArgumentException, ClientException;
	
	/**
	 * <p>This is similar to the {@link #getAccessTokenDetailsByTokenId(String)}, where as this will return if token is valid</p>
	 * @param token
	 * @return {@link AccessToken} if a valid token found
	 * @throws AccessTokenException if no valid token found, error code 404 for if no token exist, 419 if token is expired else 500 for Database error
	 * @throws IllegalArgumentException if input_parameter is not correct
	 * @throws ClientException while reteriving client info
	 */
	public AccessToken getActiveAccessTokenDetailsByTokenId(final String token) throws AccessTokenException, IllegalArgumentException, ClientException;
	
	/**
	 * <p>This will load the Access Key, client_id and user_id 
	 * from the cache or the storage
	 * system, based on the availability by access_key</p>
	 * @param apiKey
	 * @return
	 * @throws AccessTokenException if no token found, error code 404 for if no token exist else 500 for Database error
	 * @throws IllegalArgumentException if input_parameter is not correct
	 */
	public AccessToken getAccessTokenByTokenId(final String token) throws AccessTokenException, IllegalArgumentException;
	
	/**
	 * <p>This is similar to the {@link #getAccessTokenByTokenId(String)}, where as this will return if token is valid</p>
	 * @param token
	 * @return {@link AccessToken} if a valid token found
	 * @throws AccessTokenException if no valid token found, error code 404 for if no token exist, 419 if token is expired else 500 for Database error
	 * @throws IllegalArgumentException if input_parameter is not correct
	 */
	public AccessToken getActiveAccessTokenByTokenId(final String token) throws AccessTokenException, IllegalArgumentException;
	
	/**
	 * <p>This will mark the token as expired for the given token id</p>
	 * @param token
	 * @throws IllegalArgumentException if input_parameter is not correct
	 * @throws AccessTokenException error code 500, if some db issue
	 */
	public void markTokenAsExpired(final String token) throws AccessTokenException, IllegalArgumentException;
	
	/**
	 * <p>This will expire the access token for the client </p>
	 * @param clientId
	 * @throws IllegalArgumentException if input_parameter is not correct
	 * @throws AccessTokenException error code 500, if some db issue
	 */
	public void expireAllClinetToken(final String clientId) throws IllegalArgumentException, AccessTokenException;
	
	/**
	 * <p>This will expire all the access token issued to any client for this user </p>
	 * @param userId
	 * @throws IllegalArgumentException if input_parameter is not correct
	 * @throws AccessTokenException error code 500, if some db issue
	 */
	public void expireAllUserToken(final String userId) throws IllegalArgumentException, AccessTokenException;
	
	
}

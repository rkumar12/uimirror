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
package com.uimirror.ws.api.security.repo;

import java.util.List;

import com.uimirror.ws.api.security.bean.base.AccessToken;
import com.uimirror.ws.api.security.bean.base.Client;

/**
 * <p>This will get the access token Details</p>
 * @author Jay
 */
public interface AccessTokenDao {
	
	public static final String OUATH_DB = "uim_ouath";
	public static final String ACCESS_TOKEN_COLLECTION = "accesstoken";
	
	/**
	 * <p>This will save the access-token</p>
	 * <p>It assumes the document coming for the saving is final and it stores as it is
	 * , it will not perform any validation, its the the job of the caller needs to validate before requesting for save</p>
	 * <p>How ever, it checks, provided argument shouldn't be empty, else {@link IllegalArgumentException} </p>
	 * @param accessToken
	 * @throws IllegalArgumentException if input parameter is null
	 */
	public void insert(AccessToken accessToken) throws IllegalArgumentException;
	
	/**
	 * <p>Find the Access Token by token</p>
	 * <p>This search as a exact token search, it avoids the regular expression search for various reason such as 
	 * why its required?</p>
	 * @param token token_id to be searched 
	 * @return <code>{@link AccessToken}</code>
	 * @throws IllegalArgumentException if input parameter is null
	 */
	public AccessToken findByToken(String token) throws IllegalArgumentException;
	
	/**
	 * <p>Find All the access Token available<p/>
	 * @return {@link List} of {@link AccessToken}
	 */
	public List<AccessToken> findAll();
	
	/**
	 * <pFind/search list of {@link AccessToken} By Client Id</p>
	 * @param clientId
	 * @return
	 * @throws IllegalArgumentException if input parameter is null
	 */
	public List<AccessToken> findByClientId(String clientId) throws IllegalArgumentException;
	
	/**
	 * <p>Find/Search List of {@link AccessToken} by User Id</p>
	 * @param userId
	 * @return
	 * @throws IllegalArgumentException if input parameter is null
	 */
	public List<AccessToken> findByUserId(String userId) throws IllegalArgumentException;
	
	/**
	 * <p>Mark a {@link AccessToken} as invalid as its expired</p>
	 * @param token
	 * @throws IllegalArgumentException if input parameter is null
	 */
	public void markAsExpired(String token) throws IllegalArgumentException;
	
	/**
	 * <p>This will expire all token issued to a client for all other user</p>
	 * @param clientid
	 * @throws IllegalArgumentException if input parameter is null
	 */
	public void expireAllTokenForClient(String clientid) throws IllegalArgumentException;
	
	/**
	 * <p>This will expire all token issued to different client for a specific user</p>
	 * @param userId
	 * @throws IllegalArgumentException if input parameter is null
	 */
	public void expireAllTokenForUser(String userId) throws IllegalArgumentException;
	
	/**
	 * <p>delete a {@link AccessToken} by token_id</p>
	 * @param token
	 * @throws IllegalArgumentException if input parameter is null
	 */
	public void deleteByToken(String token) throws IllegalArgumentException;
	
	/**
	 * <p>Delete all the {@link AccessToken} issued for the {@link Client} by clinet_id</p>
	 * @param clientId
	 * @throws IllegalArgumentException if input parameter is null
	 */
	public void deleteByClientId(String clientId) throws IllegalArgumentException;
	
	/**
	 * <p>Delete all the {@link AccessToken} issued for any {@link Client} by user_id</p></p>
	 * @param userId
	 * @throws IllegalArgumentException if input parameter is null
	 */
	public void deleteByUserId(String userId) throws IllegalArgumentException;

}

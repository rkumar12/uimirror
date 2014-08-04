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
	
	/**
	 * <p>This will save the access-token</p>
	 * <p>It assumes the document coming for the saving is final and it stores as it is
	 * , it will not perform any validation, its the the job of the caller needs to validate before requesting for save</p>
	 * <p>How ever, it checks, provided argument shouldn't be empty, else {@link IllegalArgumentException} </p>
	 * @param accessToken
	 */
	public void insert(AccessToken accessToken);
	
	/**
	 * <p>Find the Access Token by token</p>
	 * <p>This search as a exact token search, it avoids the regular expression search for various reason such as 
	 * why its required?</p>
	 * @param token token_id to be searched 
	 * @return <code>{@link AccessToken}</code>
	 */
	public AccessToken findByToken(String token);
	
	/**
	 * <p>Find All the access Token available<p/>
	 * @return {@link List} of {@link AccessToken}
	 */
	public List<AccessToken> findAll();
	
	/**
	 * <pFind/search list of {@link AccessToken} By Client Id</p>
	 * @param clientId
	 * @return
	 */
	public List<AccessToken> findByClientId(String clientId);
	
	/**
	 * <p>Find/Search List of {@link AccessToken} by User Id</p>
	 * @param userId
	 * @return
	 */
	public List<AccessToken> findByUserId(String userId);
	
	/**
	 * <p>Mark a {@link AccessToken} as invalid as its expired</p>
	 * @param token
	 */
	public void markAsExpired(String token);
	
	/**
	 * <p>This will expire all token issued to a client for all other user</p>
	 * @param clientid
	 */
	public void expireAllTokenForClient(String clientid);
	
	/**
	 * <p>This will expire all token issued to different client for a specific user</p>
	 * @param userId
	 */
	public void expireAllTokenForUser(String userId);
	
	/**
	 * <p>delete a {@link AccessToken} by token_id</p>
	 * @param token
	 */
	public void deleteByToken(String token);
	
	/**
	 * <p>Delete all the {@link AccessToken} issued for the {@link Client} by clinet_id</p>
	 * @param clientId
	 */
	public void deleteByClientId(String clientId);
	
	/**
	 * <p>Delete all the {@link AccessToken} issued for any {@link Client} by user_id</p></p>
	 * @param userId
	 */
	public void deleteByUserId(String userId);

}

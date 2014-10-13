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
package com.uimirror.auth.user;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.auth.Scope;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * A basic document which has relations like,
 * when user has granted the access to the client and what scope client has 
 * accordingly during new request access token, user will be promoted for the same.
 * @author Jay
 */
public class UserAuthorizedClients extends BeanBasedDocument<UserAuthorizedClients> implements BeanValidatorService{

	private static final long serialVersionUID = 8725514681944084516L;
	private List<ClientAuthorizedScope> clients;

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public UserAuthorizedClients initFromMap(Map<String, Object> src) {
		//Validate the source shouldn't be empty
		validateSource(src);
		//Initialize the state
		init(src);
		return this;
	}

	/**
	 * Initialize the state of the object from the given source {@link Map}
	 * @param src
	 */
	private void init(Map<String, Object> src) {
		this.setId((String)src.get(UserAuthorizedClientDBFields.ID));
		List<Map<String, Object>> clients =  (List<Map<String, Object>>)src.get(UserAuthorizedClientDBFields.CLIENTS);
		
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(!StringUtils.hasText(getProfileId()))
			valid = Boolean.FALSE;
		if(CollectionUtils.isEmpty(getClients()))
			valid = Boolean.FALSE;
		return valid;
	}

	/**
	 * Gets the List of client that has 
	 * @return
	 */
	public List<ClientAuthorizedScope> getClients() {
		return clients;
	}
	
	public String getProfileId(){
		return getId();
	}
	
	/**
	 * gets the matching client ID
	 * @return
	 */
	public String getMatchingClientId(){
		if(isMatched())
			return getClients().get(0).getClientId();
		else
			return null;
	}
	
	/**
	 * Gets the matching clients scope
	 * @return
	 */
	public Scope getMatchingClientScope(){
		if(isMatched())
			return getClients().get(0).getScope();
		else
			return null;
	}
	
	/**
	 * Checks if the returned client has any matching clients
	 * @return
	 */
	private boolean isMatched(){
		return !CollectionUtils.isEmpty(getClients()) && getClients().size() == 1;
	}
	
}

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
package com.uimirror.sso.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.auth.Scope;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * A basic document which has relations like,
 * when user has granted the access to the client and what scope client has 
 * accordingly during new request access token, user will be promoted for the same.
 * @author Jay
 */
public class UserAuthorizedClient extends AbstractBeanBasedDocument<UserAuthorizedClient> implements BeanValidatorService{

	private static final long serialVersionUID = 8725514681944084516L;
	private List<ClientAuthorizedScope> clients;
	
	//DOn't Use this until it has specific requirement
	public UserAuthorizedClient(){
		//NOP
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public UserAuthorizedClient readFromMap(Map<String, Object> src) {
		//Validate the source shouldn't be empty
		isValidSource(src);
		//Initialize the state
		return init(src);
	}
	
	/**
	 * @param id
	 * @param clients
	 * @param on
	 */
	public UserAuthorizedClient(String id, List<ClientAuthorizedScope> clients){
		this.setId(id);
		this.clients = clients;
	}

	/**
	 * Initialize the state of the object from the given source {@link Map}
	 * @param src
	 */
	@SuppressWarnings("unchecked")
	private UserAuthorizedClient init(Map<String, Object> src) {
		String id = (String)src.get(UserAuthorizedClientDBFields.ID);
		List<Map<String, Object>> clientsMap =  (List<Map<String, Object>>)src.get(UserAuthorizedClientDBFields.CLIENTS);
		UserAuthorizedClient authorizedClients = null;
		if(clientsMap != null){
			List<ClientAuthorizedScope> clients = new ArrayList<ClientAuthorizedScope>(clientsMap.size()+5);
			clientsMap.forEach((map) -> {
				ClientAuthorizedScope ca = new ClientAuthorizedScope((String)map.get(UserAuthorizedClientDBFields.CLIENT_ID)
						, (String)map.get(UserAuthorizedClientDBFields.SCOPE), (long)src.getOrDefault(UserAuthorizedClientDBFields.ON, 0l));
				clients.add(ca);
			});
			authorizedClients = new UserAuthorizedClient(id, clients);
		}else{
			authorizedClients = new UserAuthorizedClient(id, null);
		}
		
		return authorizedClients;
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
		return clients == null ? new ArrayList<ClientAuthorizedScope>(5) : clients;
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
		return getMathcingClient() == null ? null : getMathcingClient().getScope();
	}
	
	/**
	 * Returns the matching client by given query
	 * @return
	 */
	public ClientAuthorizedScope getMathcingClient(){
		if(isMatched())
			return getClients().get(0);
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
	
	/**
	 * This map the document should have which fields
	 * always it will have _id
	 */
	@Override
	public Map<String, Object> writeToMap(){
		//First check if it represents a valid state then can be serialized
		if(!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/**
	 * Creates the {@link Map} that will be serialized over the network
	 * @return
	 */
	private Map<String, Object> serailize() {
		Map<String, Object> map = new WeakHashMap<String, Object>(10);
		map.put(UserAuthorizedClientDBFields.ID, this.getId());
		List<Map<String, Object>> clients = new ArrayList<Map<String,Object>>(getClients().size());
		
		getClients().forEach((client) -> {
			Map<String, Object> c = new WeakHashMap<String, Object>();
			c.put(UserAuthorizedClientDBFields.CLIENT_ID, client.getClientId());
			c.put(UserAuthorizedClientDBFields.SCOPE, client.getScope().getScope());
			c.put(UserAuthorizedClientDBFields.ON, client.getOn());
			clients.add(c);
		});
		map.put(UserAuthorizedClientDBFields.CLIENTS, clients);
		return map;
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

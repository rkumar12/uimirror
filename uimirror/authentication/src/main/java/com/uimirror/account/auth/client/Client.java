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
package com.uimirror.account.auth.client;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.user.AccountStatus;

/**
 * A basic client , which will have at minimum client id,
 * API key, client secret, redirect URL  
 * @author Jay
 */
public class Client extends BeanBasedDocument<Client> implements BeanValidatorService{

	private static final long serialVersionUID = -5074118579759365950L;
	private String name;
	private String secret;
	private String redirectURI;
	private AccountStatus status;
	private String apiKey;
	private long registeredOn;
	private String registeredBy;
	private Map<String, Object> details;

	//DOn't Use this until it has specific requirement
	public Client(){
		super();
	}
	public Client(Map<String, Object> map) {
		super(map);
	}

	/**
	 * @param id
	 * @param name
	 * @param secret
	 * @param redirectURI
	 * @param status
	 * @param apiKey
	 * @param registeredOn
	 * @param registeredBy
	 * @param details
	 */
	public Client(String id, String name, String secret, String redirectURI,
			AccountStatus status, String apiKey, long registeredOn,
			String registeredBy, Map<String, Object> details) {
		super(id);
		this.name = name;
		this.secret = secret;
		this.redirectURI = redirectURI;
		this.status = status;
		this.apiKey = apiKey;
		this.registeredOn = registeredOn;
		this.registeredBy = registeredBy;
		this.details = details;
	}



	public String getClientId(){
		return getId();
	}

	public String getSecret() {
		return secret;
	}

	public AccountStatus getStatus() {
		return status == null ? AccountStatus.ACTIEVE : this.status;
	}
	
	public String getName() {
		return name;
	}

	public String getRedirectURI() {
		return redirectURI;
	}

	public String getApiKey() {
		return apiKey;
	}

	public long getRegisteredOn() {
		return registeredOn;
	}

	public String getRegisteredBy() {
		return registeredBy;
	}

	public Map<String, Object> getDetails() {
		return details == null ? new LinkedHashMap<String, Object>(5) : this.details;
	}
	
	/**
	 * Update the provided details, if any new details to add,
	 * it will create a new instance and returned
	 * @param details
	 * @return
	 */
	public Client updateDetails(Map<String, Object> details){
		if(CollectionUtils.isEmpty(details))
			return this;
			
		details.putAll(this.getDetails());
		return new Client(this.getId(), this.name, this.secret, this.redirectURI, this.status, this.apiKey, this.registeredOn, this.registeredBy, details);
		
	}
	

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public Client initFromMap(Map<String, Object> src) {
		//Validate the source shouldn't be empty
		validateSource(src);
		//Initialize the state
		return init(src);
	}
	
	
	
	@SuppressWarnings("unchecked")
	private Client init(Map<String, Object> src){
		String id = (String)src.get(ClientDBFields.ID);
		String name = (String)src.get(ClientDBFields.NAME);
		String secret = (String)src.get(ClientDBFields.SECRET);
		String redirectURI = (String)src.get(ClientDBFields.REDIRECT_URI);
		String st = (String)src.get(ClientDBFields.STATUS);
		AccountStatus status = st == null ? null : AccountStatus.getEnum(st);
		String apiKey = (String)src.get(ClientDBFields.API_KEY);
		String  registeredBy = (String)src.get(ClientDBFields.REGISTERED_BY);
		long registeredOn = 0l;
		if(src.get(ClientDBFields.REGISTERED_ON) != null){
			registeredOn = (long)src.get(ClientDBFields.REGISTERED_ON);
		}
		Map<String, Object> details = (Map<String, Object>)src.get(ClientDBFields.DETAILS);
		return new Client(id, name, secret, redirectURI, status, apiKey, registeredOn, registeredBy, details);
	}

	/** 
	 * Checks the necessary fields that needs to be present to demonstrate a state of the client. 
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(!StringUtils.hasText(getId()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getName()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getSecret()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getRedirectURI()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getApiKey()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getRegisteredBy()))
			valid = Boolean.FALSE;
		if(getRegisteredOn() <= 0l)
			valid = Boolean.FALSE;
		return valid;
	}

	/**
	 * Create a map that needs to be persisted
	 * @return
	 * @throws IllegalStateException 
	 */
	@Override
	public Map<String, Object> toMap() throws IllegalStateException{
		//First check if it represents a valid state then can be serialized
		if(!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * @return
	 */
	public Map<String, Object> serailize(){
		Map<String, Object> state = new LinkedHashMap<String, Object>(16);
		state.put(ClientDBFields.ID, getId());
		state.put(ClientDBFields.NAME, getName());
		state.put(ClientDBFields.SECRET, getSecret());
		state.put(ClientDBFields.STATUS, getStatus().getStatus());
		state.put(ClientDBFields.REDIRECT_URI, getRedirectURI());
		state.put(ClientDBFields.API_KEY, getApiKey());
		state.put(ClientDBFields.REGISTERED_BY, getRegisteredBy());
		state.put(ClientDBFields.REGISTERED_ON, getRegisteredOn());
		if(!CollectionUtils.isEmpty(getDetails()))
			state.put(ClientDBFields.DETAILS, getDetails());
		return state;
	}
}

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
package com.uimirror.core.client;

import static com.uimirror.core.client.ClientDBFields.API_KEY;
import static com.uimirror.core.client.ClientDBFields.APP_URL;
import static com.uimirror.core.client.ClientDBFields.DETAILS;
import static com.uimirror.core.client.ClientDBFields.IMAGE;
import static com.uimirror.core.client.ClientDBFields.NAME;
import static com.uimirror.core.client.ClientDBFields.REDIRECT_URI;
import static com.uimirror.core.client.ClientDBFields.REGISTERED_BY;
import static com.uimirror.core.client.ClientDBFields.REGISTERED_ON;
import static com.uimirror.core.client.ClientDBFields.SECRET;
import static com.uimirror.core.client.ClientDBFields.STATUS;
import static com.uimirror.core.mongo.feature.BasicDBFields.ID;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.AccountStatus;
import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.util.web.WebUtil;

/**
 * A basic client , which will have at minimum client id,
 * API key, client secret, redirect URL
 *   
 * @author Jay
 */
public final class Client extends AbstractBeanBasedDocument<Client> implements BeanValidatorService{

	private static final long serialVersionUID = -5074118579759365950L;
	private String name;
	private String secret;
	private String redirectURI;
	private String appURL;
	private AccountStatus status;
	private String apiKey;
	private long registeredOn;
	private String registeredById;
	private String image;
	private Map<String, Object> details;

	//DOn't Use this until it has specific requirement
	public Client(){
		super();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.AbstractBeanBasedDocument#updateId(java.lang.String)
	 */
	@Override
	public Client updateId(String id){
		if(!StringUtils.hasText(id)){
			throw new IllegalArgumentException("Can't update invalid ID");
		}
		return new ClientBuilder(id).
				addApiKey(apiKey).
				addAppURL(appURL).
				addDetails(details).
				addImagePath(image).
				addName(name).
				addRedirectURI(redirectURI).
				addRegisteredBy(registeredById).
				addRegisteredOn(registeredOn).
				addSecret(secret).
				addStatus(status).build();
	}
	
	/**
	 * Update the provided details, if any new details to add,
	 * it will create a new instance and returned
	 * @param details needs to be upsert
	 * @param upsert flags suggest to replace all value or update only
	 * @return updated client
	 */
	public Client updateDetails(Map<String, Object> details, boolean upsert){
		if(CollectionUtils.isEmpty(details))
			return this;
		if(upsert)
			this.details = details;
		else{
			this.details = getDetails();
			this.details.putAll(details);
		}
		return new ClientBuilder(getClientId()).
				addApiKey(apiKey).
				addAppURL(appURL).
				addDetails(details).
				addImagePath(image).
				addName(name).
				addRedirectURI(redirectURI).
				addRegisteredBy(registeredById).
				addRegisteredOn(registeredOn).
				addSecret(secret).
				addStatus(status).build();
	}
	
	/**
	 * Checks if the client is in {@link AccountStatus#ACTIVE} status
	 * @return <code>true</code> if active else <code>false</code>
	 */
	public boolean isActive(){
		return AccountStatus.ACTIVE == status;
	}
	
	/**
	 * Checks if client has valid app and redirect URL
	 * @return <code>true</code> if valid else <code>false</code>
	 */
	public boolean isValidAppAndRedirectURL(){
		return WebUtil.isValidAppAndRedirectURL(appURL, redirectURI);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public Client readFromMap(Map<String, Object> src) {
		//Validate the source shouldn't be empty
		isValidSource(src);
		//Initialize the state
		return init(src);
	}
	
	private Client init(Map<String, Object> src){
		String id = (String)src.get(ID);
		String name = (String)src.get(NAME);
		String secret = (String)src.get(SECRET);
		String redirectURI = (String)src.get(REDIRECT_URI);
		String appURL = (String)src.get(APP_URL);
		String st = (String)src.get(STATUS);
		AccountStatus status = st == null ? null : AccountStatus.getEnum(st);
		String apiKey = (String)src.get(API_KEY);
		String  registeredById = (String)src.get(REGISTERED_BY);
		String  image = (String)src.get(ClientDBFields.IMAGE);
		long registeredOn = (long)src.getOrDefault(REGISTERED_ON, 0l);
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)src.get(DETAILS);
		return new ClientBuilder(id).
				addApiKey(apiKey).
				addAppURL(appURL).
				addDetails(details).
				addImagePath(image).
				addName(name).
				addRedirectURI(redirectURI).
				addRegisteredBy(registeredById).
				addRegisteredOn(registeredOn).
				addSecret(secret).
				addStatus(status).build();
	}
	
	/**
	 * Create a map that needs to be persisted
	 * @return a map of state of object
	 * @throws IllegalStateException when object state is invalid
	 */
	@Override
	public Map<String, Object> writeToMap() throws IllegalStateException{
		//First check if it represents a valid state then can be serialized
		if(!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}

	/** 
	 * Checks the necessary fields that needs to be present to demonstrate a state of the client. 
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(!StringUtils.hasText(getName()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getSecret()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getRedirectURI()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getApiKey()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getRegisteredById()))
			valid = Boolean.FALSE;
		if(getRegisteredOn() <= 0l)
			valid = Boolean.FALSE;
		return valid;
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * @return a map of state
	 */
	public Map<String, Object> serailize(){
		Map<String, Object> state = new WeakHashMap<String, Object>(16);
		if(StringUtils.hasText(getId()))
			state.put(ID, getId());
		state.put(NAME, getName());
		state.put(SECRET, getSecret());
		state.put(STATUS, getStatus().getStatus());
		state.put(REDIRECT_URI, getRedirectURI());
		state.put(APP_URL, getAppURL());
		state.put(API_KEY, getApiKey());
		state.put(REGISTERED_BY, getRegisteredById());
		state.put(REGISTERED_ON, getRegisteredOn());
		state.put(IMAGE, getImage());
		if(!CollectionUtils.isEmpty(getDetails()))
			state.put(DETAILS, getDetails());
		return state;
	}
	
	public static class ClientBuilder implements Builder<Client>{
		
		private String clientId;
		private String name;
		private String secret;
		private String redirectURI;
		private String appURL;
		private AccountStatus status;
		private String apiKey;
		private long registeredOn;
		private String registeredById;
		private String image;
		private Map<String, Object> details;
		
		public ClientBuilder(String clientId){
			this.clientId = clientId;
		}
		
		public ClientBuilder addName(String name){
			this.name = name;
			return this;
		}

		public ClientBuilder addSecret(String secretkey){
			this.secret = secretkey;
			return this;
		}

		public ClientBuilder addRedirectURI(String redirectURI){
			this.redirectURI = redirectURI;
			return this;
		}
		
		public ClientBuilder addAppURL(String appURL){
			this.appURL = appURL;
			return this;
		}

		public ClientBuilder addStatus(String status){
			if(!StringUtils.hasText(status))
				throw new IllegalArgumentException("Status is invalid");
			this.status = AccountStatus.getEnum(status);
			return this;
		}

		public ClientBuilder addStatus(AccountStatus status){
			this.status = status;
			return this;
		}

		public ClientBuilder addApiKey(String apiKey){
			this.apiKey = apiKey;
			return this;
		}
		
		public ClientBuilder addRegisteredOn(long registeredOn){
			this.registeredOn = registeredOn;
			return this;
		}
		
		public ClientBuilder addRegisteredBy(String registeredBy){
			this.registeredById = registeredBy;
			return this;
		}
		
		public ClientBuilder addImagePath(String image){
			this.image = image;
			return this;
		}
		
		public ClientBuilder addDetails(Map<String, Object> details){
			this.details = details;
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public Client build() {
			return new Client(this);
		}
		
	}
	
	private Client(ClientBuilder builder){
		Assert.notNull(builder, "Can't create a state");
		super.setId(builder.clientId);
		this.name = builder.name;
		this.secret = builder.secret;
		this.redirectURI = builder.redirectURI;
		this.appURL = builder.appURL;
		this.status = builder.status;
		this.apiKey = builder.apiKey;
		this.registeredOn = builder.registeredOn;
		this.registeredById = builder.registeredById;
		this.image = builder.image;
		this.details = builder.details;
	}
	
	public String getClientId(){
		return getId();
	}

	public String getName() {
		return name;
	}


	public String getSecret() {
		return secret;
	}


	public String getRedirectURI() {
		return redirectURI;
	}


	public String getAppURL() {
		return appURL;
	}


	public AccountStatus getStatus() {
		return status;
	}


	public String getApiKey() {
		return apiKey;
	}


	public long getRegisteredOn() {
		return registeredOn;
	}


	public String getRegisteredById() {
		return registeredById;
	}


	public String getImage() {
		return image;
	}


	public Map<String, Object> getDetails() {
		return details == null ? new WeakHashMap<String, Object>() : details;
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

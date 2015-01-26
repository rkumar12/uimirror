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
package com.uimirror.core.user;

import static com.uimirror.core.mongo.feature.BasicDBFields.ID;
import static com.uimirror.core.user.UserAuthDBFields.PASSWORD;
import static com.uimirror.core.user.UserAuthDBFields.USER_ID;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * contains the login details of the person.
 * These details will be saved in the DB uim_usr schema with the collection name user_credential
 * @author Jay
 */
public class UserCredential extends AbstractBeanBasedDocument<UserCredential> implements BeanValidatorService{

	private static final long serialVersionUID = 2893855400129476502L;
	private List<String> userNames;
	private List<String> passwords;
	
	
	public UserCredential() {
		//NOP
	}
	
	private UserCredential(UserCredentailBuilder builder){
		
		super(builder.profileId);
		this.userNames = builder.userNames;
		this.passwords = builder.passwords;
	}

	@Override
	public UserCredential updateId(String id) {
		return new UserCredentailBuilder(id)
				.addUserNames(userNames)
				.addPasswords(passwords)
				.build();
	}
	
	/**
	 * Create a map that needs to be persisted
	 * @return state in a {@link Map}
	 * @throws IllegalStateException  when the state is not valid
	 */
	@Override
	public Map<String, Object> writeToMap() throws IllegalStateException{
		//First check if it represents a valid state then can be serialized
		if(!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * @return serialized {@link Map}
	 */
	public Map<String, Object> serailize(){
		Map<String, Object> state = new LinkedHashMap<String, Object>(16);
		state.put(ID, getId());
		state.put(USER_ID, getUserNames());
		state.put(PASSWORD, getPasswords());
		return state;
	}


	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public UserCredential readFromMap(Map<String, Object> src) {
		//Validate the source shouldn't be empty
		isValidSource(src);
		//Initialize the state
		return init(src);
	}
	
	@SuppressWarnings("unchecked")
	private UserCredential init(Map<String, Object> raw){
		String id = (String)raw.get(ID);
		List<String> userNames = (List<String>) raw.get(USER_ID);
		List<String> passwords = (List<String>)raw.get(PASSWORD);
		UserCredentailBuilder build = new UserCredentailBuilder(id)
		.addUserNames(userNames)
		.addPasswords(passwords);
		return build.build();
	}



	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(CollectionUtils.isEmpty(getUserNames()))
			valid = Boolean.FALSE;
		if(!CollectionUtils.isEmpty(getPasswords()))
			valid = Boolean.FALSE;
		
		return valid;
	}
	
	public static class UserCredentailBuilder implements Builder<UserCredential>{

		private String profileId;
		private List<String> userNames;
		private List<String> passwords;
		
		public UserCredentailBuilder(String profileId) {
			this.profileId = profileId;
		}
		
		public UserCredentailBuilder addUserNames(List<String> userNames){
			this.userNames = userNames;
			return this;
		}
		
		public UserCredentailBuilder addPasswords(List<String> passwords){
			this.passwords = passwords;
			return this;
		}
		
		
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public UserCredential build() {
			// TODO Auto-generated method stub
			return new UserCredential(this);
		}
		
	}
	
	public String getProfileId() {
		return getId();
	}

	public List<String> getUserNames() {
		return userNames;
	}
	
	public List<String> getPasswords() {
		return passwords;
	}
	
	

}

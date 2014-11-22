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

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.uimirror.core.auth.Scope;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.util.DateTimeUtil;

/**
 * Basic class that user has the granted access to the client
 * it specifies which type of access user has granted
 * @author Jay
 */
public class ClientAuthorizedScope implements BeanValidatorService{

	private String clientId;
	private Scope scope;
	private long on;

	/**
	 * Creates the instance of the authorized client scope.
	 * @param clientId of the client
	 * @param scope which scope has been assigned
	 * @param on which date it has been granted
	 */
	public ClientAuthorizedScope(String clientId, Scope scope, long on) {
		Assert.hasText(clientId, "Client Id is required");
		Assert.notNull(scope, "Scope can't be empty");
		this.clientId = clientId;
		this.scope = scope;
		this.on = on;
	}
	
	/**
	 * Gets the current approval with the given scope for the given client.
	 * @param clientId of the client
	 * @param scope which operations client can perform
	 * @return instance of {@link ClientAuthorizedScope}
	 */
	public static ClientAuthorizedScope approveClient(String clientId, Scope scope){
		return new ClientAuthorizedScope(clientId, scope, DateTimeUtil.getCurrentSystemUTCEpoch());
	}
	
	/**
	 * @param clientId of the client
	 * @param scope which has been assigned to the client
	 * @param on which date it has been granted
	 */
	public ClientAuthorizedScope(String clientId, String scope, long on) {
		this.clientId = clientId;
		if(StringUtils.hasText(scope))
			this.scope = Scope.getEnum(scope);
		this.on = on;
	}
	
	/**
	 * Converts the current state to the serailizable map
	 * @return a map instance
	 */
	public Map<String, Object> toMap(){
		Map<String, Object> clients = new WeakHashMap<String, Object>(5);
		clients.put(UserAuthorizedClientDBFields.CLIENT_ID, getClientId());
		clients.put(UserAuthorizedClientDBFields.SCOPE, getScope().getScope());
		clients.put(UserAuthorizedClientDBFields.ON, getOn());
		return clients;
	}
	
	public String getClientId() {
		return clientId;
	}
	public Scope getScope() {
		return scope;
	}

	public long getOn() {
		return on;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(!StringUtils.hasText(getClientId()))
			valid = Boolean.FALSE;
		if(getScope() == null)
			valid = Boolean.FALSE;
		if(getOn() <= 0l)
			valid = Boolean.FALSE;
		return valid;
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
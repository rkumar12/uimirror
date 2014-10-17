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

import java.util.LinkedHashMap;
import java.util.Map;

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
	 * @param clientId
	 * @param scope
	 * @param on
	 */
	public ClientAuthorizedScope(String clientId, Scope scope, long on) {
		super();
		this.clientId = clientId;
		this.scope = scope;
		this.on = on;
	}
	
	/**
	 * @param clientId
	 * @param scope
	 */
	public ClientAuthorizedScope(String clientId, Scope scope) {
		super();
		this.clientId = clientId;
		this.scope = scope;
		this.on = DateTimeUtil.getCurrentSystemUTCEpoch();
	}
	
	/**
	 * @param clientId
	 * @param scope
	 * @param on
	 */
	public ClientAuthorizedScope(String clientId, String scope, long on) {
		super();
		this.clientId = clientId;
		if(StringUtils.hasText(scope))
			this.scope = Scope.getEnum(scope);
		this.on = on;
	}
	
	/**
	 * @param clientId
	 * @param scope
	 */
	public ClientAuthorizedScope(String clientId, String scope) {
		super();
		this.clientId = clientId;
		if(StringUtils.hasText(scope))
			this.scope = Scope.getEnum(scope);
		this.on = DateTimeUtil.getCurrentSystemUTCEpoch();
	}
	
	/**
	 * Converts the current state to the serailizable map
	 * @return
	 */
	public Map<String, Object> toMap(){
		Map<String, Object> clients = new LinkedHashMap<String, Object>(5);
		clients.put(UserAuthorizedClientDBFields.CLIENT_ID, getClientId());
		clients.put(UserAuthorizedClientDBFields.SCOPE, getScope());
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
	
}
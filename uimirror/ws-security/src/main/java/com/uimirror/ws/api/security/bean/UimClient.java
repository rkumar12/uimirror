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
package com.uimirror.ws.api.security.bean;

import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.uimirror.ws.api.security.bean.base.Client;
import com.uimirror.ws.api.security.common.SecurityFieldConstants;
import com.uimirror.ws.api.security.ouath.License;

/**
 * <p>Bean to hold all the client info of uimirror
 * <p>Class has been marked as immutable </p>
 * <code>{@link Client}</code>
 * @author Jayaram
 */
public final class UimClient extends Client {

	private static final long serialVersionUID = -1347152923071428219L;
	
	public UimClient(String id, String apiKey, String secret, String redirectURI, License clientLicense, boolean active, boolean autoApproval, Map<String, Object> additionalInfo) {
		super(id, apiKey, secret, redirectURI, clientLicense, active, autoApproval, additionalInfo);
		this.initialize();
		if(!CollectionUtils.isEmpty(this.getAdditionalInfo())){
			super.put(SecurityFieldConstants._CLIENT_ADDITIONAL_INFO, this.getAdditionalInfo());
		}
	}

	public UimClient(String id, String apiKey, String secret, String redirectURI, License clientLicense, boolean active, boolean autoApproval) {
		super(id, apiKey, secret, redirectURI, clientLicense, active, autoApproval);
		this.initialize();
	}

	public UimClient(String id, License clientLicense) {
		super(id, clientLicense);
		this.initializeLess();
	}

	public UimClient(String id, String license) {
		super(id, license);
		this.initializeLess();
	}

	@Override
	public Client updateAdditionalInfo(Map<String, Object> info) {
		return new UimClient(this.getId(), this.getApiKey(), this.getSecret(), this.getRedirectURI(), this.getClientLicense(), this.isActive(), this.isAutoApproval(), info);
	}
	
	/**
	 * <p>This will add the additional info to the existing</p>
	 * @param key
	 * @param value
	 */
	public Client addAdditionalInfo(String key, Object value){
		super.addAdditionalInfo(key, value);
		return this;
	}

	/**
	 * <p>Initialize the common and frequent used variable</p>
	 */
	private void initializeLess(){
		super.put(SecurityFieldConstants._ID, this.getId());
		super.put(SecurityFieldConstants._CLIENT_LICENSE, this.getClientLicense());
	}
	
	/**
	 * <p>Initialize the commonly used attributes into the map</p>
	 */
	private void initialize() {
		super.put(SecurityFieldConstants._ID, this.getId());
		super.put(SecurityFieldConstants._API_KEY, this.getApiKey());
		super.put(SecurityFieldConstants._CLIENT_SECRET, this.getSecret());
		super.put(SecurityFieldConstants._CLIENT_REDIRECT_URL, this.getRedirectURI());
		super.put(SecurityFieldConstants._CLIENT_LICENSE, this.getClientLicense().getLicense());
		super.put(SecurityFieldConstants._CLIENT_IS_ACTIEVE, this.isActive() ? SecurityFieldConstants._ST_NUM_1 : SecurityFieldConstants._ST_NUM_0);
		super.put(SecurityFieldConstants._CLIENT_IS_AUTO_APPROVE, this.isAutoApproval() ? SecurityFieldConstants._ST_NUM_1 : SecurityFieldConstants._ST_NUM_0);
	}

}

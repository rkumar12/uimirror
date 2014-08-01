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
package com.uimirror.ws.api.security.bean.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.uimirror.mongo.feature.BeanBasedDocument;
import com.uimirror.util.web.WebUtil;
import com.uimirror.ws.api.security.common.SecurityFieldConstants;
import com.uimirror.ws.api.security.ouath.License;

/**
 * <p>Bean to hold all the client Created Information</p>
 * <p>Class has been marked as immutable, leaving the modifier as final to enable other users
 * to extend it, but please note, any body wants to extend it take proper care to make this as immutable</p>
 * 
 * @author Jayaram
 */
public class Client extends BeanBasedDocument implements Serializable{
	
	private static final long serialVersionUID = -4993504324270707065L;
	
	private final String id;
	private final String apiKey;
	private final String secret;
	private final String redirectURI;
	private final License clientLicense;
	private final boolean active;
	private final boolean autoApproval;
	private final Map<String, Object> additionalInfo;
	
	/**
	 * @param m
	 */
	@SuppressWarnings("unchecked")
	public Client(Map<String, Object> m){
		super(m);
		Assert.notEmpty(m, "Object Can't be Deserailized");
		//Fail fast if Id and license is not valid.
		String _id = (String)m.get(SecurityFieldConstants._ID);
		Assert.hasText(_id, "Client Id Can't be empty.");
		this.id = _id;
		String _license_id = (String)m.get(SecurityFieldConstants._CLIENT_LICENSE);
		this.clientLicense = License.getEnum(_license_id);
		String _apiKey = (String)m.get(SecurityFieldConstants._API_KEY);
		String _secret = (String)m.get(SecurityFieldConstants._CLIENT_SECRET);
		String _redirectUri = (String)m.get(SecurityFieldConstants._CLIENT_REDIRECT_URL);
		
		String _status = (String)m.get(SecurityFieldConstants._CLIENT_IS_ACTIEVE);
		String _autoApproval = (String)m.get(SecurityFieldConstants._CLIENT_IS_AUTO_APPROVE);
		Map<String, Object> _additionalInfo = (Map<String, Object>)m.get(SecurityFieldConstants._CLIENT_ADDITIONAL_INFO);
		//Below are the optional fields
		this.apiKey= StringUtils.hasText(_apiKey)? _apiKey : SecurityFieldConstants.EMPTY;
		this.secret = StringUtils.hasText(_secret)? _secret : SecurityFieldConstants.EMPTY;
		this.redirectURI = StringUtils.hasText(_redirectUri)? _redirectUri : SecurityFieldConstants.EMPTY;
		this.active = SecurityFieldConstants._ST_NUM_1.equals(_status) ? Boolean.TRUE : Boolean.FALSE;
		this.autoApproval = SecurityFieldConstants._ST_NUM_1.equals(_autoApproval) ? Boolean.TRUE : Boolean.FALSE;
		this.additionalInfo = CollectionUtils.isEmpty(_additionalInfo) ? new LinkedHashMap<String, Object>(5): _additionalInfo;
	}
	
	/**
	 * <p>For Security context client id and license is sufficient to gather the principal</p>
	 * @param id
	 */
	public Client(String id, License clientLicense) {
		super(5);
		this.id = id;
		this.apiKey=SecurityFieldConstants.EMPTY;
		this.secret = SecurityFieldConstants.EMPTY;
		this.redirectURI = SecurityFieldConstants.EMPTY;
		this.clientLicense = clientLicense;
		this.active = Boolean.TRUE;
		this.autoApproval = Boolean.FALSE;
		this.additionalInfo = null;
	}
	
	/**
	 * <p>For Security context client id and license is sufficient to gather the principal</p>
	 * @param id
	 */
	public Client(String id, String license) {
		super(5);
		this.id = id;
		this.apiKey=SecurityFieldConstants.EMPTY;
		this.secret = SecurityFieldConstants.EMPTY;
		this.redirectURI = SecurityFieldConstants.EMPTY;
		this.clientLicense = License.getEnum(license);
		this.active = Boolean.TRUE;
		this.autoApproval = Boolean.FALSE;
		this.additionalInfo = null;
	}

	/**
	 * @param id
	 * @param apiKey
	 * @param secret
	 * @param redirectURI
	 * @param clientLicense
	 * @param isActive
	 * @param autoApproval
	 */
	public Client(String id, String apiKey, String secret, String redirectURI, License clientLicense, boolean isActive, boolean autoApproval) {
		super(10);
		vaidate(id, apiKey, secret, redirectURI, clientLicense);
		this.id = id;
		this.apiKey = apiKey;
		this.secret = secret;
		this.redirectURI = redirectURI;
		this.clientLicense = clientLicense;
		this.active = isActive;
		this.autoApproval = autoApproval;
		this.additionalInfo = new LinkedHashMap<String, Object>(5,1);
	}

	/**
	 * @param id
	 * @param apiKey
	 * @param secret
	 * @param redirectURI
	 * @param clientLicense
	 * @param isActive
	 * @param autoApproval
	 * @param additionalInfo
	 */
	protected Client(String id, String apiKey, String secret, String redirectURI, License clientLicense, boolean isActive, boolean autoApproval, Map<String, Object> additionalInfo) {
		super(10);
		vaidate(id, apiKey, secret, redirectURI, clientLicense);
		Assert.notNull(additionalInfo, "Additional Info Can't be empty");
		this.id = id;
		this.apiKey = apiKey;
		this.secret = secret;
		this.redirectURI = redirectURI;
		this.clientLicense = clientLicense;
		this.active = isActive;
		this.autoApproval = autoApproval;
		this.additionalInfo = additionalInfo;
	}
	
	/**
	 * <p>This will create a new instance</p>
	 * @param info
	 * @return
	 */
	public Client updateAdditionalInfo(Map<String, Object> info) {
		return new Client(this.getId(), this.getApiKey(), this.getSecret(), this.getRedirectURI(), this.getClientLicense(), this.isActive(), this.isAutoApproval(), info);
	}
	
	/**
	 * <p>This will add the additional info to the existing</p>
	 * @param key
	 * @param value
	 */
	public Client addAdditionalInfo(String key, Object value){
		Assert.notEmpty(additionalInfo, "Additional Info can't be updated as it's not yet intialized.");
		this.additionalInfo.putIfAbsent(key, value);
		return this;
	}

	public String getId() {
		return id;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getRedirectURI() {
		return redirectURI;
	}

	public License getClientLicense() {
		return clientLicense;
	}

	public boolean isActive() {
		return active;
	}

	public Map<String, Object> getAdditionalInfo() {
		return additionalInfo;
	}

	public String getSecret() {
		return secret;
	}

	public boolean isAutoApproval() {
		return autoApproval;
	}
	
	/**
	 * @param id
	 * @param apiKey
	 * @param secret
	 * @param redirectURI
	 * @param clientLicense
	 */
	private static void vaidate(String id, String apiKey, String secret, String redirectURI, License clientLicense){
		Assert.hasText(id, "Client Id Can't be empty.");
		Assert.hasText(apiKey, "Client API Key Can't be empty.");
		Assert.hasText(secret, "Client Secret Key Can't be empty.");
		if(!WebUtil.isValidUrl(redirectURI)){
			throw new IllegalArgumentException("Redirect URI is not valid");
		}
		Assert.notNull(clientLicense, "License Should have a license");
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apiKey == null) ? 0 : apiKey.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (apiKey == null) {
			if (other.apiKey != null)
				return false;
		} else if (!apiKey.equals(other.apiKey))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", apiKey=" + apiKey + ", secret=" + secret
				+ ", redirectURI=" + redirectURI + ", clientLicense="
				+ clientLicense + ", active=" + active + ", autoApproval="
				+ autoApproval + ", additionalInfo=" + additionalInfo + "]";
	}

}

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

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.uimirror.ws.api.security.bean.base.Client;
import com.uimirror.ws.api.security.bean.base.SecurityFieldConstants;
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
	}

	public UimClient(String id, String apiKey, String secret, String redirectURI, License clientLicense, boolean active, boolean autoApproval) {
		super(id, apiKey, secret, redirectURI, clientLicense, active, autoApproval);
	}

	/**
	 *<p>This will populate the Map from the details available</p> 
	 */
	@Override
	public Map<String, Object> serailizeToDoucmentMap() {

		Map<String, Object> map = new HashMap<String, Object>(8,1);
		map.put(SecurityFieldConstants._ID, this.getId());
		map.put(SecurityFieldConstants._API_KEY, this.getApiKey());
		map.put(SecurityFieldConstants._CLIENT_SECRET, this.getSecret());
		map.put(SecurityFieldConstants._CLIENT_REDIRECT_URL, this.getRedirectURI());
		map.put(SecurityFieldConstants._CLIENT_LICENSE, this.getClientLicense().getLicense());
		map.put(SecurityFieldConstants._CLIENT_IS_ACTIEVE, this.isActive() ? SecurityFieldConstants._ST_NUM_1 : SecurityFieldConstants._ST_NUM_0);
		map.put(SecurityFieldConstants._CLIENT_IS_AUTO_APPROVE, this.isAutoApproval() ? SecurityFieldConstants._ST_NUM_1 : SecurityFieldConstants._ST_NUM_0);
		if(!CollectionUtils.isEmpty(this.getAdditionalInfo())){
			map.put(SecurityFieldConstants._CLIENT_ADDITIONAL_INFO, this.getAdditionalInfo());
		}
		return map;
	}

	/**
	 * <p>This will convert a map to the CLientdetails object</p>
	 * <p>While deserailizing it verifies if it has any id associated with it or not, if not
	 * <code>throws new {@link IllegalArgumentException}</code></p>
	 * @param data
	 * @return a new instance of <code>{@link Client}</code>
	 */
	public static Client deserailizeFromDocumentMap(Map<String, Object> data){
		
		Client cl = null;
		if(CollectionUtils.isEmpty(data)){
			throw new IllegalArgumentException("Data Can't be Deserailized as it has no data.");
		}
		String _id = (String) data.get(SecurityFieldConstants._ID);
		String _api_key = (String)data.get(SecurityFieldConstants._API_KEY);
		String _secret = (String) data.get(SecurityFieldConstants._CLIENT_SECRET);
		//TODO work on the url shortening part
		String _redirect_uri = (String) data.get(SecurityFieldConstants._CLIENT_REDIRECT_URL);
		License _license = License.getEnum((String) data.get(SecurityFieldConstants._CLIENT_LICENSE));
		boolean _active = ((int)data.get(SecurityFieldConstants._CLIENT_IS_ACTIEVE)) == SecurityFieldConstants.NUM_1 ? Boolean.TRUE : Boolean.FALSE;
		boolean _auto_approve = ((int)data.get(SecurityFieldConstants._CLIENT_IS_AUTO_APPROVE)) == SecurityFieldConstants.NUM_1 ? Boolean.TRUE : Boolean.FALSE;
		
		@SuppressWarnings("unchecked")
		Map<String, Object> additional_info = (Map<String, Object>) data.get(SecurityFieldConstants._CLIENT_ADDITIONAL_INFO);
		if(CollectionUtils.isEmpty(additional_info)){
			cl = new UimClient(_id, _api_key, _secret, _redirect_uri, _license, _active, _auto_approve);
		}else{
			cl = new UimClient(_id, _api_key, _secret, _redirect_uri, _license, _active, _auto_approve, additional_info);
		}
		return cl;
	}

	@Override
	public Client updateAdditionalInfo(Map<String, Object> info) {
		return new UimClient(this.getId(), this.getApiKey(), this.getSecret(), this.getRedirectURI(), this.getClientLicense(), this.isActive(), this.isAutoApproval(), info);
	}
}

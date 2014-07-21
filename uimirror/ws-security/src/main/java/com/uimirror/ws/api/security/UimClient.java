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
package com.uimirror.ws.api.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.uimirror.ws.api.security.base.Client;
import com.uimirror.ws.api.security.base.ClientDetails;
import com.uimirror.ws.api.security.base.Role;
import com.uimirror.ws.api.security.base.SecurityFieldConstants;

/**
 * <p>Bean to hold all the client info of uimirror
 * <p>Class has been marked as immutable </p>
 * <code>{@link Client}</code>
 * @author Jayaram
 */
public final class UimClient extends Client {

	private static final long serialVersionUID = -1347152923071428219L;

	/**
	 * @param uimClientDetails
	 * @param roles
	 */
	public UimClient(ClientDetails uimClientDetails, Set<Role> roles) {
		super(uimClientDetails, roles);
	}

	/**
	 * @param id
	 * @param apiKey
	 * @param uimClientDetails
	 * @param roles
	 * @param isActive
	 */
	private UimClient(long id, String apiKey, ClientDetails uimClientDetails,
			Set<Role> roles, boolean isActive) {
		super(id, apiKey, uimClientDetails, roles, isActive);
	}
	
	/**
	 *<p>This will populate the Map from the details available</p> 
	 */
	public Map<String, Object> serailizeToDoucmentMap() {
		Map<String, Object> map = new HashMap<String, Object>(4,1);
		
		map.put(SecurityFieldConstants._ID, String.valueOf(this.getId()));
		map.put(SecurityFieldConstants._API_KEY, this.getApiKey());
		map.put(SecurityFieldConstants._CLIENT_IS_ACTIEVE, this.isActive());
		
		if(!CollectionUtils.isEmpty(this.getRoles())){
			map.put(SecurityFieldConstants._CLIENT_ROLES, this.getRoles());
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
		if(CollectionUtils.isEmpty(data) || !StringUtils.isNumeric((String)data.get(SecurityFieldConstants._ID))){
			throw new IllegalArgumentException("Data Can't be Deserailized as it has no data.");
		}
		@SuppressWarnings("unchecked")
		List<String> roles = (List<String>)data.get(SecurityFieldConstants._CLIENT_ROLES);
		Set<Role> assignedRole = new HashSet<Role>();
		for (String role : roles){
			assignedRole.add(Role.getEnum(role));
		}
		return new UimClient(Long.valueOf((String)data.get(SecurityFieldConstants._ID)), 
				(String)data.get(SecurityFieldConstants._API_KEY), 
				null, 
				assignedRole, 
				(boolean)data.get(SecurityFieldConstants._CLIENT_IS_ACTIEVE));
	}
}

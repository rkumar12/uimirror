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
package com.uimirror.ws.api.security.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This will hold the generic map which needs to be validated or run against the drools rule.</p>
 * @author Jayaram
 */
public class DroolsDto implements Serializable{
	
	private static final long serialVersionUID = -4664219797849966887L;

	protected final Map<String, Object> facts;
	protected Map<String, Object> states;
	
	public DroolsDto(Map<String, Object> facts){
		this.facts = facts;
		this.states = new HashMap<String, Object>();
	}
	
	public void updateStates(String key, Object val){
		this.states.put(key, val);
	}

	public Map<String, Object> getStates() {
		return states;
	}

	public void setStates(Map<String, Object> states) {
		this.states = states;
	}

	public Map<String, Object> getFacts() {
		return facts;
	}
	
	public Object getFact(String key){
		return facts.get(key);
	}
}

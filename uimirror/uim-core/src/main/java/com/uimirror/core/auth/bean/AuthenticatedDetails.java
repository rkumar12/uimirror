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
package com.uimirror.core.auth.bean;

import java.io.Serializable;

import com.uimirror.core.ActorType;

/**
 * Will hold the details, after an actor is getting authenticated,
 * it will store the authenticated details of the actor such as 
 * 
 * <ul>
 * <li>id- user id/client id</li>
 * <li>type- Specifies the authenticated details are of type {@linkplain ActorType}</li>
 * <li>refreshinterval- Specifies the interval period this token/ or new token that will be generated will last for</li>
 * <li>instructions- If this principal has some instructions to take care</li>
 * </ul> 
 * @author Jay
 */
public interface AuthenticatedDetails extends Serializable{

	/**
	 * This will hold the user id/client id of the actor
	 * @return
	 */
	Object getId();
	
	/**
	 * will hold the {@linkplain ActorType} such as {@linkplain ActorType#USER}, {@linkplain ActorType#CLIENT}
	 * 
	 * @return
	 */
	ActorType getType();
	
	/**
	 * Holds the time interval a token can last for
	 * @return
	 */
	long getRefreshTokenInterval();
	
	/**
	 * Holds any instructions that applies to this principal
	 * @return
	 */
	Object getInstructions();

}

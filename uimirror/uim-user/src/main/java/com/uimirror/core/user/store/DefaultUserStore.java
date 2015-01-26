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
package com.uimirror.core.user.store;

import java.util.List;

import com.uimirror.core.exceptions.db.DBException;
import com.uimirror.core.exceptions.db.RecordNotFoundException;
import com.uimirror.core.user.DefaultUser;

/**
 * A flat representation of user data 
 * into temp user collection 
 * 
 * @author Jay
 */
public interface DefaultUserStore {

	String USER_BASIC_INFO_SEQ = "ubis";
	
	/**
	 * Retrieves user by the profile id
	 * 
	 * @param profileId on which basics document will be retrieved
	 * @return found object else {@link RecordNotFoundException}
	 * @throws DBException if any internal or syntax error found
	 */
	DefaultUser getByProfileId(String profileId) throws DBException;
	
	/**
	 * Retrieves the list of the profile id whose created time is before given time
	 * 
	 * @param minutes as parameter
	 * @return list of DefaultUser matching to the query
	 * @throws DBException if record not found 
	 */
	List<DefaultUser> getUnVerifiedAccountBefore(int minutes) throws DBException;
	
	/**
	 * Retrieves user by the profile id
	 * 
	 * @param profileId on which basics document will be retrieved and updated
	 * @return found object else {@link RecordNotFoundException}
	 * @throws DBException if any internal or syntax error found
	 */
	DefaultUser enableAccount(String profileId) throws DBException;
	
	/**
	 * Retrieves user by the email id
	 * 
	 * @param email search criteria
	 * @return retrieved user else {@link RecordNotFoundException}
	 * @throws DBException if any syntax error
	 */
	DefaultUser getByEmail(String email) throws DBException;

	/**
	 * Delete the use based on the profile ID
	 * @param profileId on which basics it will be deleted
	 * @throws DBException if any syntax error
	 */
	void deleteByprofileId(String profileId) throws DBException;
	
	/**store the user document into the collection
	 * 
	 * @param user which will be saved
	 * @return updated user, with profile id
	 */
	DefaultUser store(DefaultUser user);
}

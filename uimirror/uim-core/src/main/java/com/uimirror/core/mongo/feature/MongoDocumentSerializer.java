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
package com.uimirror.core.mongo.feature;

import java.util.Map;

/**
 * <p>A Seralizer and desarilzer contract for the Beans</p>
 * @author Jay
 */
public interface MongoDocumentSerializer<T> {
	
	/**
	 * Id of the doument
	 * @return id value for the current document
	 */
	public String getId();

	/**
	 * Serialize the state of object into a {@link Map}
	 * which will be stored to the Mongo document.
	 * 
	 * @return a {@link Map} of details that needs to be store
	 * @throws IllegalStateException in case the object state its trying to persist is not valid
	 */
	public Map<String, Object> writeToMap() throws IllegalStateException;

	/**
	 * Reads the content from map and restore the state of the object.
	 * 
	 * @param src a {@link Map} from where state will be restored
	 * @return a state full instance of type T
	 * @throws IllegalArgumentException in case provided source is invalid
	 */
	public abstract T readFromMap(Map<String, Object> src) throws IllegalArgumentException;
	
	/**
	 * Checks if the provided source is valid, if not throws {@link IllegalArgumentException}
	 * @param src which will be validated
	 * @return <code>true</code> if valid
	 * @throws IllegalArgumentException in case wrong input
	 */
	public boolean isValidSource(Map<String, Object> src) throws IllegalArgumentException;
	
	/**
	 * Will update the id field into the bean and copying the other existing details.
	 * @param id id to be updated in the document, when id is generated from the sequence
	 * @return restored object
	 */
	public T updateId(String id);

}

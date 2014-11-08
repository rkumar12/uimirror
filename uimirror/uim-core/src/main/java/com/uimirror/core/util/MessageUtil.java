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
package com.uimirror.core.util;

import java.util.List;
/**
 * An Utility class that helps to create Message String objects.
 * @author Jay
 */
public class MessageUtil {
	private static final String COMMA =" , ";
	private static final String AMPERSAND = " & ";
	private static final String SINGLE_FIELD_MESSAGE = " is not valid. ";
	private static final String MULTIPLE_FIELD_MESSAGE = " are not valid. ";
	//TODO: change message based on gender.
	private static final String AGE_LIMIT_MESSAGE = " Ohh Boy!!! We don't have any content for you at your age.";
	

	private MessageUtil() {
	}
	
	/**
	 * Creates the message based on the field
	 * @param fields list of fields which needs to segregated
	 * @return final string represenation of the message
	 */
	public static String getErrorMessage(List<String> fields){
		StringBuilder sb = new StringBuilder();
		final int size = fields.size(); 
		for (int i = 0; i < size; i++) {
			sb.append(fields.get(i));
			if (i != size - 1) {
				if (i != size - 2) {
					sb.append(COMMA);
				} else {
					sb.append(AMPERSAND);
				}
			}
		}
		if(size == 1){
			sb.append(SINGLE_FIELD_MESSAGE);
		}else{
			sb.append(MULTIPLE_FIELD_MESSAGE);
		}
		return sb.toString();
	}
	
	public static String getAgeLimitMessage(){
		return AGE_LIMIT_MESSAGE;
	}
}

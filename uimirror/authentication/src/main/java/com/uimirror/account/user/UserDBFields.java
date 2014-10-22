package com.uimirror.account.user;

import com.uimirror.core.BasicDBFields;

public interface UserDBFields extends BasicDBFields, UserAuthDBFields{
	//User Account logs such as created on etc
	String CREATED_ON = "created_on";
	String MODIFIED_ON = "modified_on";
	String DETAILS = "details";
}

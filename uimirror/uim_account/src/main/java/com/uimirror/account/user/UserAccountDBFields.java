package com.uimirror.account.user;

import com.uimirror.core.BasicDBFields;

public interface UserAccountDBFields extends BasicDBFields{
	//User Credentials
	String USER_ID = "uid";
	String PASSWORD = "pwd";
	String SCREEN_PASSWORD = "screen_pwd";
	String ACCOUNT_STATE = "state";
	String ACCOUNT_STATUS = "status";
	String ENCRYPTION_PWD = "salt";
	String CREATED_ON="created_on";
	String MODIFIED_ON="modified_on";
	String DETAILS = "details";

}

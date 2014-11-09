package com.uimirror.core.user;


public interface UserDBFields extends UserAuthDBFields, UserAccountLogDBFields, DOBDBFields, AddressDBFields{
	 
	 String FIRST_NAME="first_name";
	 String LAST_NAME="last_name";
	 String NAME="name";
	 String EMAIL="email";
	 String GENDER="gender";
	 String DATE_OF_BIRTH="dob";
	 String PRESENT_ADDRESS="present_add";
	 String PERMANET_ADDRESS="permanet_add";
	 String ACCOUNT_STATUS="account_status";
	 String ACCOUNT_STATE="account_state";
	 String INFO = "info";

}

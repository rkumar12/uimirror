package com.uimirror.rtp.shop.bean;

import com.uimirror.core.mongo.feature.BasicDBFields;
import com.uimirror.core.mongo.feature.LocationDBField;

public interface ShopInfoDBFields extends BasicDBFields , LocationDBField {

	String NAME = "name";
	String CATEGORY = "category";
	String STARTED_ON = "started_on";
	String NUMBER_OF_CAMPAIGN = "campaign_count";
	String REPORTING_TO_SHOP = "reporting_to";
	String SHOPS_REPORTING = "shops_reporting";
	String ACCOUNT_STATUS = "status";
	String CONTACT_NUMBER = "contact";

}

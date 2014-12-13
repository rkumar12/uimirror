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
package com.uimirror.reach.web.core;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Home View settings of the user.
 * @author Jay
 */
public class ReachHomeViewSettings extends AbstractBeanBasedDocument<ReachHomeViewSettings> implements BeanValidatorService{
	
	private static final long serialVersionUID = -3067800282684401930L;
	public static final String NAME = "viewSetting";
	
	private ReachTypeOfView landingView;
	private boolean textViewOnLanding;
	private String defaultShopIdForDashBoard;
	private boolean keepSubmenuClose;
	
	private ReachHomeViewSettings(HomeViewSettingsBuilder builder) {
		super(builder.profileId);
		this.landingView = builder.landingView;
		this.defaultShopIdForDashBoard = builder.defaultShopIdForDashBoard;
		this.textViewOnLanding = builder.isTextViewOnLanding;
		this.keepSubmenuClose = builder.keepSubmenuClose;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public ReachHomeViewSettings readFromMap(Map<String, Object> src) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Map<String, Object> writeToMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return null;
	}
	
	public String getProfileId() {
		return super.getId();
	}

	public ReachTypeOfView getLandingView() {
		return landingView;
	}

	public boolean isTextViewOnLanding() {
		return textViewOnLanding;
	}

	public String getDefaultShopIdForDashBoard() {
		return defaultShopIdForDashBoard;
	}

	public boolean isKeepSubmenuClose() {
		return keepSubmenuClose;
	}

	@Override
	public String toString() {
		StandardToStringStyle style = new StandardToStringStyle();
	    style.setFieldSeparator(", ");
	    style.setUseClassName(false);
	    style.setUseIdentityHashCode(false);
	    return new ReflectionToStringBuilder(this, style).toString();
	}


	public static class HomeViewSettingsBuilder implements Builder<ReachHomeViewSettings>{

		private ReachTypeOfView landingView;
		private boolean isTextViewOnLanding;
		private String defaultShopIdForDashBoard;
		private boolean keepSubmenuClose;
		private String profileId;

		public HomeViewSettingsBuilder(String profileId){
			this.profileId = profileId;
		}
		
		public HomeViewSettingsBuilder updateLandingView(ReachTypeOfView landingView){
			this.landingView = landingView;
			return this;
		}
		
		public HomeViewSettingsBuilder updateLandingView(int landingView){
			this.landingView = ReachTypeOfView.getEnum(landingView);
			return this;
		}
		
		public HomeViewSettingsBuilder updateTextViewOnLanding(boolean typeOfView){
			this.isTextViewOnLanding = typeOfView;
			return this;
		}
		
		public HomeViewSettingsBuilder updateDefaultShopId(String shopId){
			this.defaultShopIdForDashBoard = shopId;
			return this;
		}
		
		public HomeViewSettingsBuilder updateSubMenuCollapseStatus(boolean keepSubmenuOpen){
			this.keepSubmenuClose = keepSubmenuOpen;
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public ReachHomeViewSettings build() {
			return new ReachHomeViewSettings(this);
		}
		
	}
	
	//Below are the dummy view settings for the user
	private static final Map<String, ReachHomeViewSettings> SAMPLE_DATA = new WeakHashMap<String, ReachHomeViewSettings>();
	static{
		//New user with user view and no shop id, and submenu as closed
		ReachHomeViewSettings h1 = new HomeViewSettingsBuilder("1").
				updateLandingView(ReachTypeOfView.USER).
				updateTextViewOnLanding(Boolean.FALSE).
				updateSubMenuCollapseStatus(Boolean.TRUE).build();
		SAMPLE_DATA.put(h1.getProfileId(), h1);

		//New user with user view and no shop id, and submenu as open
		ReachHomeViewSettings h2 = new HomeViewSettingsBuilder("2").
				updateLandingView(ReachTypeOfView.USER).
				updateTextViewOnLanding(Boolean.FALSE).
				updateSubMenuCollapseStatus(Boolean.TRUE).build();
		SAMPLE_DATA.put(h2.getProfileId(), h2);
		
		//New user with user view with text data and no shop id, and submenu as Close
		ReachHomeViewSettings h3 = new HomeViewSettingsBuilder("3").
				updateLandingView(ReachTypeOfView.USER).
				updateTextViewOnLanding(Boolean.TRUE).
				updateSubMenuCollapseStatus(Boolean.FALSE).build();
		SAMPLE_DATA.put(h3.getProfileId(), h3);
		
		//New user with user view with text data and no shop id, and submenu as Close
		ReachHomeViewSettings h4 = new HomeViewSettingsBuilder("4").
				updateLandingView(ReachTypeOfView.SHOP).
				updateTextViewOnLanding(Boolean.FALSE).
				updateDefaultShopId("1").
				updateSubMenuCollapseStatus(Boolean.TRUE).build();
		SAMPLE_DATA.put(h4.getProfileId(), h4);
	}
	
	public static ReachHomeViewSettings getSettings(String profileId){
		//New user with user view and no shop id, and submenu as closed
		ReachHomeViewSettings h1 = new HomeViewSettingsBuilder(profileId).
				updateLandingView(ReachTypeOfView.USER).
				updateTextViewOnLanding(Boolean.FALSE).
				updateSubMenuCollapseStatus(Boolean.FALSE).build();
		ReachHomeViewSettings rs = SAMPLE_DATA.get(profileId);
		if(rs == null){
			SAMPLE_DATA.put(profileId, h1);
			return h1;
		}
			
		return rs;
	}
	
	public void updateSettings(String profileId, List<String> keys, List<Object> vals){
		//Orginal update should be done by the per key it should put to a map and update the value
		ReachHomeViewSettings rs = SAMPLE_DATA.get(profileId);
		//TODO Update part in last please
	}
}

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
package com.uimirror.reach.web.core.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.util.DateTimeUtil;

/**
 * Contains the wish that created by the user.
 * @author Jay
 */
public class Wish extends AbstractBeanBasedDocument<Wish> implements BeanValidatorService{

	private static final long serialVersionUID = 8622272139392008940L;

	private String wish;
	private String wishedItem;
	private long on;
	private boolean completedFlag;
	private long completedOn;
	private String profileId;
	
	private Wish(WishBuilder builder){
		super(builder.wishId);
		this.wish = builder.wish;
		this.on = builder.on;
		this.completedFlag = builder.completedFlag;
		this.completedOn = builder.completedOn;
		this.profileId = builder.profileId;
		this.wishedItem = builder.wishedItem;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public Wish readFromMap(Map<String, Object> src)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> writeToMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
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

	public static class WishBuilder implements Builder<Wish>{
		private String wish;
		private String wishedItem;
		private long on;
		private boolean completedFlag;
		private long completedOn;
		private String profileId;
		private String wishId;
		
		public WishBuilder(String wishId){
			this.wishId = wishId;
		}
		
		public WishBuilder makeWish(String wish){
			this.wish = wish;
			return this;
		}
		
		public WishBuilder wishForItem(String wishedItem){
			this.wishedItem = wishedItem;
			return this;
		}
		
		public WishBuilder wishOn(long on){
			this.on = on;
			return this;
		}
		
		public WishBuilder completedOn(long completedOn){
			this.completedOn = completedOn;
			return this;
		}
		
		public WishBuilder markWishCompleted(boolean completedFlag){
			this.completedFlag = completedFlag;
			return this;
		}

		public WishBuilder updateWhoseWish(String profileId){
			this.profileId = profileId;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public Wish build() {
			return new Wish(this);
		}
	}

	public String getWish() {
		return wish;
	}

	public String getWishedItem() {
		return wishedItem;
	}

	public long getOn() {
		return on;
	}

	public boolean isCompletedFlag() {
		return completedFlag;
	}

	public long getCompletedOn() {
		return completedOn;
	}

	public String getProfileId() {
		return profileId;
	}
	
	public String getWishId(){
		return getId();
	}

	//Sample Dummy Data
	private static final Map<String, List<Wish>> DATA;
	
	static{
		DATA = new WeakHashMap<String, List<Wish>>();
		Wish i11 = new WishBuilder("1").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("Phone").updateWhoseWish("1").build();
		Wish i12 = new WishBuilder("2").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("LG TV").updateWhoseWish("1").build();
		Wish i13 = new WishBuilder("3").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("Fridge").updateWhoseWish("1").
				completedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).markWishCompleted(Boolean.TRUE).build();
		Wish i14 = new WishBuilder("4").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Sell").wishForItem("Phone").updateWhoseWish("1").
				completedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).markWishCompleted(Boolean.TRUE).build();
		List<Wish> msgs = new ArrayList<Wish>();
		msgs.add(i11);
		msgs.add(i12);
		msgs.add(i13);
		msgs.add(i14);
		DATA.put(i11.getProfileId(), msgs);

		Wish i21 = new WishBuilder("1").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("Phone").updateWhoseWish("2").build();
		Wish i22 = new WishBuilder("2").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("LG TV").updateWhoseWish("2").build();
		Wish i23 = new WishBuilder("3").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("Fridge").updateWhoseWish("2").
				completedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).markWishCompleted(Boolean.TRUE).build();
		Wish i24 = new WishBuilder("4").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Sell").wishForItem("Phone").updateWhoseWish("2").
				completedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).markWishCompleted(Boolean.TRUE).build();
		List<Wish> msgs1 = new ArrayList<Wish>();
		msgs1.add(i21);
		msgs1.add(i22);
		msgs1.add(i23);
		msgs1.add(i24);
		DATA.put(i21.getProfileId(), msgs1);
		
		Wish i31 = new WishBuilder("1").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("Phone").updateWhoseWish("3").build();
		Wish i32 = new WishBuilder("2").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("LG TV").updateWhoseWish("3").build();
		Wish i33 = new WishBuilder("3").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("Fridge").updateWhoseWish("3").
				completedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).markWishCompleted(Boolean.TRUE).build();
		Wish i34 = new WishBuilder("4").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Sell").wishForItem("Phone").updateWhoseWish("3").
				completedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).markWishCompleted(Boolean.TRUE).build();
		List<Wish> msgs2 = new ArrayList<Wish>();
		msgs1.add(i31);
		msgs1.add(i32);
		msgs1.add(i33);
		msgs1.add(i34);
		DATA.put(i31.getProfileId(), msgs2);
		
		Wish i41 = new WishBuilder("1").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("Phone").updateWhoseWish("4").build();
		Wish i42 = new WishBuilder("2").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("LG TV").updateWhoseWish("4").build();
		Wish i43 = new WishBuilder("3").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("Fridge").updateWhoseWish("4").
				completedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).markWishCompleted(Boolean.TRUE).build();
		Wish i44 = new WishBuilder("4").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Sell").wishForItem("Phone").updateWhoseWish("4").
				completedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).markWishCompleted(Boolean.TRUE).build();
		List<Wish> msgs3 = new ArrayList<Wish>();
		msgs3.add(i41);
		msgs3.add(i42);
		msgs3.add(i43);
		msgs3.add(i44);
		DATA.put(i41.getProfileId(), msgs3);
		
		Wish i51 = new WishBuilder("1").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("Phone").updateWhoseWish("5").build();
		Wish i52 = new WishBuilder("2").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("LG TV").updateWhoseWish("5").build();
		Wish i53 = new WishBuilder("3").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Buy").wishForItem("Fridge").updateWhoseWish("5").
				completedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).markWishCompleted(Boolean.TRUE).build();
		Wish i54 = new WishBuilder("4").wishOn(DateTimeUtil.getCurrentSystemUTCEpoch()).makeWish("I want to Sell").wishForItem("Phone").updateWhoseWish("5").
				completedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).markWishCompleted(Boolean.TRUE).build();
		List<Wish> msgs4 = new ArrayList<Wish>();
		msgs3.add(i51);
		msgs3.add(i52);
		msgs3.add(i53);
		msgs3.add(i54);
		DATA.put(i51.getProfileId(), msgs4);
	}
	
	public static List<Wish> getWishes(String profileId){
		return DATA.get(profileId);
	}
	
	public Wish getMessage(String wishId){
		Wish inb = null;
		for(String key : DATA.keySet()){
			if(inb!= null){
				break;
			}
			List<Wish> msgs = DATA.get(key);
			for(Wish b : msgs){
				if(b.getWishId().equalsIgnoreCase(wishId)){
					inb = b;
					break;
				}
			}
		}
		return inb;
	}
}

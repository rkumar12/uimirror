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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Contains the product and its category details.
 * such as Mongo DB-> DataBase->Books
 * Id is known as the product name
 * parent-> specifies the ID of the parent category
 * left-> number from the left
 * right-> number from the right
 * icon-> icon name
 * @author Jay
 */
public class RetailProduct extends AbstractBeanBasedDocument<RetailProduct> implements BeanValidatorService{

	private static final long serialVersionUID = -3294256332186876588L;

	private String parent;
	private int left;
	private int right;
	private String icon;
	
	
	private RetailProduct(RetailProductBuilder builder){
		super(builder.name);
		this.parent = builder.parent;
		this.left = builder.left;
		this.right = builder.right;
		this.icon = builder.icon;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public RetailProduct readFromMap(Map<String, Object> src)
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
	
	public static class RetailProductBuilder implements Builder<RetailProduct>{

		private String name;
		private String parent;
		private int left;
		private int right;
		private String icon;
		
		public RetailProductBuilder(String name) {
			this.name = name;
		}
		
		public RetailProductBuilder addParent(String parentName){
			this.parent = parentName;
			return this;
		}
		
		public RetailProductBuilder addLeftPostion(int left){
			this.left = left;
			return this;
		}
		
		public RetailProductBuilder addRightPostion(int right){
			this.right = right;
			return this;
		}
		
		public RetailProductBuilder addIcon(String icon){
			this.icon = icon;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public RetailProduct build() {
			return new RetailProduct(this);
		}
		
	}

	public String getParent() {
		return parent;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public String getIcon() {
		return icon;
	}
	
	public String getName(){
		return getId();
	}
	
	/**
	 * If a category doesn't have any parent meaning it should be the Parent category
	 * @return <code>true</code> if it has no parent else <code>false</code>
	 */
	public boolean isMainCategory(){
		return getParent() == null;
	}
	
	
	/**
	 * If the left and right position difference is 1, then it should be the final product
	 * @return <code>true</code> if difference is 1 else <code>false</code>.
	 */
	public boolean isFinalProduct(){
		return right -left == 1;
	}
	
	//Sample Dummy Data
	private static final List<RetailProduct> MAIN_CATEGORIES;
	private static final RetailProduct p1;
	private static final RetailProduct p11;
	private static final RetailProduct p12;
	private static final RetailProduct p13;
	private static final RetailProduct p14;
	private static final RetailProduct p15;
	private static final RetailProduct p2;
	private static final RetailProduct p21;
	private static final RetailProduct p22;
	private static final RetailProduct p23;
	private static final RetailProduct p24;
	private static final RetailProduct p25;
	static{
		MAIN_CATEGORIES = new ArrayList<RetailProduct>();
		p1 = new RetailProductBuilder("Book").addLeftPostion(1).addRightPostion(12).addIcon("book.png").build();
		p11 = new RetailProductBuilder("Programing").addLeftPostion(2).addRightPostion(11).addParent("Book").addIcon("programing.png").build();
		p12 = new RetailProductBuilder("Language").addLeftPostion(3).addRightPostion(4).addParent("Programing").addIcon("language.png").build();
		p13 = new RetailProductBuilder("DataBase").addLeftPostion(5).addRightPostion(10).addParent("Language").addIcon("database.png").build();
		p14 = new RetailProductBuilder("Mongo").addLeftPostion(6).addRightPostion(7).addParent("DataBase").addIcon("mongo.png").build();
		p15 = new RetailProductBuilder("Oracle").addLeftPostion(8).addRightPostion(9).addParent("DataBase").addIcon("oracle.png").build();

		p2 = new RetailProductBuilder("Electronic").addLeftPostion(1).addRightPostion(12).addIcon("electronic.png").build();
		p21 = new RetailProductBuilder("Home Appliance").addLeftPostion(2).addRightPostion(11).addParent("Electronic").addIcon("home_ap.png").build();
		p22 = new RetailProductBuilder("TV").addLeftPostion(3).addRightPostion(4).addParent("Home Appliance").addIcon("tv.png").build();
		p23 = new RetailProductBuilder("Refigartor").addLeftPostion(5).addRightPostion(6).addParent("Home Appliance").addIcon("refigartor.png").build();
		p24 = new RetailProductBuilder("Washing Machiene").addLeftPostion(7).addRightPostion(8).addParent("Home Appliance").addIcon("wm.png").build();
		p25 = new RetailProductBuilder("Ovane").addLeftPostion(9).addRightPostion(10).addParent("Home Appliance").addIcon("ovane.png").build();
		
		
		MAIN_CATEGORIES.add(p1);
		MAIN_CATEGORIES.add(p2);
	}
	
	public static List<RetailProduct> getMainCategories(){
		return MAIN_CATEGORIES;
	}
	
	public static RetailProduct getProgramingProduct(){
		return p11;
	}
	public static RetailProduct getMongoBookProduct(){
		return p14;
	}
	public static RetailProduct getTvProduct(){
		return p22;
	}
	
	public static RetailProduct getHomeAppliance(){
		return p21;
	}

}

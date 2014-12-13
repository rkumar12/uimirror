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
 * Contains the notifications that arrives for a user.
 * @author Jay
 */
public class Notification extends AbstractBeanBasedDocument<Notification> implements BeanValidatorService{

	private static final long serialVersionUID = 8622272139392008940L;

	private String message;
	private long arrivedOn;
	private boolean readFlag;
	private long readOn;
	private String to;
	
	private Notification(NotificationBuilder builder){
		super(builder.messageId);
		this.message = builder.message;
		this.arrivedOn = builder.arrivedOn;
		this.readFlag = builder.readFlag;
		this.readOn = builder.readOn;
		this.to = builder.to;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public Notification readFromMap(Map<String, Object> src)
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

	public static class NotificationBuilder implements Builder<Notification>{
		private String messageId;
		private String message;
		private long arrivedOn;
		private boolean readFlag;
		private long readOn;
		private String to;
		
		public NotificationBuilder(String messageId){
			this.messageId = messageId;
		}
		
		public NotificationBuilder createMessage(String message){
			this.message = message;
			return this;
		}
		
		public NotificationBuilder createdOn(long createdOn){
			this.arrivedOn = createdOn;
			return this;
		}
		
		public NotificationBuilder updatedReadStatus(boolean readFlag){
			this.readFlag = readFlag;
			return this;
		}
		
		public NotificationBuilder readOn(long readOn){
			this.readOn = readOn;
			return this;
		}
		
		public NotificationBuilder writeTo(String to){
			this.to = to;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public Notification build() {
			return new Notification(this);
		}
	}

	public String getMessage() {
		return message;
	}

	public long getArrivedOn() {
		return arrivedOn;
	}

	public boolean isReadFlag() {
		return readFlag;
	}

	public long getReadOn() {
		return readOn;
	}
	
	public String getMessageId(){
		return getId();
	}

	public String getTo() {
		return to;
	}
	
	
	//Sample Dummy Data
	private static final Map<String, List<Notification>> DATA;
	
	static{
		DATA = new WeakHashMap<String, List<Notification>>();
		Notification i11 = new NotificationBuilder("1").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("1").build();
		Notification i12 = new NotificationBuilder("2").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("1").build();
		Notification i13 = new NotificationBuilder("3").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("1").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		Notification i14 = new NotificationBuilder("4").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("1").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		List<Notification> msgs = new ArrayList<Notification>();
		msgs.add(i11);
		msgs.add(i12);
		msgs.add(i13);
		msgs.add(i14);
		DATA.put(i11.getTo(), msgs);

		Notification i21 = new NotificationBuilder("1").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("2").build();
		Notification i22 = new NotificationBuilder("2").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("2").build();
		Notification i23 = new NotificationBuilder("3").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("2").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		Notification i24 = new NotificationBuilder("4").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("2").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		List<Notification> msgs1 = new ArrayList<Notification>();
		msgs1.add(i21);
		msgs1.add(i22);
		msgs1.add(i23);
		msgs1.add(i24);
		DATA.put(i21.getTo(), msgs1);
		
		Notification i31 = new NotificationBuilder("1").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("3").build();
		Notification i32 = new NotificationBuilder("2").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("3").build();
		Notification i33 = new NotificationBuilder("3").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("3").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		Notification i34 = new NotificationBuilder("4").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("3").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		List<Notification> msgs2 = new ArrayList<Notification>();
		msgs1.add(i31);
		msgs1.add(i32);
		msgs1.add(i33);
		msgs1.add(i34);
		DATA.put(i31.getTo(), msgs2);
		
		Notification i41 = new NotificationBuilder("1").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("4").build();
		Notification i42 = new NotificationBuilder("2").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("4").build();
		Notification i43 = new NotificationBuilder("3").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("4").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		Notification i44 = new NotificationBuilder("4").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("4").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		List<Notification> msgs3 = new ArrayList<Notification>();
		msgs3.add(i41);
		msgs3.add(i42);
		msgs3.add(i43);
		msgs3.add(i44);
		DATA.put(i41.getTo(), msgs3);
		
		Notification i51 = new NotificationBuilder("1").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("5").build();
		Notification i52 = new NotificationBuilder("2").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("5").build();
		Notification i53 = new NotificationBuilder("3").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("5").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		Notification i54 = new NotificationBuilder("4").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("5").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		List<Notification> msgs4 = new ArrayList<Notification>();
		msgs3.add(i51);
		msgs3.add(i52);
		msgs3.add(i53);
		msgs3.add(i54);
		DATA.put(i51.getTo(), msgs4);
	}
	
	public static List<Notification> getMessages(String profileId){
		return DATA.get(profileId);
	}
	
	public static int getUnReadCount(String profileId){
		List<Notification> messages = DATA.get(profileId);
		int count = 0;
		for(Notification b : messages){
			if(!b.isReadFlag()){
				count++;
			}
		}
		return count;
	}
	
	public Notification getMessage(String messageId){
		Notification inb = null;
		for(String key : DATA.keySet()){
			if(inb!= null){
				break;
			}
			List<Notification> msgs = DATA.get(key);
			for(Notification b : msgs){
				if(b.getMessageId().equalsIgnoreCase(messageId)){
					inb = b;
					break;
				}
			}
		}
		return inb;
	}
}

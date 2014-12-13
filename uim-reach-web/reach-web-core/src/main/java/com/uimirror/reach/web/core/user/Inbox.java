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
 * Contains the message that arrives for a user.
 * @author Jay
 */
public class Inbox extends AbstractBeanBasedDocument<Inbox> implements BeanValidatorService{

	private static final long serialVersionUID = 8622272139392008940L;

	private String message;
	private long arrivedOn;
	private boolean readFlag;
	private long readOn;
	private String to;
	
	private Inbox(InboxBuilder builder){
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
	public Inbox readFromMap(Map<String, Object> src)
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

	public static class InboxBuilder implements Builder<Inbox>{
		private String messageId;
		private String message;
		private long arrivedOn;
		private boolean readFlag;
		private long readOn;
		private String to;
		
		public InboxBuilder(String messageId){
			this.messageId = messageId;
		}
		
		public InboxBuilder createMessage(String message){
			this.message = message;
			return this;
		}
		
		public InboxBuilder createdOn(long createdOn){
			this.arrivedOn = createdOn;
			return this;
		}
		
		public InboxBuilder updatedReadStatus(boolean readFlag){
			this.readFlag = readFlag;
			return this;
		}
		
		public InboxBuilder readOn(long readOn){
			this.readOn = readOn;
			return this;
		}
		
		public InboxBuilder writeTo(String to){
			this.to = to;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public Inbox build() {
			return new Inbox(this);
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
	private static final Map<String, List<Inbox>> DATA;
	
	static{
		DATA = new WeakHashMap<String, List<Inbox>>();
		Inbox i11 = new InboxBuilder("1").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("1").build();
		Inbox i12 = new InboxBuilder("2").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("1").build();
		Inbox i13 = new InboxBuilder("3").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("1").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		Inbox i14 = new InboxBuilder("4").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("1").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		List<Inbox> msgs = new ArrayList<Inbox>();
		msgs.add(i11);
		msgs.add(i12);
		msgs.add(i13);
		msgs.add(i14);
		DATA.put(i11.getTo(), msgs);

		Inbox i21 = new InboxBuilder("1").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("2").build();
		Inbox i22 = new InboxBuilder("2").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("2").build();
		Inbox i23 = new InboxBuilder("3").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("2").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		Inbox i24 = new InboxBuilder("4").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("2").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		List<Inbox> msgs1 = new ArrayList<Inbox>();
		msgs1.add(i21);
		msgs1.add(i22);
		msgs1.add(i23);
		msgs1.add(i24);
		DATA.put(i21.getTo(), msgs1);
		
		Inbox i31 = new InboxBuilder("1").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("3").build();
		Inbox i32 = new InboxBuilder("2").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("3").build();
		Inbox i33 = new InboxBuilder("3").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("3").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		Inbox i34 = new InboxBuilder("4").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("3").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		List<Inbox> msgs2 = new ArrayList<Inbox>();
		msgs1.add(i31);
		msgs1.add(i32);
		msgs1.add(i33);
		msgs1.add(i34);
		DATA.put(i31.getTo(), msgs2);
		
		Inbox i41 = new InboxBuilder("1").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("4").build();
		Inbox i42 = new InboxBuilder("2").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("4").build();
		Inbox i43 = new InboxBuilder("3").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("4").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		Inbox i44 = new InboxBuilder("4").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("4").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		List<Inbox> msgs3 = new ArrayList<Inbox>();
		msgs3.add(i41);
		msgs3.add(i42);
		msgs3.add(i43);
		msgs3.add(i44);
		DATA.put(i41.getTo(), msgs3);
		
		Inbox i51 = new InboxBuilder("1").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("5").build();
		Inbox i52 = new InboxBuilder("2").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("5").build();
		Inbox i53 = new InboxBuilder("3").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test").writeTo("5").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		Inbox i54 = new InboxBuilder("4").createdOn(DateTimeUtil.getCurrentSystemUTCEpoch()).createMessage("This is a test <a hreaf=\"#\">test</a>").writeTo("5").
				readOn(DateTimeUtil.getCurrentSystemUTCEpoch()).updatedReadStatus(Boolean.TRUE).build();
		List<Inbox> msgs4 = new ArrayList<Inbox>();
		msgs3.add(i51);
		msgs3.add(i52);
		msgs3.add(i53);
		msgs3.add(i54);
		DATA.put(i51.getTo(), msgs4);
	}
	
	public static List<Inbox> getMessages(String profileId){
		return DATA.get(profileId);
	}
	
	public static int getUnReadCount(String profileId){
		List<Inbox> messages = DATA.get(profileId);
		int count = 0;
		for(Inbox b : messages){
			if(!b.isReadFlag()){
				count++;
			}
		}
		return count;
	}
	
	public Inbox getMessage(String messageId){
		Inbox inb = null;
		for(String key : DATA.keySet()){
			if(inb!= null){
				break;
			}
			List<Inbox> msgs = DATA.get(key);
			for(Inbox b : msgs){
				if(b.getMessageId().equalsIgnoreCase(messageId)){
					inb = b;
					break;
				}
			}
		}
		return inb;
	}
}

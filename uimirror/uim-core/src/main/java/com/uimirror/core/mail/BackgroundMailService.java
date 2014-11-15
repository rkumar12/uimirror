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
package com.uimirror.core.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;
import org.thymeleaf.context.Context;

import com.uimirror.core.util.thread.AbstractBackgroundProcessor;

/**
 * A Mail service implementation, that will only focus on sending the email
 * user for this should call {@link #getContext(java.util.Locale)} to get the {@link Context}
 * after context, make call to the {@link #invoke(Map)} map containing all the details such as from
 * to, sub etc
 * 
 * @author Jay
 */
public class BackgroundMailService extends AbstractBackgroundProcessor<Map<String, Object>, Object>{

	private MailService mailService;
	public static final String NAME = "BMS";

	public BackgroundMailService() {
		super(1);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.util.thread.BackgroundProcessor#invoke(java.lang.Object)
	 */
	@Override
	public void invoke(Map<String, Object> param) {
		getAdaptor().submitTasks(createJobs(param));
	}
	
	/**
	 * @param locale loacle to be used for the context
	 * @return reterived context
	 */
	public Context getContext(Locale locale){
		return mailService.getContext(locale);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.util.thread.BackgroundProcessor#getResult()
	 */
	@Override
	public Object getResult() throws IllegalThreadStateException {
		return getResults();
	}
	
	/**
	 * Creates the Job List
	 * @param paramusing these params new thread will be spermed to send email
	 * @return new thread instance
	 */
	private List<Runnable> createJobs(Map<String, Object> param){
		List<Runnable> backgrounds = new ArrayList<Runnable>();
		backgrounds.add(new RunInBackGround(param));
		return backgrounds;
	}
	
	/**
	 * From a task that can be submitted for background process
	 * @author Jay
	 */
	private class RunInBackGround implements Runnable{
		
		private final Map<String, Object> param;
		public RunInBackGround(Map<String, Object> param) {
			super();
			this.param = param;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			LOG.info("[BACKGROUND-MAIL-START]- processing email in background");
			//Step 1- Extract from the Map
			try {
				String sub = (String)param.get(MailConstants.SUBJECT);
				String to = (String)param.get(MailConstants.TO);
				String from = (String)param.get(MailConstants.FORM);
				Context ctx = (Context)param.get(MailConstants.CONTEXT);
				String templateName = (String)param.get(MailConstants.TEMPLATE_NAME);
				mailService.sendMail(sub, to, from, ctx, templateName);
			} catch (MailException | MessagingException e) {
				LOG.error("[BACKGROUND-MAIL-ERROR]- Something went wrong while processing email {}", e);
			}
			LOG.info("[BACKGROUND-MAIL-END]- processing email in background");
		}
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

}

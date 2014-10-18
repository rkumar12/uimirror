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

import javax.mail.internet.MimeMessage;

import com.uimirror.core.util.thread.AbstractBackgroundProcessor;

/**
 * @author Jay
 */
public class BackgroundMailer extends AbstractBackgroundProcessor<MimeMessage, Object>{

	private Mailer mailer;
	
	/**
	 * @param size
	 */
	public BackgroundMailer() {
		super(1);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.util.thread.BackgroundProcessor#invoke(java.lang.Object)
	 */
	@Override
	public void invoke(MimeMessage param) {
		getAdaptor().submitTasks(createJobs(param));
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
	 * @param token
	 * @return
	 */
	private List<Runnable> createJobs(MimeMessage message){
		List<Runnable> backgrounds = new ArrayList<Runnable>();
		backgrounds.add(new RunInBackGround(message));
		return backgrounds;
	}
	
	/**
	 * From a task that can be submitted for background process
	 * @author Jay
	 */
	private class RunInBackGround implements Runnable{
		
		private final MimeMessage message;
		public RunInBackGround(MimeMessage message){
			this.message = message;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			LOG.info("[BACKGROUND-MAIL-START]- processing email in background");
			//Step 1- Transform the token to the UserAuthorizedClient object
			mailer.sendMail(message);
			LOG.info("[BACKGROUND-MAIL-END]- processing email in background");
		}
		
	}

	

}

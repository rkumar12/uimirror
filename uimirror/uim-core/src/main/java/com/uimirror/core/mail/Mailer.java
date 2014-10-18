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

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import com.uimirror.core.rest.extra.InternalException;

/**
 * @author Jay
 */
public class Mailer {

	protected static Logger LOG = LoggerFactory.getLogger(Mailer.class);

    private JavaMailSender mailSender;

	public void sendMail(final MimeMessage message) throws InternalException{
	    LOG.info("[START]- Sending Email.");
	    try {
	    	mailSender.send(message);
	    	LOG.info("[INTERIM]- A mail has been sent.");
	    } catch (MailException e) {
	    	LOG.warn("Error occured while sending the mail, the excption for the same is: ()", e);
	    	throw new InternalException("Can't Send mail Now");
	    }finally{
	    	LOG.info("[END]- Sending Email.");
	    }
	}

	public void setMailSender(JavaMailSender mailSender) {
	    this.mailSender = mailSender;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

}

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

import javax.mail.MessagingException;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 * A Mail service implementation, that will only focus on sending the email
 * user for this should call {@link #getContext(java.util.Locale)} to get the {@link Context}
 * after context, make call to the {@link #sendMail(String, String, String, Context, String)}
 * @author Jay
 */
public class MailService  extends AbstractMailService{

	/**
	 * @param mailSender
	 * @param templateEngine
	 */
	public MailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
		super(mailSender, templateEngine);
	}

	public void sendMail(final String sub, final String to, final String from, final Context ctx, final String templateName) throws MailException, MessagingException{
		LOG.info("[SINGLE]- Sending email to {}", to);
		getMailSender().send(createInlineMessage(sub, to, from, ctx, templateName));
	}


}

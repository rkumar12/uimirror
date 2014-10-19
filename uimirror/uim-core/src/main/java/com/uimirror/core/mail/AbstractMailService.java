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

import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.uimirror.core.Constants;


/**
 * Has common implementation for the mail service
 * first call should be {@link #getContext(Locale)} to get the {@link Context}
 * @author Jay
 */
public class AbstractMailService {

	private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractMailService.class);
    
    public AbstractMailService(JavaMailSender mailSender, TemplateEngine templateEngine){
    	this.mailSender = mailSender;
    	this.templateEngine = templateEngine;
    }
	/**
	 * gets the context using the locale
	 * @param locale
	 * @return
	 */
	public Context getContext(Locale locale){
		locale = locale == null ? Locale.ENGLISH : locale;
		final Context ctx = new Context(locale);
		return ctx;
	}
	
	/**
	 * Builds the message which needs to be sent
	 * @param sub
	 * @param to
	 * @param ctx
	 * @param templateName
	 * @return
	 * @throws MessagingException
	 */
	public MimeMessage createInlineMessage(final String sub, final String to, final String from, final Context ctx, final String templateName) throws MessagingException{
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, Boolean.TRUE /* multipart */, Constants.UTF_8);
        message.setSubject(sub);
        if(StringUtils.hasText(from)){
        	message.setFrom(from);
        }else{
        	message.setFrom("UIMirror Team");
        }
        message.setTo(to);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process(templateName, ctx);
        message.setText(htmlContent, Boolean.TRUE /* isHtml */);
        
        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
        //TODO make the default logo specific here, instead of passing null, it should be valid
        final InputStreamSource imageSource = new ByteArrayResource(null);
        //TODO complete this inline content type as well
        //message.addInline(imageResourceName, imageSource, imageContentType);
        return mimeMessage;
	}
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

}

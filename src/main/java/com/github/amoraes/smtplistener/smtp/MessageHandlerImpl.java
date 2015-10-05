package com.github.amoraes.smtplistener.smtp;

import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.RejectException;

import com.github.amoraes.smtplistener.domain.EmailMessageWithContent;

/**
 * Subethamail MessageHandler implementation 
 * @author Alessandro Moraes
 */
public class MessageHandlerImpl implements MessageHandler{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private MessageContext ctx;
    private EmailMessageWithContent email = new EmailMessageWithContent();
	private SMTPListener smtpListener;
    

    public MessageHandlerImpl(MessageContext ctx, SMTPListener smtpListener) {
            this.ctx = ctx;
            this.smtpListener = smtpListener;
    }
    
    public void from(String from) throws RejectException {
            log.info("Mail Received!");
            log.info("From: "+from);
            email.setFrom(from);
    }

    public void recipient(String recipient) throws RejectException {
    		log.info("To:"+recipient);
            email.setTo(recipient);
    }

    public void data(InputStream data) throws IOException {
    	MessageParser parser = new MessageParser();
    	parser.parseMessage(data);
    	email.setSubject(parser.getSubject());
    	email.setReplyTo(parser.getReplyTo());
    	email.setTextBody(parser.getTextBody());
    	email.setHtmlBody(parser.getHtmlBody());
    	email.setAttachments(parser.getAttachments());
    	log.info("Subject:"+parser.getSubject());
            
            
    }

    public void done() {
        email.setDate(new Date());    
		smtpListener.addEmail(email);
    }

}








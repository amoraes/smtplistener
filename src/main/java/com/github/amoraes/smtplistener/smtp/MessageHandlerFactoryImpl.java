package com.github.amoraes.smtplistener.smtp;

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;

/**
 * Subethamail MessageHandlerFactory implementation 
 * @author Alessandro Moraes
 */
public class MessageHandlerFactoryImpl implements MessageHandlerFactory {

	private SMTPListener smtpListener;

	public MessageHandlerFactoryImpl(SMTPListener smtpListener){
		this.smtpListener = smtpListener;
	}
	
    public MessageHandler create(MessageContext ctx) {
        return new MessageHandlerImpl(ctx,smtpListener);
    }

    
}

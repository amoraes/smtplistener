package com.github.amoraes.smtplistener.smtp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.subethamail.smtp.server.SMTPServer;

import com.github.amoraes.smtplistener.domain.Attachment;
import com.github.amoraes.smtplistener.domain.EmailMessageWithContent;

/**
 * A singleton used as a SMTP Server with an in-memory database for received e-mails 
 * @author Alessandro Moraes
 */
@Component
@Scope("singleton")
public class SMTPListener {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${server.port}")
	private int serverPort;
	@Value("${server.address}")
	private String serverAddress;
	@Value("${server.contextPath}")
	private String serverContext;
	@Value("${smtp.port}")
	private int smtpPort;
	
	/**
	 * Sequence number to generate E-mails' IDs
	 */
	private int mailIdSequence = 1;
	/**
	 * Sequence number to generate Attachments' IDs
	 */
	private int attachmentIdSequence = 1;
	/**
	 * Map to store received e-mails
	 */
	private Map<Integer,EmailMessageWithContent> emails = new HashMap<Integer,EmailMessageWithContent>();
	/**
	 * Map to store received attachments
	 */
	private Map<Integer,Attachment> attachments = new HashMap<Integer,Attachment>();
	/**
	 * Indicates if the smtp server is already started
	 */
	private boolean started = false;	
	
	public SMTPListener(){
	}
	
	/**
	 * Start listening for e-mails on port 25
	 * @throws Exception 
	 */
	public void start() throws Exception{
		if(started == false){
			MessageHandlerFactoryImpl myFactory = new MessageHandlerFactoryImpl(this) ;
			log.info("Trying to start a SMTP Server on port "+smtpPort+"...");
			SMTPServer smtpServer = new SMTPServer(myFactory);
			smtpServer.setPort(smtpPort);
			smtpServer.start();
			log.info("SMTP Server started.");
			log.info("Access http://"+serverAddress+":"+serverPort+serverContext+"/list to view received messages)");
			started = true;
		}else{
			throw new Exception("SMTP Server already started.");
		}
	}
	
	/**
	 * Store an e-mail
	 * @param email
	 */
	public void addEmail(EmailMessageWithContent email) {
		email.setId(mailIdSequence);
		for(Attachment attachment : email.getAttachments()){
			attachment.setId(attachmentIdSequence);
			attachments.put(attachmentIdSequence, attachment);
			attachmentIdSequence++;
		}
		emails.put(mailIdSequence, email);
		mailIdSequence++;
	}
	
	/**
	 * Return the e-mails received
	 * @return
	 */
	public Map<Integer,EmailMessageWithContent> getEmails() {
		return emails;
	}
	
	/**
	 * Return the attachments received
	 * @return
	 */
	public Map<Integer,Attachment> getAttachments() {
		return attachments;
	}
	
	
}

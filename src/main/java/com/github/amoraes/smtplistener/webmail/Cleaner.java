package com.github.amoraes.smtplistener.webmail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.amoraes.smtplistener.smtp.SMTPListener;

@Component
public class Cleaner {
	private static final Logger log = LoggerFactory.getLogger(Cleaner.class);
	
	@Autowired
	private SMTPListener smtpListener; 
	
	@Scheduled(cron="${cleaner.schedule}")
    public void clearEmailsAndAttachments() {
        log.info("Cleaning received email from memory!");
        smtpListener.getAttachments().clear();
        smtpListener.getEmails().clear();
    }
}

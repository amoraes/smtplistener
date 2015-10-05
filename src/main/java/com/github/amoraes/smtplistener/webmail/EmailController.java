package com.github.amoraes.smtplistener.webmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.amoraes.smtplistener.domain.EmailMessage;
import com.github.amoraes.smtplistener.domain.EmailMessageWithContent;
import com.github.amoraes.smtplistener.smtp.SMTPListener;

/**
 * RESTful Resource to retrieve e-mail data
 * @author Alessandro Moraes
 */
@RestController
@RequestMapping("/emails")
public class EmailController {

	@Autowired
	private SMTPListener smtpListener; 
	
	@RequestMapping(method = RequestMethod.GET)
	public Collection<EmailMessage> findAll(){
		//remove the content
		List<EmailMessage> listToReturn = new ArrayList<EmailMessage>();
		for(EmailMessage emailWithoutContent : smtpListener.getEmails().values()){
			listToReturn.add(emailWithoutContent);
		}
		return listToReturn;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public EmailMessageWithContent findById(@PathVariable Integer id){
		return smtpListener.getEmails().get(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void markAsRead(@RequestBody EmailMessageWithContent email, @PathVariable Integer id){
		smtpListener.getEmails().get(id).setRead(true);
	}
	
}

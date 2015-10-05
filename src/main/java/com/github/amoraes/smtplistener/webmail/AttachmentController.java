package com.github.amoraes.smtplistener.webmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.amoraes.smtplistener.domain.Attachment;
import com.github.amoraes.smtplistener.smtp.SMTPListener;

/**
 * RESTful Resource to retrieve attachments
 * @author Alessandro Moraes
 */
@RestController
@RequestMapping("/attachments")
public class AttachmentController {

	@Autowired
	private SMTPListener smtpListener; 
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> findById(@PathVariable Integer id){
		Attachment attachment = smtpListener.getAttachments().get(id);
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    headers.add("Pragma", "no-cache");
	    headers.add("Expires", "0");
		return ResponseEntity
				.ok()
				.headers(headers)
				.contentLength(attachment.retrieveContent().length)
				.contentType(MediaType.parseMediaType(attachment.getMimeType()))
				.body(attachment.retrieveContent());
	}
	
	
	
	
}
